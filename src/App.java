import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
// public class App {

//     private static final String SELECT_QUERY = "SELECT * FROM reg";

//     public static void main(String[] args) {
//         try (Connection conn = DBConnection.getConnection();
//                 Statement stmt = conn.createStatement(); // 3. CREATE STATEMENT
//                 ResultSet rs = stmt.executeQuery(SELECT_QUERY)) {

//             System.out.println("--- Results from Database ---");

//             // 4. PROCESS THE RESULTS (Iterate through the ResultSet)
//             while (rs.next()) {
//                 // Get data by column name
//                 int id = rs.getInt("id");
//                 String name = rs.getString("name");
//                 int age = rs.getInt("age");

//                 System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
//             }

//         } catch (SQLException e) {
//             // If the connection fails or the SQL is bad, the error is caught here.
//             System.err.println("Database operation failed!");
//             e.printStackTrace();
//         }
//     }
// }

public class App {

    private static final String SELECT_QUERY = "SELECT * FROM messages";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        sc.nextLine();

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement(); // 3. CREATE STATEMENT
                ResultSet rs = stmt.executeQuery(SELECT_QUERY)) {

            System.out.println("--- Results from Database ---");

            // 4. PROCESS THE RESULTS (Iterate through the ResultSet)
            boolean hasMessages = false;

            while (rs.next()) {
                hasMessages = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");

                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
            if (!hasMessages) {
                System.out.println("No messages.");
            }

        } catch (SQLException e) {
            // If the connection fails or the SQL is bad, the error is caught here.
            System.err.println("Database operation failed!");
            e.printStackTrace();
        }
        sc.close();
    }
}