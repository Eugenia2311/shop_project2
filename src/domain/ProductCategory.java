package domain;

import java.util.List;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class ProductCategory extends Category {
    private List<PetCategory> petCategories;

    public ProductCategory() {
        super();
    }

    public ProductCategory(int id) {
        super(id);
    }

    public ProductCategory(int id, String name) {
        super(id,name);
    }

    public List<PetCategory> getPetCategories() {
        return petCategories;
    }

    public void setPetCategories(List<PetCategory> petCategories) {
        this.petCategories = petCategories;
    }

    @Override
    public String toString() {
        return "product "+super.toString();
    }
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategory)) {
            return false;
        }
        PetCategory other = (PetCategory) object;
        return ((this.getId()!=0)&&(this.getId()==other.getId()))?true:false;
    }
}
