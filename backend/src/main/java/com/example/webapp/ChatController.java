package com.example.webapp;

import com.example.webapp.service.UserService;
import com.example.webapp.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    private final UserService userService;
    private final MessageService messageService;

    public ChatController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(ChatMessage message) {
        System.out.println("=== 🎯 WebSocket Message Received ===");
        System.out.println("   User: " + message.getUser());
        System.out.println("   Content: " + message.getContent());
        
        // Сохраняем сообщение через сервис
        var savedMessage = messageService.saveMessage(
            message.getUserId() != null ? message.getUserId() : "anonymous",
            message.getUser(),
            message.getContent()
        );
        
        // Создаем ответ
        MessageResponse response = new MessageResponse();
        response.setId(savedMessage.id);
        response.setUser(savedMessage.username);
        response.setContent(savedMessage.content);
        response.setTimestamp(savedMessage.timestamp);
        response.setUserId(savedMessage.userId);
        
        System.out.println("   Saved with ID: " + response.getId());
        System.out.println("   Sending to: /topic/messages");
        System.out.println("=====================================");
        
        return response;
    }

    @MessageMapping("/chat.join")
    @SendTo("/topic/users")
    public UserJoin userJoin(UserJoin userJoin) {
        System.out.println("=== 👋 User Joined ===");
        System.out.println("   Username: " + userJoin.getUsername());
        System.out.println("   Type: " + userJoin.getType());
        
        // Можно сохранить пользователя если нужно
        if ("REGISTER".equals(userJoin.getType())) {
            var user = userService.createUser(userJoin.getUsername(), userJoin.getUsername() + "@chat.com");
            userJoin.setUserId(user.id);
        }
        
        System.out.println("======================");
        return userJoin;
    }

    // DTO для ответа
    public static class MessageResponse {
        private String id;
        private String user;
        private String content;
        private String timestamp;
        private String userId;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }
}