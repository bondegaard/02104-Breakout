package breakoutbasic.utils;

import java.util.Scanner;

/**
 * Utils to get user input
 */
public class UserInputUtils {

    /**
     * Get a number from the user in the terminal
     * @return a number or -1 if the number was invalid
     */
    public static int getUserInputInteger() {
        try (Scanner scan = new Scanner(System.in)) {
            return Integer.parseInt(scan.nextLine());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
