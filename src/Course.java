
import ConsoleTable.ct4j;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;


public class Course {
    static ct4j table = new ct4j();
    static Scanner crse = new Scanner(System.in);

    static void addcourse(int userid){
        Scanner crse = new Scanner(System.in);

        System.out.println("Add courses to the system");
        System.out.println(".........................");
        System.out.println("(Course Code should be consist of three letters and four numbers. letters should be capital. (eg: ABC2003))");
        System.out.print("Enter Course Code : ");
        String code = crse.nextLine();
        System.out.print("Enter Course Name : ");
        String nme = crse.nextLine();

        try (Connection cour = DriverManager.getConnection(H2Connection.url)){

            String sql = "SELECT TEACHER_ID FROM TEACHER WHERE USER_ID = ?";
            PreparedStatement ps = cour.prepareStatement(sql);
            ps.setInt(1,userid);

            ResultSet rs = ps.executeQuery();
            int teacherid = 0;
            if (rs.next()){

            teacherid = rs.getInt("TEACHER_ID");
            }
            String sql2 = "INSERT INTO COURSE(COURSE_NAME, TEACHER_ID,COURSE_CODE) VALUES (?,?,?)";
            PreparedStatement ps2 = cour.prepareStatement(sql2);
            ps2.setString(1,nme);
            ps2.setInt(2,teacherid);
            ps2.setString(3,code);
            ps2.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    static void seecourse(){


        try(Connection conn1 = DriverManager.getConnection(H2Connection.url)) {

            PreparedStatement ps3 = conn1.prepareStatement("SELECT * FROM COURSE");
            ResultSet rs2 = ps3.executeQuery();
            table.setHeader("Course Name", "Course Code");
          while (rs2.next()){
             String name =  rs2.getString("COURSE_NAME");
              String code = rs2.getString("COURSE_CODE");
                table.addRow(name,code);
              table.setHorizontalSeparator('-');
              table.setVerticalSeparator('|');
              table.setCornerJoint('+');
              table.setUppercaseHeaders(true);


          }

        } catch (Exception e) {
            e.getMessage();

        }

    }
    static void addstoc(){
        while (true) {


            System.out.print("Enter Student ID  : ");
            int id = crse.nextInt();
            crse.nextLine();
            System.out.print("Enter Course Code :");
            String code = crse.nextLine();
            try (Connection conn2 = DriverManager.getConnection(H2Connection.url)) {
                PreparedStatement ps3 = conn2.prepareStatement("SELECT 1 FROM STUDENTS WHERE STUDENT_ID = ?");
                ps3.setInt(1, id);
                ResultSet rs3 = ps3.executeQuery();
                if (!rs3.next()) {

                    System.out.println("No Student with this ID");
                    return;
                }

                PreparedStatement ps4 = conn2.prepareStatement("SELECT 1 FROM COURSE WHERE COURSECODE = ?");
                ps4.setString(1, code);
                ResultSet rs4 = ps4.executeQuery();

                if (!rs4.next()) {

                    System.out.println("No Course with this Code");
                    return;
                }

                PreparedStatement ps5 = conn2.prepareStatement("INSERT INTO ENROLLMENT(STUDENT_ID,COURSE_CODE)VALUES (?,?)");
                ps5.setInt(1, id);
                ps5.setString(2, code);
                ps5.executeUpdate();
                System.out.println("Student Enrolled Successfully");
                System.out.println(".............................");
                System.out.println();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.println("Do you want to add another?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            int o = crse.nextInt();
            if (o==2){
                break;
            }


        }
    }


}
