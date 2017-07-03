package domain;

import java.util.List;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class PetCategory extends Category {

    private List<ProductCategory> productCategories;

    public PetCategory() {
        super();
    }

    public PetCategory(int id) {
        super(id);
    }

    public PetCategory(int id, String name) {
        super(id,name);
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public String toString() {
        return "pet "+super.toString();
    }
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PetCategory)) {
            return false;
        }
        PetCategory other = (PetCategory) object;
        return ((this.getId()!=0)&&(this.getId()==other.getId()))?true:false;
    }
}
