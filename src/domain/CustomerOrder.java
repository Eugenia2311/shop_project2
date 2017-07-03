package domain;

import java.sql.Date;
import java.util.List;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class CustomerOrder {
    private int id;
    private Date dateOfCreation;
    private String status;
    private Customer customer;
    private List<OrderedProduct> productsInOrder;

    public CustomerOrder() {
    }

    public CustomerOrder(int id) {
        this.id = id;
    }

    public CustomerOrder(int id, Date dateOfCreation, String status) {
        this.id = id;
        this.dateOfCreation = dateOfCreation;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderedProduct> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<OrderedProduct> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        return ((this.id!=0)&&(this.id==other.id))?true:false;
    }


    @Override
    public String toString(){
        return "Order: id=" + id +" status ="+ status;
    }
}
