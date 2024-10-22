package com.project.flightManagement.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.SortedMap;

@Service
public interface PaymentService {
    public URL createPaymentUrl(int amount, String orderInfo, HttpServletRequest request)throws Exception;
}
