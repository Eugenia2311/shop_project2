package domain;

import java.util.List;

/**
 * Created by Eugenia on 01.06.2017.
 */
public abstract class Category {
    private int id;
    private String name;
    private List<Product> products;

    protected Category() {
    }

    protected Category(int id) {
        this.id = id;
    }

    protected Category(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "category "+name+" id="+id;
    }
}
