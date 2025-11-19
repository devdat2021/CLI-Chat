import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AIChat {

        private static String extractText(String json) {
    String key = "\"text\":";
    int index = json.indexOf(key);
    if (index == -1) return "(no text found)";

    // Move to the first quote after "text":
    int start = json.indexOf("\"", index + key.length()) + 1;

    StringBuilder sb = new StringBuilder();
    boolean escape = false;

    for (int i = start; i < json.length(); i++) {
        char c = json.charAt(i);

        if (escape) {
            // Handle escaped characters like \" or \n
            if (c == 'n') sb.append('\n');
            else sb.append(c);
            escape = false;
            continue;
        }

        if (c == '\\') {
            escape = true;
            continue;
        }

        if (c == '"') {
            // End of string
            break;
        }

        sb.append(c);
    }

    return sb.toString();
}

    public static void main(String[] args) throws Exception {
        String apiKey = "Your API Key";

        Scanner sc = new Scanner(System.in);

        

        while (true) {
            System.out.print("You: ");
            String input = sc.nextLine();

            String jsonbody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                input.replace("\"", "\\\"")
            );

            ProcessBuilder pb = new ProcessBuilder(
                "curl",
                "-s",
                "-X", "POST",
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey,
                "-H", "Content-Type: application/json",
                "-d", jsonbody
            );

            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();

            // Extract the "text": "..."
            String aiText = extractText(json);

            System.out.println("AI: " + aiText);
            
           
            // System.out.print("AI:");
            // p.getInputStream().transferTo(System.out);


            p.waitFor();
        }
        
    }
}
