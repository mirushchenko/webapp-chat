package com.example.webapp;

public class UserJoin {
    private String username;
    private String userId;
    private String type;

    public UserJoin() {}

    public UserJoin(String username, String type) {
        this.username = username;
        this.type = type;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}