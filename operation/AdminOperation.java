package operation;

import model.Admin;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminOperation {
    private static AdminOperation instance;
    private final String USER_FILE = "data/users.txt";

    private AdminOperation() {}

    public static AdminOperation getInstance() {
        if (instance == null) {
            instance = new AdminOperation();
        }
        return instance;
    }

    public void registerAdmin() {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"user_role\":\"admin\"")) {
                    reader.close();
                    return; // Admin already exists
                }
            }
            reader.close();

            // Register default admin
            String userId = UserOperation.getInstance().generateUniqueUserId();
            String userName = "admin";
            String password = "admin123";
            String encryptedPassword = UserOperation.getInstance().encryptPassword(password);
            String registerTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
            Admin admin = new Admin(userId, userName, encryptedPassword, registerTime, "admin");

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(admin.toString());
            writer.newLine();
            writer.close();

            System.out.println("Default admin created (username: admin, password: admin123)");

        } catch (IOException e) {
            System.out.println("Error creating admin: " + e.getMessage());
        }
    }
}