package lk.rental.util;

import java.util.Scanner;

public class InputUtil {
    private final Scanner sc = new Scanner(System.in);

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid integer value.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid numeric value.");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public int readMenuChoice(int min, int max) {
        while (true) {
            int choice = readInt("Enter choice (" + min + "-" + max + "): ");
            if (choice >= min && choice <= max) return choice;
            System.out.println("❌ Invalid menu selection. Try again.");
        }
    }
}