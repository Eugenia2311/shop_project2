package dao;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Eugenia on 02.06.2017.
 */
public class CategoryDAO {

    public void addPetCategory(PetCategory category){

        if (category!=null) {
            try (Connection connection = JDBCUtil.getConnection()) {
                    String sql = "INSERT INTO pet_category (name) VALUES(?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, category.getName());
                    statement.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Can't add pet category");
                    ex.printStackTrace();
                }
        }
    }

    public void addProductSubCategory(ProductCategory productCategory, PetCategory petCategory){
        ProductCategory oldProductCategory = this.getProductCategoryByName(productCategory.getName());
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql;
            Statement statement = connection.createStatement();
            if (oldProductCategory == null) {
                sql = String.format("INSERT INTO product_category (name) VALUES('%s')",
                        productCategory.getName());
                statement.executeUpdate(sql);
                oldProductCategory = this.getProductCategoryByName(productCategory.getName());
            }
                sql = String.format("INSERT INTO pet_category_has_product_category VALUES('%d', '%d')",
                        petCategory.getId(), oldProductCategory.getId());
                statement.executeUpdate(sql);


        } catch (SQLException ex) {
            System.out.println("Can't add product category");
            ex.printStackTrace();
        }
    }
    //Переделать(сначала нужно удалить из соединяющей таблицы)
    /*public void removeCategory(Category category){
        String categoryType = null;
        if (category!=null) {
            if (category instanceof PetCategory) {
                categoryType = "pet";
            } else if (category instanceof ProductCategory) {
                categoryType = "product";
            }
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = String.format("DELETE LOW_PRIORITY FROM %s_category WHERE id = '%d'",
                        categoryType, category.getId());
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Can't delete category");
                ex.printStackTrace();
            }
        }
    }*/

    public PetCategory getPetCategoryById(int id) {
        PetCategory category = null;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM pet_category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result != null) {
                while (result.next()) {
                    int currentId = result.getByte("id");
                    String name = result.getString("name");
                    category = new PetCategory(id, name);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Can't find a pet category");
            ex.printStackTrace();
        }
        return category;
    }

    public PetCategory getPetCategoryByName(String name){
        PetCategory category = null;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM pet_category WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

                while (result.next()) {
                    int id = result.getByte("id");
                    category = new PetCategory(id, name);
                }

        } catch (SQLException ex) {
            System.out.println("Can't find a pet category");
            ex.printStackTrace();
        }
        return category;
    }

    public List<PetCategory> getAllPetCategories(){
        List<PetCategory> categories = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM pet_category";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    int id = result.getByte("id");
                    String name = result.getString("name");
                    PetCategory category = new PetCategory(id, name);
                    List<ProductCategory> subCategories = this.getAllPetSubCategories(category);
                    category.setProductCategories(subCategories);
                    categories.add(category);
                }
        } catch (SQLException ex) {
            System.out.println("Can't get all categories");
            ex.printStackTrace();
        }
        return categories;
    }

    public ProductCategory getProductCategoryById(int id) {
        ProductCategory category = null;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM product_category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result != null) {
                while (result.next()) {
                    int currentId = result.getByte("id");
                    String name = result.getString("name");
                    category = new ProductCategory(id, name);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Can't find  product category");
            ex.printStackTrace();
        }
        return category;
    }


    public ProductCategory getProductCategoryByName(String name){
        ProductCategory category = null;
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT* FROM product_category WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int id = result.getByte("id");
                    category = new ProductCategory(id, name);
            }
        } catch (SQLException ex) {
            System.out.println("Can't find a category");
            ex.printStackTrace();
        }
        return category;
    }
    public List<ProductCategory> getAllPetSubCategories(PetCategory petCategory){
        List<ProductCategory> categories = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "SELECT a.id, a.name FROM product_category a INNER JOIN " +
                    "pet_category_has_product_category b ON a.id=b.product_category_id WHERE " +
                    "b.pet_category_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, petCategory.getId());
            ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int id = result.getByte("id");
                    String name = result.getString("name");
                    ProductCategory category = new ProductCategory(id, name);
                    categories.add(category);

            }
        } catch (SQLException ex) {
            System.out.println("Can't get all categories");
            ex.printStackTrace();
        }
        return categories;
    }

    public static void main(String[] args) {
        /*try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM pet_category_has_product_category";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("ok");
            sql = "DELETE FROM product_category";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE product_category AUTO_INCREMENT=0";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE product AUTO_INCREMENT=0";
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println("Can't delete");
            ex.printStackTrace();
        } */
        CategoryDAO dao = new CategoryDAO();
        //PetCategory pet1 = dao.getPetCategoryByName("кошки");
        //PetCategory pet2 = dao.getPetCategoryByName("собаки");
        List<PetCategory> petCategories = dao.getAllPetCategories();
        for(PetCategory category: petCategories){
            System.out.println(category);
            //dao.addProductSubCategory(new ProductCategory(1,"сухой корм"), category);
            //dao.addProductSubCategory(new ProductCategory(2,"консервы"), category);
            //dao.addProductSubCategory(new ProductCategory(3,"игрушки"), category);
            List<ProductCategory> productCategories = dao.getAllPetSubCategories(category);
            for(ProductCategory prCategory: productCategories){
                System.out.println(prCategory);
            }
        }


    }

}
