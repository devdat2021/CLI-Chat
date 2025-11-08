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
                ps.setInt(1, App.id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String username = rs.getString("username");
                    LocalDateTime time = rs.getObject("timestamp", LocalDateTime.class);
                    String color = rs.getString("color_code");
                    String text = rs.getString("message_text");
                    message msg = new message(username, text, color, time);
                    msg.fetchmessage();

                    App.id = rs.getInt("id");
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

public class App {
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
                message msg = new message(uname, text, ucolor);
                msg.writemessage(conn);
            }
            reciever.join();

        } catch (SQLException e) {
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
        sc.close();
    }
}