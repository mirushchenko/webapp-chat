package com.example.webapp;

public class ChatMessage {
    private String id;
    private String user;
    private String userId;
    private String content;
    private String timestamp;

    public ChatMessage() {}

    public ChatMessage(String user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}