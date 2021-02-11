package Connectivity;

//package Connectivity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionClass {
    public static Connection connection;

    public Connection getConnection(){
        String url = "jdbc:mysql://puff:3306/condemo_syliDB?useLegacyDatetimeCode=false&serverTimezone=America/Chicago";
        String userName = "condemo";
        String pw = "gRoayOhk2";
        System.out.println("Loading driver...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find driver", e);
        }
        System.out.println("Driver Loaded");
        connection = null;

        System.out.println("Trying to connect to database ...");
        try {
            connection = DriverManager.getConnection (url,userName, pw);
        } catch (SQLException e) {              e.printStackTrace();
        }
        System.out.println("Connected to the database");
        return connection;
    }

    public static void closeConnection() throws SQLException{
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch(Exception e){             throw e;
        }
    }
}
