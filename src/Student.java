import java.util.Scanner;
import java.sql.*;

public class Student {


    static Scanner scan = new Scanner(System.in);

    static void studentMenu(int userid) {

        while (true) {
            System.out.println("\n------ Student Menu ------");
            System.out.println("1. View My Profile");
            System.out.println("2. View My Courses");
            System.out.println("3. View Results");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    viewProfile(userid);
                    break;
                case 2:
                    System.out.println("view mycourse");
                    break;
                case 3:
                    System.out.println("view result");
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
    static void viewProfile(int userid){



        try (Connection conn = DriverManager.getConnection(H2Connection.url)){

            String query = "SELECT * FROM STUDENT WHERE USER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("\n------ My Profile ------");
                System.out.println("Student ID : " + rs.getInt("STUDENT_ID"));
                System.out.println("First Name : " + rs.getString("FIRST_NAME"));
                System.out.println("Last Name  : " + rs.getString("LAST_NAME"));
                System.out.println("Email      : " + rs.getString("EMAIL"));
            } else {
                System.out.println("Profile not found.");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}