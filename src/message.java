import java.time.LocalDateTime;

public class message {
    private int id;
    private String username;
    private String messageText;
    private String colorCode;
    private LocalDateTime timestamp;

    public message(String username, String message, String colorCode) {
        this.username = username;
        this.messageText = message;
        this.colorCode = colorCode;
    }

    public message(int id, String username, String messageText, String colorCode, LocalDateTime timestamp) {
        this.id = id;
        this.username = username;
        this.messageText = messageText;
        this.colorCode = colorCode;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getColorCode() {
        return colorCode;
    }
    // ... other getters (getID, getTimestamp)s
}
