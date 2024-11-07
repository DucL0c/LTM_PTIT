package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=IOT;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "12345";

    private static DbConnect instance;
    private Connection connection;

    public static DbConnect getInstance() {
        if (instance == null) {
            instance = new DbConnect();
        }
        return instance;
    }

    private DbConnect() {

    }
    
    public Connection getConnection() {
       try {
            // Thay đổi driver cho SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Connected to Database.");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
        }
        return connection;
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
