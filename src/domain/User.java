package domain;

/**
 * Created by Eugenia on 01.06.2017.
 */
public class User {
    private int id;
    private String login;
    private String password;
    protected String accessRight;
    private String email;
    private String fName;
    private String lName;
    private String address;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessRights() {
        return accessRight;
    }

    public void setAccessRights(String accessRights) {
        this.accessRight = accessRights;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "login = "+ login +" Id="+ id;
    }
}
