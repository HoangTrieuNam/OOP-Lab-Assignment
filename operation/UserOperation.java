package operation;

import model.*;
import java.io.*;
import java.util.Random;

public class UserOperation {
    private static UserOperation instance;
    private final String USER_FILE = "data/users.txt";
    private final Random random = new Random();

    private UserOperation() {}

    public static UserOperation getInstance() {
        if (instance == null) {
            instance = new UserOperation();
        }
        return instance;
    }

    public String generateUniqueUserId() {
        StringBuilder id = new StringBuilder("u_");
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public String encryptPassword(String userPassword) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder encrypted = new StringBuilder("^^");
        for (char ch : userPassword.toCharArray()) {
            encrypted.append(chars.charAt(random.nextInt(chars.length())));
            encrypted.append(chars.charAt(random.nextInt(chars.length())));
            encrypted.append(ch);
        }
        encrypted.append("$$");
        return encrypted.toString();
    }

    public String decryptPassword(String encryptedPassword) {
        if (!encryptedPassword.startsWith("^^") || !encryptedPassword.endsWith("$$")) return null;
        String content = encryptedPassword.substring(2, encryptedPassword.length() - 2);
        StringBuilder original = new StringBuilder();
        for (int i = 2; i < content.length(); i += 3) {
            original.append(content.charAt(i));
        }
        return original.toString();
    }

    public boolean checkUsernameExist(String userName) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"user_name\":\"" + userName + "\"")) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    public boolean validateUsername(String userName) {
        return userName.matches("[A-Za-z_]{5,}");
    }

    public boolean validatePassword(String userPassword) {
        return userPassword.matches("(?=.*[A-Za-z])(?=.*\\d).{5,}");
    }

    public User login(String userName, String userPassword) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"user_name\":\"" + userName + "\"")) {
                    String encrypted = line.split("\"user_password\":\"")[1].split("\"")[0];
                    String decrypted = decryptPassword(encrypted);
                    if (decrypted != null && decrypted.equals(userPassword)) {
                        if (line.contains("\"user_role\":\"admin\"")) {
                            return new Admin(); // Simplified
                        } else {
                            return new Customer(); // Simplified
                        }
                    }
                }
            }
        } catch (IOException ignored) {}
        return null;
    }
}
