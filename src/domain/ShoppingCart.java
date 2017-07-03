package domain;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class ShoppingCart {
    private Customer customer;
    private Product product;
    private int quantity;

    public ShoppingCart() {
    }

    public ShoppingCart(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
