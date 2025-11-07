import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        System.out.print("\r"); // move cursor to line start
        System.out.print("\033[K"); // clear line
        String time = timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String output = "[" + time + "] " + BOLD + colorCode + username + RESET + " : " + messageText;
        System.out.println(output);
        System.out.flush();
        System.out.print("> ");

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
