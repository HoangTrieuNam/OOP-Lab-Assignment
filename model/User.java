package model;

public abstract class User {
    protected String userId;
    protected String userName;
    protected String userPassword;
    protected String userRegisterTime;
    protected String userRole;

   
    public User(String userId, String userName, String userPassword, String userRegisterTime, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRegisterTime = userRegisterTime;
        this.userRole = userRole;
    }

  
    public User() {
        this.userId = "u_000000000";
        this.userName = "default_user";
        this.userPassword = "default_pass";
        this.userRegisterTime = "01-01-2025_00:00:00";
        this.userRole = "customer";
    }

    @Override
    public String toString() {
        return String.format("{\"userId\":\"%s\",\"userName\":\"%s\",\"userPassword\":\"%s\",\"userRegisterTime\":\"%s\",\"userRole\":\"%s\"}",
                userId, userName, userPassword, userRegisterTime, userRole);
    }
}
