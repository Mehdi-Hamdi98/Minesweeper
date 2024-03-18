package org.example;
import java.util.*;
public class InputValidator {
    public static int readIntegerInput(Scanner reader) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(reader.nextLine());
                if (input >= 0) {
                    break;
                } else {
                    System.out.println("Please enter a non-negative integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
        return input;
    }

}
