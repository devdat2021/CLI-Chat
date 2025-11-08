import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

class MessageFetch extends Thread {
    private Connection conn;
    private static String SELECT_QUERY = "SELECT * FROM messages WHERE id > ? ORDER BY id ASC";
    private String CurrentUser;
    private String CurrentColor;

    public MessageFetch(Connection conn,String CurrentColor,String CurrentUser) {
        this.conn = conn;
        this.CurrentColor=CurrentColor;
        this.CurrentUser=CurrentUser;
        
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
                   msg.fetchmessage(CurrentColor,CurrentUser);

                   

                    App.id = rs.getInt("id");
                }

                rs.close();
                ps.close();
                Thread.sleep(1300);

            } catch (SQLException e) {
                System.out.println("Database error occurred!");
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

     // ANSI CODES
    private static final String SAVE = "\u001B[s";
    private static final String REST = "\u001B[u";
    private static final String CLR  = "\u001B[K";

    private static StringBuilder input = new StringBuilder();

      public static synchronized void printIncoming(String msg) {
        System.out.print(SAVE);       // save cursor
        System.out.print("\r\n");     // move to new line
        System.out.println(msg);      // print message
        System.out.print(REST);       // restore cursor
        System.out.print(CLR);        // clear junk
        System.out.print("> " + input); // redraw input
        System.out.flush();
    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        String CurrentUser =uname;
        String ucolor = getRandomColor();
        String CurrentColor = ucolor;
        Console console = System.console();

        try (Connection conn = DBConnection.getConnection();) {
            System.out.println("--- Chat room (Live) ---");
            MessageFetch reciever = new MessageFetch(conn, CurrentColor,CurrentUser);
            reciever.start();
          // String text;
            while (true) {
                
            //     if (System.in.available()>0){
            //         char c = (char) System.in.read();

            //         if(c=='\n'){
            //             text = input.toString();
            //             input.setLength(0);

            //             if (text.equalsIgnoreCase("exit")){
            //         break; // allow quitting
            //     }

            //     message msg = new message(uname, text, ucolor);
            //     msg.writemessage(conn);
            //     System.out.println();
            //     continue;
            //  }

            //  if(c==127 || c==8){
            //     if (input.length()>0){
            //         input.setLength(input.length()-1);
            //         System.out.print("\b \b");

            //     }
            //     continue;
            //  }
            //  input.append(c);
            //  System.out.print(c);


            //   }

            //   Thread.sleep(10);

                String text = sc.nextLine();
                //String text = console.readLine("msg:");

                        if (text.equalsIgnoreCase("exit")){
                    break; // allow quitting
                }else if (text.equalsIgnoreCase("/list")){
                    System.out.println("/red /butterfly");
                }
                else if (text.equalsIgnoreCase("/butterfly")) {
                   text = "\n⣠⣤⣤⡤⠤⢤⣤⣀⡀⠀⠐⠒⡄⠀⡠⠒⠀⠀⢀⣀⣤⠤⠤⣤⣤⣤⡄\n" + //
                                              "⠈⠻⣿⡤⠤⡏⠀⠉⠙⠲⣄⠀⢰⢠⠃⢀⡤⠞⠋⠉⠈⢹⠤⢼⣿⠏⠀\n" + //
                                              "⠀⠀⠘⣿⡅⠓⢒⡤⠤⠀⡈⠱⣄⣼⡴⠋⡀⠀⠤⢤⡒⠓⢬⣿⠃⠀⠀\n" + //
                                              "⠀⠀⠀⠹⣿⣯⣐⢷⣀⣀⢤⡥⢾⣿⠷⢥⠤⣀⣀⣞⣢⣽⡿⠃⠀⠀⠀\n" + //
                                              "⠀⠀⠀⠀⠈⢙⣿⠝⠀⢁⠔⡨⡺⡿⡕⢔⠀⡈⠐⠹⣟⠋⠀⠀⠀⠀⠀\n" + //
                                              "⠀⠀⠀⠀⠀⢼⣟⢦⢶⢅⠜⢰⠃⠀⢹⡌⢢⣸⠦⠴⣿⡇⠀⠀⠀⠀⠀\n" + //
                                              "⠀⠀⠀⠀⠀⠘⣿⣇⡬⡌⢀⡟⠀⠀⠀⢷⠀⣧⢧⣵⣿⠂⠀⠀⠀⠀⠀\n" + //
                                              "⠀⠀⠀⠀⠀⠀⠈⢻⠛⠋⠉⠀⠀⠀⠀⠈⠉⠙⢻⡏⠀⠀⠀⠀⠀⠀⠀\n" + //
                                              "⠀⠀⠀⠀⠀⠀⢰⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠄⠀⠀⠀⠀⠀⠀";
                                              System.out.println(text);
                    
                }else if(text.equalsIgnoreCase("/red")){
                    text = "\n⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⢻⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⢤⣄⣀⣀⣀⣰⡇⠈⣧⣀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⢦⡀⠀⠀⠀⠀⢀⣠⠾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣹⠃⠀⡀⠀⢿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠀⣠⠔⠂⠀⠀⠀⢠⣏⣴⠞⠛⢦⣜⣧⠀⠀⠀⠀⠢⣄⡀⠀⠀⠀⠀\n" + //
                                                "⠀⠀⢠⣖⡿⡋⠀⠀⠀⠀⠀⠾⠋⠀⠀⠀⠀⠉⠻⡄⠀⠀⠀⠀⢝⢿⣱⣄⠀⠀\n" + //
                                                "⠀⡜⣿⣨⡾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠲⣤⡀⠀⠀⠀⠀⠀⠈⢳⣇⣿⢡⠀\n" + //
                                                "⢰⣇⣟⣵⠃⠀⠀⠀⠀⠀⠀⢀⣴⣦⡤⠀⠀⠈⠻⣷⡀⠀⠀⠀⠀⠈⣯⡻⢸⡆\n" + //
                                                "⡆⣿⡾⡅⠀⠀⠀⠀⠀⢀⣴⣿⣿⣏⠀⠀⠀⠀⠀⠹⣿⡆⠀⠀⠀⠀⢨⢻⣾⢱\n" + //
                                                "⣷⡘⣱⠇⠀⠀⠀⠀⠀⠀⠹⠋⠈⠻⣷⣄⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠘⣧⢋⣾\n" + //
                                                "⡼⣷⡿⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣷⣄⠀⢀⣿⣿⠀⠀⠀⠀⢰⢻⣾⢇\n" + //
                                                "⢳⣌⠇⣿⠀⠀⠀⠀⠀⠀⣴⢶⣤⣀⡀⠀⠈⢻⣷⣾⣿⠏⠀⠀⠀⠀⣿⠸⣡⡞\n" + //
                                                "⠀⡿⢷⣿⡸⣄⠀⢀⣴⡾⠉⠀⠈⠛⠿⢿⣿⣿⡿⠿⣷⣄⠀⠀⢠⡇⣿⡾⢛⠀\n" + //
                                                "⠀⠘⢦⣝⡣⢿⡦⡈⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠋⢀⣴⡿⣘⣭⡶⠃⠀\n" + //
                                                "⠀⠀⠀⠹⣛⠿⠷⡹⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⢟⠾⠟⣛⠝⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠈⠛⡿⠿⠶⢛⣫⣤⡶⣒⡶⠶⣖⠶⣶⣍⣛⠚⠿⣟⠛⠁⠀⠀⠀⠀\n" + //
                                                "⠀⠀⠀⠀⠀⠀⠈⠙⠛⠛⠋⢡⠞⠁⠀⠀⠈⠻⣮⠙⠛⠛⠋⠁⠀⠀⠀⠀⠀⠀";
                                                System.out.println(text);
                }

                     message msg = new message(uname, text, ucolor);
                msg.writemessage(conn);
                
                
            }

        } catch (SQLException e) {
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
        sc.close();
    }
}