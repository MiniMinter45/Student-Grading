import javax.sound.midi.Soundbank;
import java.util.Scanner;
import java.sql.*;
import ConsoleTable.ct4j;

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
                    viewcourse(userid);
                    break;
                case 3:
                    result(userid);
                    break;
                case 4:
                    Main.main();
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
        ct4j table = new ct4j();
            try (Connection conn2 = DriverManager.getConnection(H2Connection.url)){

                PreparedStatement ps2 = conn2.prepareStatement("SELECT STUDENT_ID, FIRST_NAME, LAST_NAME FROM STUDENT WHERE USER_ID = ?");
                ps2.setInt(1,userid);
                ResultSet rs2 = ps2.executeQuery();
                int stid = 0;
                String sname= "";
                if (rs2.next()){
                    stid = rs2.getInt("STUDENT_ID");
                    sname = rs2.getString("FIRST_NAME") + " " + rs2.getString("LAST_NAME");
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
                    table.setHeader("Course Name", "Course Code", "Grade");
                    while (rs3.next()){
                       String name = rs3.getString("COURSE_NAME");
                        String code = rs3.getString("COURSE_CODE");
                        String grade = rs3.getString("GRADE");
                        table.addRow(name,code,grade);
                        table.setHorizontalSeparator('-');
                        table.setVerticalSeparator('|');
                        table.setCornerJoint('+');
                        table.setUppercaseHeaders(true);
                }
                    table.printTable();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    static void viewcourse(int userid){
        ct4j table = new ct4j();

        try(Connection conn3 = DriverManager.getConnection(H2Connection.url)){
            PreparedStatement ps3 = conn3.prepareStatement("SELECT STUDENT_ID FROM STUDENT WHERE USER_ID = ?");
            ps3.setInt(1,userid);
            ResultSet rs3 = ps3.executeQuery();
            int sid = 0;
            if (rs3.next()){
                sid = rs3.getInt("STUDENT_ID");
            }
            String sql = "SELECT e.COURSE_CODE, c.COURSE_NAME " +
                    "FROM ENROLLMENT e JOIN COURSE c " +
                    "ON e.COURSE_CODE = c.COURSE_CODE " +
                    "WHERE e.STUDENT_ID = ?";
            PreparedStatement ps4 = conn3.prepareStatement(sql);
            ps4.setInt(1,sid);
            ResultSet rs4 = ps3.executeQuery();
           table.setHeader("Course Name","Course Code");
            while(rs4.next()){
                String code = rs4.getString("COURSE_CODE");
                String name = rs4.getString("COURSE_NAME");
                table.addRow(name,code);
                table.setHorizontalSeparator('-');
                table.setVerticalSeparator('|');
                table.setCornerJoint('+');
                table.setUppercaseHeaders(true);


            }
            table.printTable();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}