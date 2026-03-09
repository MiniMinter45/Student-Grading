import java.util.Scanner;

public class GradeSystem {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter your mark:");
        int mark = input.nextInt();

        String grade;

        if (mark >= 75 && mark <= 100) {
            grade = "A";
        } else if (mark >= 65) {
            grade = "B";
        } else if (mark >= 55) {
            grade = "C";
        } else if (mark >= 35) {
            grade = "S";
        } else {
            grade = "Fail";
        }

        System.out.println("Grade = " + grade);
    }
}
