import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = Secrets.DB_URL;

    private static final String USER = Secrets.USER;
    private static final String PASS = Secrets.PASS;

    public static Connection getConnection() throws SQLException {
        System.out.println("Attempting to connect to server...");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connection successful!");
        return connection;
    }
}
