package operation;

import model.*;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String id = "u_";
        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }
        return id;
    }

    public String encryptPassword(String userPassword) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        int len = userPassword.length() * 2;
        for (int i = 0; i < len; i++) {
            randomString.append(chars.charAt(random.nextInt(chars.length())));
        }

        StringBuilder encrypted = new StringBuilder("^^");
        for (int i = 0; i < userPassword.length(); i++) {
            encrypted.append(randomString.charAt(2 * i));
            encrypted.append(randomString.charAt(2 * i + 1));
            encrypted.append(userPassword.charAt(i));
        }
        encrypted.append("$$");
        return encrypted.toString();
    }

    public String decryptPassword(String encryptedPassword) {
        if (!encryptedPassword.startsWith("^^") || !encryptedPassword.endsWith("$$")) {
            return null;
        }

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
                if (line.contains("\"user_name\":\"" + userName + "\"")) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
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
                            return new Admin(); // stub, replace with full parsing
                        } else {
                            return new Customer(); // stub, replace with full parsing
                        }
                    }
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}

