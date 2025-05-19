package operation;

import model.Customer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerOperation {
    private static CustomerOperation instance;
    private final String USER_FILE = "data/users.txt";

    private CustomerOperation() {}

    public static CustomerOperation getInstance() {
        if (instance == null) {
            instance = new CustomerOperation();
        }
        return instance;
    }

    public boolean validateEmail(String userEmail) {
        return userEmail.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

    public boolean validateMobile(String userMobile) {
        return userMobile.matches("^(04|03)\\d{8}$");
    }

    public boolean registerCustomer(String userName, String userPassword, String userEmail, String userMobile) {
        UserOperation userOp = UserOperation.getInstance();

        if (!userOp.validateUsername(userName) || !userOp.validatePassword(userPassword)
                || !validateEmail(userEmail) || !validateMobile(userMobile)
                || userOp.checkUsernameExist(userName)) {
            return false;
        }

        String userId = userOp.generateUniqueUserId();
        String registerTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
        String encryptedPassword = userOp.encryptPassword(userPassword);

        Customer customer = new Customer(userId, userName, encryptedPassword, registerTime, "customer", userEmail, userMobile);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(customer.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void deleteAllCustomers() {
        try {
            File inputFile = new File(USER_FILE);
            File tempFile = new File("data/temp_users.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("\"user_role\":\"customer\"")) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } catch (IOException ignored) {}
    }
}