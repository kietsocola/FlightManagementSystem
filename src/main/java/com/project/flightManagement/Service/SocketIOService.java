package com.project.flightManagement.Service;

import org.springframework.stereotype.Service;

@Service
public interface SocketIOService {
    public void emit(String event, Object data);
}
