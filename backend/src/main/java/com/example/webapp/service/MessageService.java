package com.example.webapp.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageService {
    private final List<ChatMessage> messageHistory = new CopyOnWriteArrayList<>();
    private final int MAX_HISTORY = 100;

    public static class ChatMessage {
        public String id;
        public String userId;
        public String username;
        public String content;
        public String timestamp;
        public String roomId;

        public ChatMessage(String userId, String username, String content) {
            this.id = "msg_" + System.currentTimeMillis() + "_" + new Random().nextInt(1000);
            this.userId = userId;
            this.username = username;
            this.content = content;
            this.timestamp = java.time.LocalDateTime.now().toString();
            this.roomId = "general"; // можно добавить комнаты позже
        }
    }

    public ChatMessage saveMessage(String userId, String username, String content) {
        System.out.println("🎯 MessageService: Saving message from " + username);
        
        ChatMessage message = new ChatMessage(userId, username, content);
        messageHistory.add(message);
        
        // Ограничиваем историю
        if (messageHistory.size() > MAX_HISTORY) {
            messageHistory.remove(0);
        }
        
        return message;
    }

    public List<ChatMessage> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public List<ChatMessage> getRecentMessages(int limit) {
        int start = Math.max(0, messageHistory.size() - limit);
        return new ArrayList<>(messageHistory.subList(start, messageHistory.size()));
    }

    public Optional<ChatMessage> getMessage(String messageId) {
        return messageHistory.stream()
                .filter(msg -> msg.id.equals(messageId))
                .findFirst();
    }
}
