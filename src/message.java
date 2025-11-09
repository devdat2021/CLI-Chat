import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class message {
    private String username;
    private String messageText;
    private String colorCode;
    private LocalDateTime timestamp;

    public message(String username, String message, String colorCode) {
        this.username = username;
        this.messageText = message;
        this.colorCode = colorCode;

    }

    public message(String username, String messageText, String colorCode, LocalDateTime timestamp) {
        this.username = username;
        this.messageText = messageText;
        this.colorCode = colorCode;
        this.timestamp = (timestamp != null) ? timestamp : LocalDateTime.now();
    }

    public void fetchmessage() {
        final String BOLD = "\u001B[1m";
        final String RESET = "\u001B[0m";
        synchronized (System.out) {
            System.out.print("\r"); // move to start of current line
            System.out.print("\033[K"); // clear line
            // String time = timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalDateTime localTime = timestamp.plusHours(5).plusMinutes(30); // shifting UTC time to IST
            String time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            if (!((colorCode.equals(App.ucolor)) && (username.equals(App.uname)))) {
                String output = "[" + time + "] " + BOLD + colorCode + username + RESET + " : " + messageText;

                System.out.println(output);
            }
            System.out.print(BOLD + App.ucolor + "YOU" + RESET + " : ");
            // String output = "[" + time + "] " + BOLD + colorCode + username + RESET + " :
            // " + messageText;
            // System.out.println(output);
            // System.out.print("> ");
            System.out.flush();
        }

    }

    public void writemessage(Connection conn) throws SQLException {
        String sql = "INSERT INTO messages (username, message_text, color_code) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, messageText);
            ps.setString(3, colorCode);
            ps.executeUpdate();
        }
    }
}
