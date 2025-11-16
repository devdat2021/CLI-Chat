import java.util.*;

public class App {

    public static String uname, ucolor;
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
        uname = sc.nextLine();
        ucolor = getRandomColor();
        // System.out.println("Select mode:");
        // System.out.println("1. Live Chat");
        // System.out.println("2. AI Chat");
        // System.out.print("Enter choice: ");
        int choice = 1; // sc.nextInt();
        // sc.nextLine();

        if (choice == 1) {
            LiveChat.start(sc);
        } else if (choice == 2) {
            System.out.println("AI Chat mode coming soon!");
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}