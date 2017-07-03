package dao;

import domain.*;
import java.sql.*;
import java.util.*;



/**
 * Created by Eugenia on 03.06.2017.
 */
public class ShoppingCartDAO {

    public boolean addProductInShoppingCart(Customer customer, Product product){
        boolean addingSuccess = false;
        int quantityInCart = this.ProductQuantityInCart(customer, product);
            if(quantityInCart==0) {
                try (Connection connection = JDBCUtil.getConnection()) {
                    String sql = "INSERT INTO shopping_cart VALUES (?, ?,'1')";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, customer.getId());
                    statement.setInt(2, product.getId());
                    statement.executeUpdate();
                    addingSuccess = true;
                } catch (SQLException ex) {
                    System.out.println("Can't add product in shopping cart");
                    ex.printStackTrace();
                }
            }
            else {
                addingSuccess = this.updateProductCount(customer, product, quantityInCart + 1);
            }
        return addingSuccess;
    }


    public int ProductQuantityInCart(Customer customer, Product product){
        int quantity =0;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql ="SELECT* FROM shopping_cart WHERE user_id=? AND product_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getId());
            statement.setInt(2, product.getId());
            ResultSet result = statement.executeQuery();
            ProductDAO dao = new ProductDAO();
            if (result.next()) {
                quantity = result.getInt("quantity");
            }
        } catch (SQLException ex) {
            System.out.println("Can't get product quantity in shopping cart");
            ex.printStackTrace();
        }
        return quantity;
    }
    public boolean updateProductCount(Customer customer, Product product, int newCount){
        boolean updateCountSuccess = false;
        ProductDAO dao = new ProductDAO();
        int productQuantity = dao.getProductQuantity(product);
        if(productQuantity>=newCount) {
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = "UPDATE shopping_cart SET quantity= ? WHERE user_id = ? AND product_id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, newCount);
                statement.setInt(2, customer.getId());
                statement.setInt(3, product.getId());
                statement.executeUpdate();
                updateCountSuccess = true;
            } catch (SQLException ex) {
                System.out.println("Can't update product quantity  in shopping_cart");
                ex.printStackTrace();
            }
        }
        return updateCountSuccess;
    }
    public void removeProductFromCart(Customer customer, Product product){
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getId());
            statement.setInt(2, product.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Can't delete product from cart");
            ex.printStackTrace();
        }
    }

    public Map<Product, Integer> getAllProductsInCart(Customer customer){
        Map<Product,Integer> products = new HashMap<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql ="SELECT* FROM shopping_cart WHERE user_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getId());
            ResultSet result = statement.executeQuery();
            ProductDAO dao = new ProductDAO();
            while (result.next()) {
                int id = result.getInt("product_id");
                int quantity = result.getInt("quantity");
               Product product = dao.getProductById(id);
               products.put(product,quantity);
            }
        } catch (SQLException ex) {
            System.out.println("Can't get products in shopping cart");
            ex.printStackTrace();
        }
        return products;
    }
    private void addProductsInOrder(int order_id, Map<Product,Integer> products){
        ProductDAO dao = new ProductDAO();
        try(Connection connection = JDBCUtil.getConnection()){
            connection.setAutoCommit(false);
            String sql = "INSERT INTO ordered_product VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            for(Map.Entry<Product, Integer> entry: products.entrySet()){
                Product product = entry.getKey();
                int quantity = entry.getValue();
                statement.setInt(1, order_id);
                statement.setInt(2, product.getId());
                statement.setInt(3, quantity);
                statement.addBatch();
                int oldQuantity = dao.getProductQuantity(product);
                if(quantity<=oldQuantity) {
                    dao.updateProductQuantity(product, oldQuantity - quantity);
                }
            }
            statement.executeBatch();
            connection.commit();
        }
        catch (SQLException ex){
            System.out.println("Can't add products in the order");
            ex.printStackTrace();
        }
    }
    public CustomerOrder takeOrder(Customer customer, Map<Product, Integer> products){
        int orderId=0;
        CustomerOrder order = null;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = String.format("INSERT INTO customer_order (status, user_id) VALUES ('заказ принят','%d')",
                    customer.getId());
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet result = statement.getGeneratedKeys();
            if(result.next()){
                 orderId = result.getInt(1);
            }
            if (orderId>0) {
                addProductsInOrder(orderId, products);
                removeCart(customer);
                order = new CustomerOrder(orderId);
                order.setStatus("заказ принят");
            }
        } catch (SQLException ex) {
            System.out.println("Can't take the order");
            ex.printStackTrace();
        }
        return order;
    }

    public void removeCart(Customer customer){
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM shopping_cart WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Can't delete user shopping cart");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ShoppingCartDAO dao = new ShoppingCartDAO();
        OrderDAO orderDAO = new OrderDAO();
        //Product product1 = new Product(1);
        //Product product2 = new Product(2);
        Customer customer = new Customer(2);
        //dao.addProductInShoppingCart(customer, product1);
         //dao.addProductInShoppingCart(customer, product2);
         //dao.removeProductFromCart(customer, product1);
        //dao.removeCart(customer);
        //int id = dao.takeOrder(customer);
        List<OrderedProduct> orderedProducts = orderDAO.getProductsInOrder(new CustomerOrder(1));
        for(OrderedProduct orderedProduct: orderedProducts){
            System.out.println(orderedProduct+" quantity="+orderedProduct.getQuantity());
        }
        //Map<Product, Integer> cartContains = dao.getAllProductsInCart(customer);
        //for(Map.Entry<Product, Integer> entry: cartContains.entrySet()){
           // System.out.println(entry.getKey()+" "+entry.getValue());
        //}
    }
}
