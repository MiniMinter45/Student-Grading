import java.util.Scanner;

public class Main {

    static Scanner first = new Scanner(System.in);
    static void main() {
        User ps = new User();

            H2Connection.connect();
        while (true) {
            System.out.println("Do you have a Account");
            System.out.println("1.Yes");
            System.out.println("2.No");
            System.out.print("Enter Number : ");
            int a = first.nextInt();
            switch (a) {

                case 1:
                    User.login();
                    break;
                case 2:
                    if (User.register()) {
                        User.login();
                        break;
                    }
                default:
                    break;
            }
            int m = first.nextInt();
        if (m == 1){
            break;
        }

            }
        }
    }
