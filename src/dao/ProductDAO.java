package dao;

import domain.Category;
import domain.PetCategory;
import domain.Product;
import domain.ProductCategory;

import java.sql.*;
import java.util.*;

/**
 * Created by Eugenia on 02.06.2017.
 */
public class ProductDAO {



    private Product extractProductFromResultSet(ResultSet result){
        Product product = null;
        CategoryDAO categoryDAO = new CategoryDAO();

        try {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int price = result.getInt("price");
                    String description = result.getString("description");
                    PetCategory petCategory = categoryDAO.getPetCategoryById(
                            result.getInt("pet_category_id"));
                    ProductCategory productCategory = categoryDAO.getProductCategoryById(
                            result.getInt("product_category_id"));
                    product = new Product(id, name, price);
                    product.setDescription(description);
                    product.setPetCategory(petCategory);
                    product.setProductCategory(productCategory);
        }catch(SQLException ex){
            System.out.println("Can't extract product from ResultSet");
            ex.printStackTrace();
        }
        return product;
    }
    private void addNewProductInStorage(String name, int quantity, Connection connection){
            try { Product product = getProductByName(name);
            String sql = "INSERT INTO storage VALUES(?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getId());
            statement.setInt(2, quantity);
            statement.executeUpdate();
        } catch(SQLException ex){
            System.out.println("Can't add product in storage");
            ex.printStackTrace();
        }
    }
    public void addProduct(Product product, int quantity){
        try(Connection connection = JDBCUtil.getConnection()){
            Product oldProduct = getProductByName(product.getName());
            if(oldProduct==null) {
                String sql = "INSERT INTO product (name, price, description, pet_category_id, product_category_id) " +
                                "VALUES(?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, product.getName());
                statement.setInt(2, product.getPrice());
                statement.setString(3, product.getDescription());
                statement.setInt(4, product.getPetCategory().getId());
                statement.setInt(5, product.getProductCategory().getId());
                statement.executeUpdate();
                addNewProductInStorage(product.getName(),quantity, connection);
            }
        }
        catch(SQLException ex){
            System.out.println("Can't add product");
            ex.printStackTrace();
        }
    }

    public Product getProductByName(String name){
        Product product = null;
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = "SELECT* FROM product WHERE name = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                ResultSet result = statement.executeQuery();
                if (result.next()){
                    product = extractProductFromResultSet(result);
                }
            } catch (SQLException ex) {
                System.out.println("Can't find product by name");
                ex.printStackTrace();
            }
        return product;
    }

    public Product getProductById(int id){
        Product product = null;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                product = extractProductFromResultSet(result);
            }
        } catch (SQLException ex) {
            System.out.println("Can't find product by id");
            ex.printStackTrace();
        }
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = String.format("SELECT* FROM product");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    Product product = extractProductFromResultSet(result);
                    products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Can't find product");
            ex.printStackTrace();
        }
        return products;
    }

        public Map<Product, Integer> getAllProductsInCategory(PetCategory petCategory, ProductCategory productCategory){
            Map<Product, Integer> products = new HashMap<>();

                try (Connection connection = JDBCUtil.getConnection()) {
                    String sql = "SELECT* FROM product WHERE pet_category_id=? AND " +
                                    "product_category_id=?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, petCategory.getId());
                    statement.setInt(2, productCategory.getId());
                    ResultSet result = statement.executeQuery();
                        while (result.next()) {
                            Product product = extractProductFromResultSet(result);
                            int quantity = this.getProductQuantity(product);
                            products.put(product, quantity);
                        }
                } catch (SQLException ex) {
                    System.out.println("Can't get products in category");
                    ex.printStackTrace();
                }
        return products;
    }

    private void removeProductFromStorage(Product product, Connection connection){
        try{
            String sql = "DELETE FROM storage WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getId());
            statement.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Can't delete product from storage");
            ex.printStackTrace();
        }
    }

    public void removeProduct(Product product){
        Product oldProduct = getProductByName(product.getName());
        if(oldProduct!=null) {
            try (Connection connection = JDBCUtil.getConnection()) {
                removeProductFromStorage(oldProduct, connection);
                String sql = "DELETE FROM product WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, product.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Can't delete product");
            }
        }
    }
    public void updateProductQuantity(Product product, int quantity){
        Product oldProduct = getProductById(product.getId());
        if(oldProduct!=null) {
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = "UPDATE LOW_PRIORITY storage SET quantity= ? WHERE product_id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, quantity);
                statement.setInt(2, product.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Can't update product quantity  in storage");
                ex.printStackTrace();
            }
        }
    }
    public int getProductQuantity(Product product) {
        int quantity = 0;
        Product oldProduct = getProductById(product.getId());
        if (oldProduct != null) {
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = "SELECT quantity FROM storage WHERE product_id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, product.getId());
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    quantity = result.getInt("quantity");
                }
            } catch (SQLException ex) {
                System.out.println("Can't get product quantity  from storage");
                ex.printStackTrace();
            }
        }
        return quantity;
    }


    public static void main(String[] args) {
         ProductDAO dao = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
         /*ProductCategory productCategory1 = new ProductCategory(3);
         ProductCategory productCategory2 = new ProductCategory(4);
         ProductCategory productCategory3 = new ProductCategory(5);
         PetCategory petCategory = new PetCategory(2);
         Product product1 = new Product(1,"Royal canin beef",15,"корм для собак со вкусом говядины");
         product1.setPetCategory(petCategory);
         product1.setProductCategory(productCategory1);
         dao.addProduct(product1, 5);

        Product product2 = new Product(2,"Royal canin puppy",12,"корм для щенков");
        product2.setPetCategory(petCategory);
        product2.setProductCategory(productCategory1);
        dao.addProduct(product2, 5);

        Product product3 = new Product(3,"Royal canin chiken ",13,"консервы с курицей");
        product3.setPetCategory(petCategory);
        product3.setProductCategory(productCategory2);
        dao.addProduct(product3, 4);

        Product product4 = new Product(4,"Royal canin beef(can) ",15,"консервы с говядиной");
        product4.setPetCategory(petCategory);
        product4.setProductCategory(productCategory2);
        dao.addProduct(product4, 3);

        Product product5 = new Product(5,"Утка резиновая ",3,"игрушка для собак: резиновая утка");
        product5.setPetCategory(petCategory);
        product5.setProductCategory(productCategory3);
        dao.addProduct(product5, 2);

        Product product6 = new Product(6,"Мячик средний для собак ",2,"игрушка для собак: мячик");
        product6.setPetCategory(petCategory);
        product6.setProductCategory(productCategory3);
        dao.addProduct(product6, 2);*/


        //dao.updateProductQuantity(new Product(1), 10);
        List<PetCategory> petCategories = categoryDAO.getAllPetCategories();
        for(PetCategory category: petCategories){
            System.out.println(category);
            List<ProductCategory> productCategories = category.getProductCategories();
            for(ProductCategory prCategory: productCategories){
                System.out.println(prCategory);
                Map<Product, Integer> products = dao.getAllProductsInCategory(category, prCategory);
                for(Map.Entry<Product, Integer> entry: products.entrySet()){
                    System.out.println(entry.getKey()+" quantity="+entry.getValue());
                }

            }
        }

       /*List<Product> allProducts = dao.getAllProducts();
        for(Product product: allProducts){
            System.out.println(product);
        }*/
    }
}
