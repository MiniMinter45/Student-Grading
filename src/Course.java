import java.sql.*;

import java.sql.DriverManager;
import java.util.Scanner;

public class Course {
    static Scanner crse = new Scanner(System.in);

    static void addcourse(int userid){
        Scanner crse = new Scanner(System.in);

        System.out.println("Add courses to the system");
        System.out.println(".........................");
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


        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
    static void seecourse(){

        try(Connection conn1 = DriverManager.getConnection(H2Connection.url)) {

            PreparedStatement ps3 = conn1.prepareStatement("SELECT * FROM COURSE");
            ResultSet rs2 = ps3.executeQuery();

          while (rs2.next()){
              System.out.println("Course Name  : " + rs2.getString("COURSE_NAME"));
              System.out.println("Course Code  :" + rs2.getString("COURSE_CODE"));
              System.out.println("..................................");

          }

        } catch (SQLException e) {

        }

    }
    static void addstoc(){

        System.out.println("Enter Student ID : ");
        int id = crse.nextInt();
        System.out.println("Enter Course Code  :");

    }

}
