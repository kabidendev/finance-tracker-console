package com.company.utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (Exception e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }
}
