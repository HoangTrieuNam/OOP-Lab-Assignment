package io;

import java.util.List;

public class IOInterface {
    private static IOInterface instance = null;

    private IOInterface() {}

    public static IOInterface getInstance() {
        if (instance == null) {
            instance = new IOInterface();
        }
        return instance;
    }

    public void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Register (Customer)");
        System.out.println("3. Quit");
    }

    public void showAdminMenu() {
        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. View All Products");
        System.out.println("2. View All Customers");
        System.out.println("3. View All Orders");
        System.out.println("4. Delete All Data");
        System.out.println("5. Logout");
    }

    public void showCustomerMenu() {
        System.out.println("\n--- CUSTOMER MENU ---");
        System.out.println("1. View Profile");
        System.out.println("2. View Products");
        System.out.println("3. Place Order");
        System.out.println("4. View My Orders");
        System.out.println("5. Logout");
    }

    public void printError(String source, String message) {
        System.out.println("[ERROR][" + source + "]: " + message);
    }

    public void printMessage(String message) {
        System.out.println("[INFO]: " + message);
    }

    public void printObject(Object obj) {
        if (obj != null) {
            System.out.println(obj.toString());
        } else {
            System.out.println("No data to display.");
        }
    }

    public void printList(List<?> list, String title) {
        System.out.println("\n--- " + title + " ---");
        if (list == null || list.isEmpty()) {
            System.out.println("No records found.");
        } else {
            int i = 1;
            for (Object item : list) {
                System.out.println(i++ + ". " + item);
            }
        }
    }
}