package main;

import io.IOInterface;
import model.*;
import operation.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final IOInterface io = IOInterface.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    private static final UserOperation userOp = UserOperation.getInstance();
    private static final CustomerOperation customerOp = CustomerOperation.getInstance();
    private static final AdminOperation adminOp = AdminOperation.getInstance();
    private static final ProductOperation productOp = ProductOperation.getInstance();
    private static final OrderOperation orderOp = OrderOperation.getInstance();

    private static User currentUser = null;

    public static void main(String[] args) {
        adminOp.registerAdmin();  // Ensure admin exists

        boolean running = true;
        while (running) {
            io.showMainMenu();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> login();
                case "2" -> registerCustomer();
                case "3" -> {
                    io.printMessage("Exiting... Goodbye!");
                    running = false;
                }
                default -> io.printError("Main Menu", "Invalid option.");
            }
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = userOp.login(username, password);
        if (currentUser == null) {
            io.printError("Login", "Invalid credentials.");
            return;
        }

        io.printMessage("Welcome, " + currentUser.getUserName() + "!");
        if ("admin".equals(currentUser.getUserRole())) {
            adminMenu();
        } else {
            customerMenu();
        }
        currentUser = null; // logout after menu ends
    }

    private static void registerCustomer() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mobile: ");
        String mobile = scanner.nextLine();

        boolean success = customerOp.registerCustomer(username, password, email, mobile);
        if (success) io.printMessage("Registration successful!");
        else io.printError("Register", "Failed! Check inputs or username taken.");
    }

    private static void adminMenu() {
        boolean stay = true;
        while (stay) {
            io.showAdminMenu();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> io.printList(productOp.getAllProducts(), "All Products");
                case "2" -> io.printMessage("Listing all customers not implemented fully.");
                case "3" -> io.printList(orderOp.getAllOrders(), "All Orders");
                case "4" -> {
                    productOp.deleteAllProducts();
                    orderOp.deleteAllOrders();
                    customerOp.deleteAllCustomers();
                    io.printMessage("All data deleted.");
                }
                case "5" -> {
                    io.printMessage("Logging out...");
                    stay = false;
                }
                default -> io.printError("Admin Menu", "Invalid option.");
            }
        }
    }

    private static void customerMenu() {
        boolean stay = true;
        while (stay) {
            io.showCustomerMenu();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> io.printObject(currentUser);
                case "2" -> io.printList(productOp.getAllProducts(), "Available Products");
                case "3" -> {
                    System.out.print("Enter Product ID to order: ");
                    String pid = scanner.nextLine();
                    boolean ordered = orderOp.createOrder(currentUser.getUserId(), pid);
                    if (ordered) io.printMessage("Order placed successfully.");
                    else io.printError("Order", "Failed to place order.");
                }
                case "4" -> io.printList(orderOp.getAllOrders(), "Your Orders (simplified list)");
                case "5" -> {
                    io.printMessage("Logging out...");
                    stay = false;
                }
                default -> io.printError("Customer Menu", "Invalid option.");
            }
        }
    }
}