package com.example.webapp.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public static class User {
        public String id;
        public String username;
        public String email;
        public String avatarUrl;
        public String createdAt;

        public User(String username, String email) {
            this.id = "user_" + System.currentTimeMillis() + "_" + new Random().nextInt(1000);
            this.username = username;
            this.email = email;
            this.avatarUrl = "https://ui-avatars.com/api/?name=" + username + "&background=007bff&color=fff";
            this.createdAt = java.time.LocalDateTime.now().toString();
        }
    }

    public User createUser(String username, String email) {
        System.out.println("🎯 UserService: Creating user: " + username);
        User user = new User(username, email);
        users.put(user.id, user);
        onlineUsers.add(user.id);
        return user;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<User> getOnlineUsers() {
        return onlineUsers.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public void setUserOnline(String userId) {
        onlineUsers.add(userId);
    }

    public void setUserOffline(String userId) {
        onlineUsers.remove(userId);
    }

    public boolean userExists(String userId) {
        return users.containsKey(userId);
    }
}
