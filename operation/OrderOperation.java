package operation;

import model.Order;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderOperation {
    private static OrderOperation instance;
    private final String ORDER_FILE = "data/orders.txt";
    private final Random random = new Random();

    private OrderOperation() {}

    public static OrderOperation getInstance() {
        if (instance == null) {
            instance = new OrderOperation();
        }
        return instance;
    }

    public String generateUniqueOrderId() {
        return "o_" + String.format("%05d", random.nextInt(100000));
    }

    public boolean createOrder(String userId, String proId) {
        String orderId = generateUniqueOrderId();
        String orderTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
        Order order = new Order(orderId, userId, proId, orderTime);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDER_FILE, true))) {
            bw.write(order.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                orders.add(new Order()); // Stub: Replace with real parsed order
            }
        } catch (IOException ignored) {}
        return orders;
    }

    public boolean deleteOrder(String orderId) {
        File inputFile = new File(ORDER_FILE);
        File tempFile = new File("data/temp_orders.txt");
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("\"order_id\":\"" + orderId + "\"")) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            return false;
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
        return deleted;
    }

    public void deleteAllOrders() {
        try {
            new FileWriter(ORDER_FILE, false).close();
        } catch (IOException ignored) {}
    }
}