package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.Service.SocketIOService;
import org.springframework.stereotype.Service;

@Service
public class MockSocketIOServiceImpl implements SocketIOService {
    @Override
    public void emit(String event, Object data) {
        System.out.println("Mock emit: Event - " + event + ", Data - " + data);
    }
}
