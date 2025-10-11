import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatAppCLI {
    public static void main(String[] args) throws IOException {
        List<String> messages = new ArrayList<>();
        StringBuilder input = new StringBuilder();
        int terminalRows = 20; // you can calculate dynamically too

        while (true) {
            // Clear screen
            System.out.print("\033[H\033[J");

            // Print messages above
            int maxMessages = terminalRows - 2;
            int start = Math.max(0, messages.size() - maxMessages);
            for (int i = start; i < messages.size(); i++) {
                System.out.println(messages.get(i));
            }

            // Move cursor to bottom
            System.out.printf("\033[%d;1H> %s", terminalRows, input.toString());
            System.out.flush();

            int ch = System.in.read();
            if (ch == '\n' || ch == '\r') {
                if (input.length() > 0) {
                    messages.add("You: " + input.toString());
                    input.setLength(0);
                }
            } else if (ch == 127 || ch == 8) { // backspace
                if (input.length() > 0)
                    input.deleteCharAt(input.length() - 1);
            } else {
                input.append((char) ch);
            }
        }
    }
}
