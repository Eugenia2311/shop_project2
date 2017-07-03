package domain;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class Administrator extends User {

   public Administrator(){
        super();
    }
   public Administrator(int id){
        super(id);
    }

    public Administrator(int id, String login, String password, String email) {
        super(id, login, password, email);
    }

    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        return ((this.getId()!=0)&&(this.getId()==other.getId()))?true:false;
    }

    @Override
    public String toString(){
        return "Administrator "+super.toString();
    }

}
