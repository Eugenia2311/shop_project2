package dao;

import domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugenia on 03.06.2017.
 */
public class OrderDAO {

    public List<OrderedProduct> getProductsInOrder(CustomerOrder order){
        List<OrderedProduct> products = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM ordered_product WHERE customer_order_id = ?" ;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int productId = result.getInt("product_id");
                int quantity = result.getByte("quantity");
                OrderedProduct orderedProduct = new OrderedProduct(quantity);
                ProductDAO dao = new ProductDAO();
                Product product = dao.getProductById(productId);
                orderedProduct.setProduct(product);
                orderedProduct.setCustomerOrder(order);
                products.add(orderedProduct);
            }
        } catch (SQLException ex) {
            System.out.println("Can't get user orders");
            ex.printStackTrace();
        }
        return products;
    }

    public List<CustomerOrder> getCustomerOrders(Customer customer) {
        List<CustomerOrder> orders = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM customer_order WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int id = result.getInt("id");
                Date dateOfCreation  = result.getDate("date_created");
                String status = result.getString("status");
                CustomerOrder order = new CustomerOrder(id, dateOfCreation, status);
                orders.add(order);
            }
        } catch (SQLException ex) {
            System.out.println("Can't get user orders");
            ex.printStackTrace();
        }
        return orders;
    }
}
