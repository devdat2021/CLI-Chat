import java.time.LocalDateTime;
import java.sql.*;
import java.util.*;

class MessageFetch extends Thread {
    private Connection conn;
    private static String SELECT_QUERY = "SELECT * FROM messages WHERE id > ? ORDER BY id ASC";

    public MessageFetch(Connection conn) {
        this.conn = conn;
    }

    public void run() {
        while (true) {
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_QUERY);
                ps.setInt(1, LiveChat.id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String username = rs.getString("username");
                    LocalDateTime time = rs.getObject("timestamp", LocalDateTime.class);
                    String color = rs.getString("color_code");
                    String text = rs.getString("message_text");
                    message msg = new message(username, text, color, time);
                    msg.fetchmessage();

                    LiveChat.id = rs.getInt("id");
                }

                rs.close();
                ps.close();
                Thread.sleep(1300);

            } catch (SQLException e) {
                System.out.println("Database error occurred! -> " + e.getMessage());
                break;
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
                break;
            }

        }
    }
}

public class LiveChat {
    public static int id = 0;
    public static final Set<String> ASCII_COMMANDS = Set.of(
            "/list",
            "/red",
            "/butterfly",
            "/aah",
            "/dance",
            "/kitty",
            "/sparkle");

    public static void start(Scanner sc) throws InterruptedException {
        try (Connection conn = DBConnection.getConnection();) {
            System.out.println("--- Chat room (Live) ---");
            MessageFetch reciever = new MessageFetch(conn);
            reciever.start();

            while (true) {

                String text = sc.nextLine();

                if (text.equalsIgnoreCase("exit")) {
                    reciever.interrupt();
                    break; // allow quitting
                }
                message msg = new message(App.uname, text, App.ucolor);
                msg.writemessage(conn);
                if (ASCII_COMMANDS.contains(text.toLowerCase())) {
                    extra.ascii(text);
                }

            }
            reciever.join();

        } catch (SQLException e) {
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
        sc.close();
    }

}
