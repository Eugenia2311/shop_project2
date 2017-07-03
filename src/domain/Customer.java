package domain;
import java.util.List;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class Customer extends User {
    private List<CustomerOrder> orders;

    public Customer(){
        super();
    }
    public Customer(int id){
        super(id);
    }

    public Customer(int id, String login, String password, String email) {
        super(id, login, password, email);
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return ((this.getId()!=0)&&(this.getId()==other.getId()))?true:false;
    }

    @Override
    public String toString(){
        return "Customer "+super.toString();
    }

}
