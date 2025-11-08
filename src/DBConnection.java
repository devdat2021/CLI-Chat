import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://0.0.0.0:3306/chat";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() throws SQLException {

        System.out.println("Attempting to connect to server...");

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        System.out.println("Connection successful!");
        return connection;
    }
}
