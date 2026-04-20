import javax.sound.midi.Soundbank;
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
            System.out.println(userid);
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

    static void result(int userid){
            try (Connection conn2 = DriverManager.getConnection(H2Connection.url)){

                PreparedStatement ps2 = conn2.prepareStatement("SELECT STUDENT_ID, STUDENT_NAME FROM STUDENT WHERE USER_ID = ?");
                ps2.setInt(1,userid);
                ResultSet rs2 = ps2.executeQuery();
                int stid = 0;
                String sname= "";
                if (rs2.next()){
                    stid = rs2.getInt("STUDENT_ID");
                    sname = rs2.getString("STUDENT_NAME");
                    System.out.println("Student ID   : " + stid);
                    System.out.println("Student Name : " + sname);
                }
                    String query1= "SELECT g.GRADE, g.COURSE_CODE, c.COURSE_NAME " +
                                    "FROM GRADE g JOIN COURSE c " +
                                    "ON g.COURSE_CODE = c.COURSE_CODE" +
                                    " WHERE g.STUDENT_ID = ? ";
                    PreparedStatement ps3 = conn2.prepareStatement(query1);
                    ps3.setInt(1, stid);
                    ResultSet rs3 = ps3.executeQuery();
                    while (rs3.next()){
                        System.out.println("Course Name : " + rs3.getString("COURSE_NAME")
                                            + " | Course Code : " + rs3.getString("COURSE_CODE")
                                            + " | Grade : " + rs3.getString("GRADE"));


                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

}