import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public message(String username, String messageText, String colorCode, LocalDateTime timestamp) {
        this.username = username;
        this.messageText = messageText;
        this.colorCode = colorCode;
        this.timestamp = (timestamp != null) ? timestamp : LocalDateTime.now();
        // this.timestamp = timestamp;
    }

    public static final String BOLD = "\u001B[1m";
    public static final String RESET = "\u001B[0m";

    public void fetchmessage() {
        String time = timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String output = "[" + time + "] " + BOLD + colorCode + username + RESET + " : " + messageText;
        System.out.println(output);
    }

    public String getUsername() {
        return username;
    }
    // public static String getColorForUser(String username) {
    // // Uses the username's hash code to consistently pick one of four colors.
    // int hash = username.hashCode();
    // String[] colors = {GREEN, CYAN, MAGENTA, BLUE};
    // return colors[Math.abs(hash % colors.length)];
    // }

    public String getMessageText() {
        return messageText;
    }

    public String getColorCode() {
        return colorCode;
    }
}
