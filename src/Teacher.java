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


        static void markentering(int userid) {

            System.out.println("Mark Entering System");
            System.out.println(".........................");

            System.out.print("Enter Student ID : ");
            int id = scan.nextInt();
            scan.nextLine();
            while (true) {
                System.out.print("Enter Course Code : ");
                String code = scan.nextLine();

                System.out.print("Enter Marks : ");
                int marks = scan.nextInt();

                try (Connection cour = DriverManager.getConnection(H2Connection.url)) {

                    PreparedStatement ps2 = cour.prepareStatement("SELECT 1 FROM STUDENT WHERE STUDENT_ID = ?");
                    ps2.setInt(1, id);
                    ResultSet rs2 = ps2.executeQuery();
                    if (!rs2.next()) {
                        System.out.println("No Student with this ID");
                        return;
                    }
                    PreparedStatement ps3 = cour.prepareStatement("SELECT 1 FROM COURSE WHERE COURSE_ID = ?");
                    ps3.setString(1, code);
                    ResultSet rs3 = ps3.executeQuery();
                    if (!rs3.next()) {
                        System.out.println("No Course with this Code");
                        return;
                    }

                    PreparedStatement ps4 = cour.prepareStatement("SELECT TEACHER_ID FROM TEACHER WHERE USER_ID = ?");
                    ps4.setInt(1, userid);
                    ResultSet rs4 = ps4.executeQuery();
                    int teacherid = 0;
                    if (rs4.next()) {
                        teacherid = rs4.getInt("TEACHER_ID");

                    }


                    String sql = "INSERT INTO GRADE (STUDENT_ID,COURSE_CODE,MARKS,TEACHER_ID) VALUES (?,?,?,?)";
                    PreparedStatement ps = cour.prepareStatement(sql);

                    ps.setInt(1, id);
                    ps.setString(2, code);
                    ps.setInt(3, marks);
                    ps.setInt(4, teacherid);

                    ps.executeUpdate();
                    System.out.println("Successfully added marks for Student ID: ");

                } catch (Exception e) {
                    System.err.println("Database Error: " + e.getMessage());
                }
                System.out.println("If you want to exit press number 1");
                int a = scan.nextInt();
                if (a == 1){
                    break;
                }
            }
        }



    }
