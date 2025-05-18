package model;

public class Order {
    private String orderId;
    private String userId;
    private String proId;
    private String orderTime;

    public Order(String orderId, String userId, String proId, String orderTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.proId = proId;
        this.orderTime = orderTime;
    }

    public Order() {
        this.orderId = "o_00001";
        this.userId = "u_0000000000";
        this.proId = "p_00001";
        this.orderTime = "01-01-2025_00:00:00";
    }

    @Override
    public String toString() {
        return String.format(
            "{\"order_id\":\"%s\", \"user_id\":\"%s\", \"pro_id\":\"%s\", \"order_time\":\"%s\"}",
            orderId, userId, proId, orderTime
        );
    }

    // Getters (optional for use in operations)
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getProId() { return proId; }
    public String getOrderTime() { return orderTime; }
}
