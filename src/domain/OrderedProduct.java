package domain;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class OrderedProduct {
    private Product product;
    private CustomerOrder order;
    private int quantity;

    public OrderedProduct() {
    }

    public OrderedProduct(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomerOrder getCustomerOrder() {
        return order;
    }

    public void setCustomerOrder(CustomerOrder order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return "order= "+getCustomerOrder().getId()+" product_id= "+getProduct().getId();
    }
}
