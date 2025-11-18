import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Chatapp";
    private static final String USER = "root";
    private static final String PASS = "devdat";

    public static Connection getConnection() throws SQLException {

        System.out.println("Attempting to connect to server...");

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        System.out.println("Connection successful!");
        return connection;
    }
}
