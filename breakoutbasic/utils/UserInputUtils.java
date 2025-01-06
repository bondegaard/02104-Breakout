package breakoutbasic.utils;

import java.util.Scanner;

public class UserInputUtils {
    public static int getUserInputInteger() {
        try (Scanner scan = new Scanner(System.in)) {
            return Integer.parseInt(scan.nextLine());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int getUserInputInteger(String msg) {
        System.out.println(msg);

        return getUserInputInteger();
    }
}
