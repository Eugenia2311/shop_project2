package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Eugenia on 02.06.2017.
 */
public class JDBCUtil {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/petshop1?useSSL=false";

    //  Database credentials
    static final String USER = "eva";
    static final String PASS = "eva69";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        }
        catch(ClassNotFoundException ex1){
            System.out.println("Can not find driver class");
            ex1.printStackTrace();
        }
        catch(SQLException ex2){
            System.out.println("Can not connect to database");
            ex2.printStackTrace();
        }
        return connection;
    }

}
