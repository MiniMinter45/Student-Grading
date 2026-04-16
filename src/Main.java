import java.util.Scanner;

public class   Main {

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
                    int userid = User.login();

                    if (userid != -1) {
                        System.out.println("-----------------------------");
                        System.out.println("Select your role to continue: ");
                        System.out.println("1.Teacher");
                        System.out.println("2.Student");
                        System.out.print("Choice: ");

                        int role1 = first.nextInt();
                        switch (role1){
                            case 1:
                                Teacher.teacherMenu(userid); //go to teacher menu
                                break;
                            case 2:
                                Student.viewProfile(userid);   // ✅ go to Student class
                                break;
                            default:
                                System.out.println("Invalid Role Selected.");
                        }

                    } else {
                        System.out.println("Login failed. Try again.");
                    }
                    break;
                case 2:
                    if (User.register()) {
                        User.login();
                        break;
                    }
                default:
                    break;
            }
            System.out.println("enter number 1 to back : ");
            int m = first.nextInt();
        if (m == 1){

            break;

        }


            }
        }
    }
