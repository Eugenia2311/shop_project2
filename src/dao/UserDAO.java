package dao;

import domain.*;

import java.sql.*;
import java.util.*;


/**
 * Created by Eugenia on 03.06.2017.
 */
public class UserDAO {
    //public static final String REGISTRATION_FAIL =
           // "Приносим свои извинения! При регистрации возникли технические проблемы.";
    //public static final String LOGIN_EXIST = "Такой логин уже существует. Попробуйте другой";
    //public static final String REGISTRATION_SUCCESS = "Поздравляем, Вы успешно зарегестрированы!";

    private boolean registerUser(User user){
        boolean registrationSuccess = false;
        User oldUser = getUserByLogin(user.getLogin());
        if (oldUser == null) {
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = "INSERT INTO user (login, password, access_rights, email)" +
                                "VALUES(?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getAccessRights());
                statement.setString(4, user.getEmail());
                statement.executeUpdate();
                registrationSuccess = true;
            } catch (SQLException ex) {
                System.out.println("Can't register user");
                ex.printStackTrace();
            }
        }
        return registrationSuccess;
    }

    public boolean registerAdministrator(String login, String password, String email){
        User user = new User(1, login, password, email);
        user.setAccessRights("administrator");
        return registerUser(user);
    }

    public boolean registerCustomer(String login, String password, String email){
        User user = new User(1, login, password, email);
        user.setAccessRights("customer");
        return registerUser(user);
    }
    public boolean login(String login, String password){
        boolean loginSuccess = false;
        User user = getUserByLogin(login);
        if ((user!=null)&&(user.getPassword().equals(password))){
            loginSuccess = true;
        }
        return loginSuccess;
    }
    public void addUserContacts(User user, String fName, String lName, String address){
        try (Connection connection = JDBCUtil.getConnection()) {
            String sql = "UPDATE user SET f_name=?, l_name=?, address=? WHERE user_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, address);
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Can't add user contacts");
            ex.printStackTrace();
        }
    }


    public User getUserByLogin(String login){
        User user = null;
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql = "SELECT* FROM user WHERE login = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, login);
                ResultSet result = statement.executeQuery();
                if (result.next()){
                   int id = result.getInt("id");
                   String password = result.getString("password");
                   String email = result.getString("email");
                   String fName = result.getString("f_name");
                   String lName = result.getString("l_name");
                   String address = result.getString("address");
                   String accessRights = result.getString("access_rights");
                   if(accessRights.equals("customer")) {
                       user = new Customer(id, login, password, email);
                       user.setAccessRights(accessRights);
                       user.setfName(fName);
                       user.setlName(lName);
                       user.setAddress(address);
                   } else if(accessRights.equals("administrator")){
                       user = new Administrator(id, login, password, email);
                       user.setAccessRights(accessRights);
                   }
                }
            } catch (SQLException ex) {
                System.out.println("Can't get user");
                ex.printStackTrace();
            }
            return user;
        }
        public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
            try (Connection connection = JDBCUtil.getConnection()) {
                String sql ="SELECT* FROM user";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    int id = result.getInt("id");
                    String login = result.getString("login");
                    String email = result.getString("email");
                    String accessRights = result.getString("access_rights");
                    User user = new User(id, login, "", email);
                    user.setAccessRights(accessRights);
                    users.add(user);
                }
            } catch (SQLException ex) {
                System.out.println("Can't get all users");
                ex.printStackTrace();
            }
            return users;
        }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        //dao.registerAdministrator("eugenia","123456","abc@gmail.com");
        //dao.registerCustomer("anna","abc123", "anna@mail.ru");
        //dao.registerCustomer("alex", "alex459", "alex@mail.ru");
        //List<User> users = dao.getAllUsers();
        //for(User user: users){
            //System.out.println(user+" access rights= "+user.getAccessRights());
        //}
        System.out.println(dao.login("anna", "abc123"));
        System.out.println(dao.login("anna", "abc124"));
        System.out.println(dao.login("fdr", "abc123"));
      }
    }


