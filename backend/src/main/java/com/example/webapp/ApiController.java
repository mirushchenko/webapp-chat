package com.example.webapp;

import com.example.webapp.service.UserService;
import com.example.webapp.service.MessageService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
@Tag(name = "Chat API", description = "API for chat application operations")
public class ApiController {

    private final UserService userService;
    private final MessageService messageService;

    public ApiController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @Operation(summary = "Create a new user", description = "Register a new user in the chat system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/users")
    public UserService.User createUser(@RequestBody Map<String, String> request) {
        return userService.createUser(request.get("username"), request.get("email"));
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @GetMapping("/users")
    public List<UserService.User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get online users", description = "Retrieve currently online users")
    @GetMapping("/users/online")
    public List<UserService.User> getOnlineUsers() {
        return userService.getOnlineUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{userId}")
    public UserService.User getUser(@PathVariable String userId) {
        UserService.User user = userService.getUser(userId);
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }
        return user;
    }

    @Operation(summary = "Get message history", description = "Retrieve complete chat message history")
    @GetMapping("/messages")
    public List<MessageService.ChatMessage> getMessageHistory() {
        return messageService.getMessageHistory();
    }

    @Operation(summary = "Get recent messages", description = "Retrieve recent messages with limit")
    @GetMapping("/messages/recent")
    public List<MessageService.ChatMessage> getRecentMessages(@RequestParam(defaultValue = "20") int limit) {
        return messageService.getRecentMessages(limit);
    }

    @Operation(summary = "Get system information", description = "Retrieve current system statistics")
    @GetMapping("/info")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("totalUsers", userService.getAllUsers().size());
        info.put("onlineUsers", userService.getOnlineUsers().size());
        info.put("totalMessages", messageService.getMessageHistory().size());
        info.put("serverTime", LocalDateTime.now().toString());
        return info;
    }
}