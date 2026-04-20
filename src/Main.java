import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
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
    static void main() {
        H2Connection.connect();

        System.out.println("Do You Have A Account?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int a = first.nextInt();
        if (a == 1){
            loginattempt();

        } else if (a == 2) {
            if (User.register()){
                System.out.println("Registration Successful! Login Now");
                loginattempt();

            }

        }

    }
    }
 