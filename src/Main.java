import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    //login attenmps calculator

    static void loginattempt(){
        int uid = -1;
        System.out.println("Login");
        for (int i = 4; i >= 0; --i){
            uid = User.login();
            if (uid != -1){
                break;
            }
            System.out.println("Wrong Username or Password!");
            System.out.println(i + " Attempts Left!");

        }
    }

    static Scanner first = new Scanner(System.in);

    //main method it's start the program by running login attemp or register

    static void main() {
        H2Connection.connect();

        System.out.println("Do You Have A Account?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("3.Exit");
        System.out.print("Enter Number : ");
        int a = first.nextInt();
        if (a == 1){
            loginattempt();

        } else if (a == 2) {
            if (User.register()){
                System.out.println("Registration Successful! Login Now");
                loginattempt();

            }

        } else if (a == 3) {
            System.out.println();
            System.out.println("Bye");
            System.exit(0);
        }

    }
    }
 