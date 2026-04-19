import java.util.Scanner;
import java.sql.*;

public class Teacher {


    static Scanner scan = new Scanner(System.in);

    static void teacherMenu(int userid){

        while (true) {
            System.out.println("\n------ Teacher  Menu ------");
            System.out.println("1. View My Profile");
            System.out.println("2. Add New Courses");
            System.out.println("3. See Courses");
            System.out.println("4. View Student Profile");
            System.out.println("5. Add Student to Courses");
            System.out.println("6. Enter Student Marks");
            System.out.println("7. Logout");
            System.out.println(userid);
            System.out.print("Enter choice: ");


            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    profile(userid);
                    break;
                case 2:
                    Course.addcourse(userid);
                    break;
                case 3:
                    Course.seecourse();
                    break;
                case 4:
                    Student.viewProfile(userid);
                    break;
                case 5:
                    Course.addstoc();
                    break;
                case 6:
                    System.out.println("Enter Marks");
                    return;
                case 7:
                    System.out.println("Logging Out");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    //-----------Showing teacher profile-------------

    static void profile(int userid){

        try (Connection conn = DriverManager.getConnection(H2Connection.url)){

            String query = "SELECT * FROM TEACHER WHERE USER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,userid);

            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    System.out.println("\n------ My Profile ------");
                    System.out.println("Teacher ID : " + rs.getInt("TEACHER_ID"));
                    System.out.println("First Name : " + rs.getString("FIRST_NAME"));
                    System.out.println("Last Name  : " + rs.getString("LAST_NAME"));
                    System.out.println("Email      : " + rs.getString("EMAIL"));
                } else {
                    System.out.println("Profile not found.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    static void addmark(){



    }
}