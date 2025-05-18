package model;

public class Admin extends User {

    // Constructor không cần truyền userRole vì luôn là "admin"
    public Admin(String userId, String userName, String userPassword, String userRegisterTime) {
        super(userId, userName, userPassword, userRegisterTime, "admin");
    }

    // Default constructor
    public Admin() {
        super();
        this.userRole = "admin";
    }

    @Override
    public String toString() {
        return String.format(
            "{\"user_id\":\"%s\", \"user_name\":\"%s\", \"user_password\":\"%s\", " +
            "\"user_register_time\":\"%s\", \"user_role\":\"%s\"}",
            userId, userName, userPassword, userRegisterTime, userRole
        );
    }
}
