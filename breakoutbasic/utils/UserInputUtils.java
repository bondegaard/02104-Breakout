package breakoutbasic.utils;

import java.util.Scanner;

/**
 * Utils to get user input
 */
public class UserInputUtils {

    /**
     * Get a number from the user in the terminal
     * @param textStatement what kind of text
     * @return a number or run it again if the number was invalid
     */

    public static Scanner scan = new Scanner(System.in);

    public static int getUserInputInteger(String textStatement) {
        try {
            System.out.print(textStatement);
            return scan.nextInt();

        } catch (Exception e) {
            scan.next();
            return getUserInputInteger(textStatement);
        }
    }

}
