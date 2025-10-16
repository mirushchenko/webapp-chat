package com.example.webapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Включаем простой брокер в памяти для тем /topic
        config.enableSimpleBroker("/topic");
        // Префикс для сообщений от клиентов к @MessageMapping методам
        config.setApplicationDestinationPrefixes("/app");
        System.out.println("✅ WebSocket Broker: /topic (broker), /app (app prefix)");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Регистрируем WebSocket endpoint
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        System.out.println("✅ WebSocket Endpoint: /ws (SockJS enabled)");
    }
}