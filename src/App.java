import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class App {

    private static String SELECT_QUERY = "SELECT * FROM messages WHERE id > 0 ORDER BY id ASC";
    public static int id = 0;

    private static final String[] COLORS = {
            "\u001B[31m", // Red
            "\u001B[32m", // Green
            "\u001B[33m", // Yellow
            "\u001B[34m", // Blue
            "\u001B[35m", // Magenta
            "\u001B[36m", // Cyan
            "\u001B[91m", // Bright Red
            "\u001B[92m", // Bright Green
            "\u001B[93m", // Bright Yellow
            "\u001B[94m", // Bright Blue
            "\u001B[95m", // Bright Magenta
            "\u001B[96m" // Bright Cyan
    };

    public static String getRandomColor() {
        Random rand = new Random();
        return COLORS[rand.nextInt(COLORS.length)];
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        String ucolor = getRandomColor();

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_QUERY)) {

            System.out.println("--- Chat room (Live) ---");

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

            while (true) {
                System.out.print("> ");
                String text = sc.nextLine();

                if (text.equalsIgnoreCase("exit"))
                    break; // allow quitting

                message msg = new message(uname, text, ucolor);
                msg.writemessage(conn);
            }

        } catch (SQLException e) {
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
        sc.close();
    }
}