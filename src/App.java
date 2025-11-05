import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class App {

    private static String SELECT_QUERY = "SELECT * FROM messages WHERE id > 0 ORDER BY id ASC";
    public static int id = 0;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        sc.nextLine();

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_QUERY)) {

            System.out.println("--- Results from Database ---");

            boolean hasMessages = false;

            while (rs.next()) {
                String username = rs.getString("username");
                LocalDateTime time = rs.getObject("timestamp", LocalDateTime.class);
                String color = rs.getString("color_code");
                String text = rs.getString("message_text");

                message msg = new message(username, text, color, time);
                msg.fetchmessage();

                id = rs.getInt("id");
                hasMessages = true;
            }
            if (!hasMessages) {
                System.out.println("No messages.");
            }

        } catch (SQLException e) {
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
        sc.close();
    }
}