package com.shoryukane.redisspring.chat.config;

import com.shoryukane.redisspring.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ChatRoomSocketConfig {

    @Autowired
    private ChatRoomService chatRoomService;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/chat", chatRoomService);
        return new SimpleUrlHandlerMapping(map, -1);
    }

}
