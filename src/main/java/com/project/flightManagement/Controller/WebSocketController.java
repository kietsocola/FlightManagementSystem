package com.project.flightManagement.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/message")
    @SendTo("/topic/response")
    public String processMessageFromClient(String message) {
        return "Server received: " + message;
    }
}
