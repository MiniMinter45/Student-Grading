import java.sql.*;
import java.util.Scanner;

class H2Connection {

    static final String url = "jdbc:h2:./data/database";


    public static void connect() {

        try {
            Class.forName("org.h2.Driver");

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    System.out.println("Connected to H2 successfully!");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("H2 Driver not found!");
        } catch (SQLException e) {
            System.out.println("no");
        }
    }
}

public class User{
    static Scanner scan = new Scanner(System.in);


    static boolean register() {
        int a;
        System.out.println("Chose A Role");
        System.out.println("1.Teacher");
        System.out.println("2.Student");
        System.out.print("Enter 1 or 2 : ");
        a = scan.nextInt();
        System.out.println("...................");
        if (a == 1){
            teacherReg();

        } else if (a == 2) {
            studentReg();

        }else {
            System.out.println("Enter a Valid Number");
        }

        return true;
    }

    static void teacherReg(){
        String Fname;
        String Lname;
        String Uname;
        String Pword;
        String Email;
        String role1 = "Teacher";

        System.out.println("............................");
        System.out.println("Welcome to Teacher Register");
        System.out.println("............................");
        scan.nextLine();

        System.out.print("Enter First Name   : ");
        Fname = scan.nextLine();

        System.out.print("Enter Last Name    : ");
        Lname = scan.nextLine();

        System.out.print("Enter Last Email   : ");
        Email = scan.nextLine();

        System.out.print("Create a Username  : ");
        Uname = scan.nextLine();

        System.out.print("Create a Password  : ");
        Pword = scan.nextLine();

        datainput(Fname, Lname, Email, Uname, Pword,role1);
        System.out.println("Registered Successfully");
        System.out.println("........................");



    }
    static void datainput(String Fname, String Lname,String Email, String Uname, String Pword,String role1){
        try (Connection conn = DriverManager.getConnection(H2Connection.url)){

            String loginin = "INSERT INTO USERS(USERNAME,PASSWORD,ROLE) VALUES (?,?,?)";
            PreparedStatement in2 = conn.prepareStatement(loginin, Statement.RETURN_GENERATED_KEYS);
            in2.setString(1,Uname);
            in2.setString(2,Pword);
            in2.setString(3,role1);
            in2.executeUpdate();

            ResultSet rs = in2.getGeneratedKeys();
            int userid = 0;
            if (rs.next()){
                userid = rs.getInt(1);
            }

            String input = "INSERT INTO TEACHER(USER_ID,FIRST_NAME,LAST_NAME,EMAIL) VALUES (?,?,?,?)";
            PreparedStatement in1 = conn.prepareStatement(input, Statement.RETURN_GENERATED_KEYS);
            in1.setInt(1,userid);
            in1.setString(2,Fname);
            in1.setString(3,Lname);
            in1.setString(4,Email);

            in1.executeUpdate();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void studentReg(){
        String Fname;
        String Lname;
        String Uname;
        String Pword;
        String Email;
        String role1 = "Student";

        System.out.println("............................");
        System.out.println("Welcome to Student Register");
        System.out.println("............................");
        scan.nextLine();

        System.out.print("Enter First Name   : ");
        Fname = scan.nextLine();

        System.out.print("Enter Last Name    : ");
        Lname = scan.nextLine();

        System.out.print("Enter Last Email   : ");
        Email = scan.nextLine();

        System.out.print("Create a Username  : ");
        Uname = scan.nextLine();

        System.out.print("Create a Password  : ");
        Pword = scan.nextLine();

        datainput2(Fname, Lname, Email, Uname, Pword,role1);
        System.out.println("Registered Successfully");
        System.out.println("...........................");


    }
    static void datainput2(String Fname, String Lname,String Email, String Uname, String Pword,String role1){
        try (Connection conn = DriverManager.getConnection(H2Connection.url)){

            String loginin = "INSERT INTO USERS(USERNAME,PASSWORD,ROLE) VALUES (?,?,?)";
            PreparedStatement in2 = conn.prepareStatement(loginin, Statement.RETURN_GENERATED_KEYS);
            in2.setString(1,Uname);
            in2.setString(2,Pword);
            in2.setString(3,role1);
            in2.executeUpdate();

            ResultSet rs = in2.getGeneratedKeys();
            int userid = 0;
            if (rs.next()){
                userid = rs.getInt(1);
            }

            String input = "INSERT INTO STUDENT(USER_ID,FIRST_NAME,LAST_NAME,EMAIL) VALUES (?,?,?,?)";
            PreparedStatement in1 = conn.prepareStatement(input, Statement.RETURN_GENERATED_KEYS);
            in1.setInt(1,userid);
            in1.setString(2,Fname);
            in1.setString(3,Lname);
            in1.setString(4,Email);

            in1.executeUpdate();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static int login(){
        int userid=0;
        String role1="";
        System.out.println("..........................");
        System.out.println("          Login           ");
        System.out.println("..........................");
        System.out.print("Enter Username : ");
        String username = scan.nextLine();
        System.out.print("Enter Password : ");
        String password = scan.nextLine();
        try(Connection conn = DriverManager.getConnection(H2Connection.url)){
            String check = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
            PreparedStatement user = conn.prepareStatement(check);
            user.setString(1,username);
            user.setString(2,password);

            ResultSet names = user.executeQuery();
            if(names.next()){
                System.out.println("Login Successful");
                userid = names.getInt("USER_ID");
                role1 = names.getString("ROLE");

                switch (role1){
                    case "Teacher":
                        Teacher.teacherMenu(userid);
                        break;
                    case "Student":
                        Student.studentMenu(userid);
                        break;
                }


            }else {
                System.out.println("Invalid Username or Password");
                return -1;
            }


            } catch (Exception e){
            e.printStackTrace();
        }



        return userid;
    }

}