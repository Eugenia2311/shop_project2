package domain;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class Storage {
    private Product product;
    private int quantity;

    public Storage() {
    }

    public Storage(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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

    @Override
    public String toString(){
        return "Product: "+product.getName()+", quantity:"+quantity;
    }
}
