import ConsoleTable.ct4j;
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
            System.out.println("7. Student List");
            System.out.println("8. Logout");
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
                    studentprof();
                    break;
                case 5:
                    Course.addstoc();
                    break;
                case 6:
                    markentering(userid);
                    break;
                case 8:
                    Main.main();
                    break;
                case 7:
                    studentlist();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
    static void studentlist(){
        ct4j table = new ct4j();
        try (Connection conn2 = DriverManager.getConnection(H2Connection.url)){
            PreparedStatement ps1 = conn2.prepareStatement("SELECT * FROM STUDENT");
            ResultSet rs2 = ps1.executeQuery();

           table.setHeader("ID","Name");
            while (rs2.next()){
                String name = rs2.getString("FIRST_NAME") +" "+ rs2.getString("LAST_NAME");
                String id = rs2.getString("STUDENT_ID");
                table.addRow(id,name);
                table.setHorizontalSeparator('-');
                table.setVerticalSeparator('|');
                table.setCornerJoint('+');
                table.setUppercaseHeaders(true);
            }
            table.printTable();

        } catch (Exception e) {
            e.getMessage();
        }


    }


    static void studentprof(){
        System.out.println("Enter Student ID");
        int a = scan.nextInt();
        try(Connection conn4 = DriverManager.getConnection(H2Connection.url)) {
            PreparedStatement ps4 = conn4.prepareStatement("SELECT USER_ID FROM STUDENT WHERE STUDENT_ID = ?");
            ps4.setInt(1,a);
            ResultSet rs4 = ps4.executeQuery();
            int u = 0;
            if (rs4.next()){
                u= rs4.getInt("USER_ID");
            }
            Student.viewProfile(u);

        } catch (Exception e) {
            e.getMessage();
        }


    }

    static void profile(int userid){

        try (Connection conn = DriverManager.getConnection(H2Connection.url)){

            String query = "SELECT * FROM TEACHER WHERE USER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,userid);

            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    System.out.println("\n------ My Profile ------");
                    System.out.println("Teacher ID : " + rs.getInt("TEACHER_ID"));
                    System.out.println("Full Name : " + rs.getString("FIRST_NAME") + rs.getString("LAST_NAME"));
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
                float marks = scan.nextInt();
                String grade = "";
                if (marks >= 90 && marks <=100){
                    grade = "A+";
                } else if (marks >= 85 && marks <= 89.9) {
                    grade = "A";
                } else if (marks >= 80 && marks <= 84.9) {
                    grade = "A-";
                } else if (marks >= 75 && marks <= 79.9) {
                    grade = "B+";
                } else if (marks >= 70 && marks <= 74.9) {
                    grade = "B";
                } else if (marks >= 65 && marks <= 69.9) {
                    grade = "B-";
                } else if (marks >= 60 && marks <= 64.9) {
                    grade = "C+";
                } else if (marks >= 55 && marks <= 59.9) {
                    grade = "C";
                } else if (marks >= 50 && marks <= 54.9) {
                    grade = "C-";
                } else if (marks >= 40 && marks <= 49.9) {
                    grade = "D";
                } else if (marks >= 0 && marks <= 39.9) {
                    grade = "F";
                }



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


                    String sql = "INSERT INTO GRADE (STUDENT_ID,COURSE_CODE,MARKS,TEACHER_ID,GRADE) VALUES (?,?,?,?,?)";
                    PreparedStatement ps = cour.prepareStatement(sql);

                    ps.setInt(1, id);
                    ps.setString(2, code);
                    ps.setFloat(3, marks);
                    ps.setInt(4, teacherid);
                    ps.setString(5,grade);

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
