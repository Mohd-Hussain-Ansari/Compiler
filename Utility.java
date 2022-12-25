import java.util.Scanner;

public class Utility {

    public static void showError(String message) {
        System.out.println("Output: Your string is not valid");
        System.out.println("Reason: " + message);
    }

    public static String getInput(String message, Scanner sc) {
        System.out.print(message);
        String inputString = sc.nextLine();
        while (inputString.length() == 0) {
            System.out.println("Input can not be empty");
            System.out.print(message);
            inputString = sc.nextLine();
        }
        return inputString;
    }

}
