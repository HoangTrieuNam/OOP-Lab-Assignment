import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private List<User> user = new ArrayList<>();
    private List<Product> product = new ArrayList<>();
    private List<Order> order = new ArrayList<>();

    // Class to represent User data
    static class User {
        String userId;
        String userName;
        String userPassword;
        String userRegisterTime;
        String userRole;
        String userEmail;
        String userMobile;

        public User(JSONObject json) throws JSONException {
            this.userId = json.getString("user_id");
            this.userName = json.getString("user_name");
            this.userPassword = json.getString("user_password");
            this.userRegisterTime = json.getString("user_register_time");
            this.userRole = json.getString("user_role");
            this.userEmail = json.has("user_email") ? json.getString("user_email") : null;
            this.userMobile = json.has("user_mobile") ? json.getString("user_mobile") : null;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", userRole='" + userRole + '\'' +
                    (userEmail != null ? ", userEmail='" + userEmail + '\'' : "") +
                    (userMobile != null ? ", userMobile='" + userMobile + '\'' : "") +
                    '}';
        }
    }

    // Class to represent Product data
    static class Product {
        String proId;
        String proModel;
        String proCategory;
        String proName;
        double proCurrentPrice;
        double proRawPrice;
        double proDiscount;
        int proLikesCount;

        public Product(JSONObject json) throws JSONException {
            this.proId = json.getString("pro_id");
            this.proModel = json.getString("pro_model");
            this.proCategory = json.getString("pro_category");
            this.proName = json.getString("pro_name");
            this.proCurrentPrice = json.getDouble("pro_current_price");
            this.proRawPrice = json.getDouble("pro_raw_price");
            this.proDiscount = json.getDouble("pro_discount");
            this.proLikesCount = json.getInt("pro_likes_count");
        }

        @Override
        public String toString() {
            return "Product{" +
                    "proId='" + proId + '\'' +
                    ", proModel='" + proModel + '\'' +
                    ", proName='" + proName + '\'' +
                    ", proCurrentPrice=" + proCurrentPrice +
                    ", proDiscount=" + proDiscount +
                    '}';
        }
    }

    // Class to represent Order data
    static class Order {
        String orderId;
        String userId;
        String proId;
        String orderTime;

        public Order(JSONObject json) throws JSONException {
            this.orderId = json.getString("order_id");
            this.userId = json.getString("user_id");
            this.proId = json.getString("pro_id");
            this.orderTime = json.getString("order_time");
        }

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", proId='" + proId + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    '}';
        }
    }

    public DataReader(String usersFilePath, String productsFilePath, String ordersFilePath) {
        readUsersData(usersFilePath);
        readProductsData(productsFilePath);
        readOrdersData(ordersFilePath);
    }

    private void readUsersData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject userJson = new JSONObject(line);
                users.add(new User(userJson));
            }
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error parsing user JSON: " + e.getMessage());
        }
    }

    private void readProductsData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject productJson = new JSONObject(line);
                products.add(new Product(productJson));
            }
        } catch (IOException e) {
            System.err.println("Error reading products file: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error parsing product JSON: " + e.getMessage());
        }
    }

    private void readOrdersData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject orderJson = new JSONObject(line);
                orders.add(new Order(orderJson));
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error parsing order JSON: " + e.getMessage());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static void main(String[] args) {
        String usersFilePath = "data/users.txt";
        String productsFilePath = "data/products.txt";
        String ordersFilePath = "data/orders.txt";

        DataReader dataReader = new DataReader(usersFilePath, productsFilePath, ordersFilePath);

        System.out.println("--- Users ---");
        dataReader.getUsers().forEach(System.out::println);

        System.out.println("\n--- Products ---");
        dataReader.getProducts().forEach(System.out::println);

        System.out.println("\n--- Orders ---");
        dataReader.getOrders().forEach(System.out::println);
    }
}