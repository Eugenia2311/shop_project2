package domain;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class Product implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int price = 0;
    private String description = "";
    transient private PetCategory petCategory;
    transient private ProductCategory productCategory;
    transient private Storage storage;
    transient private List<OrderedProduct> productOrders;

    public Product() {
    }

    public Product(int id) {
        this.id = id;
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PetCategory getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(PetCategory petCategory) {
        this.petCategory = petCategory;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public List<OrderedProduct> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<OrderedProduct> productOrders) {
        this.productOrders = productOrders;
    }

    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(object == null)
            return false;
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        return ((this.id!=0)&&(this.id==other.id))?true:false;
    }

    @Override
    public String toString(){
        return String.format("Product: id=%s, name=%s, price=%d, description =%s",
                id, name, price, description);
    }
}
