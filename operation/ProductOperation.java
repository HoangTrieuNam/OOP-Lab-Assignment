package operation;

import model.Product;

import java.io.*;
import java.util.*;

public class ProductOperation {
    private static ProductOperation instance;
    private final String PRODUCT_FILE = "data/products.txt";

    private ProductOperation() {}

    public static ProductOperation getInstance() {
        if (instance == null) {
            instance = new ProductOperation();
        }
        return instance;
    }

    public boolean addProduct(Product product) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE, true))) {
            bw.write(product.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Simplified: return default product for each line
                productList.add(new Product());  // You can implement real parsing if needed
            }
        } catch (IOException ignored) {}
        return productList;
    }

    public boolean deleteProductById(String productId) {
        File inputFile = new File(PRODUCT_FILE);
        File tempFile = new File("data/temp_products.txt");
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("\"pro_id\":\"" + productId + "\"")) {
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

    public void deleteAllProducts() {
        try {
            new FileWriter(PRODUCT_FILE, false).close();  // Overwrite file with nothing
        } catch (IOException ignored) {}
    }
}
