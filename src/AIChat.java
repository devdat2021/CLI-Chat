import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AIChat {
    private static String extractText(String json) {
        String key = "\"text\":";
        int index = json.indexOf(key);
        if (index == -1)
            return "Sorry I didn't catch that";

        // Move to the first quote after "text":
        int start = json.indexOf("\"", index + key.length()) + 1;

        StringBuilder sb = new StringBuilder();
        boolean escape = false;

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (escape) {
                if (c == 'n')
                    sb.append('\n');
                else
                    sb.append(c);
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

    public static void start(Scanner sc, String uname) throws Exception {
        String apiKey = Secrets.Api;
        System.out.println("Bot: " + "Hi! " + uname + " I am an AI bot here to chat with you");

        while (true) {
            // System.out.print("You: ");
            message.sendtobot();
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!" + uname);
                return; // allow quitting
            }
            // String jsonbody = String.format(
            // "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
            // input.replace("\"", "\\\""));

            String systemPrompt = "You are CLIChatBot. Be friendly, helpful, and casual. " +
                    "Never reply with more than 3 lines. Keep responses clear and simple.";
            String jsonbody = String.format(
                    "{" +
                            " \"systemInstruction\": { \"parts\": [{ \"text\": \"%s\" }] }," +
                            " \"contents\": [ { \"parts\": [{ \"text\": \"%s\" }] } ]" +
                            "}",
                    systemPrompt.replace("\"", "\\\""),
                    input.replace("\"", "\\\""));

            ProcessBuilder pb = new ProcessBuilder(
                    "curl",
                    "-s",
                    "-X", "POST",
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                            + apiKey,
                    "-H", "Content-Type: application/json",
                    "-d", jsonbody);

            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();
            String aiText = extractText(json);
            // System.out.println("DEBUG - RAW JSON: " + json);
            System.out.println("Bot: " + aiText);
            p.waitFor();
        }

    }
}
