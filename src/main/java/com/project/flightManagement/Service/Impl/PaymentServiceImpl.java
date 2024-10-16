package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import com.project.flightManagement.Service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean checkPaymentStatus(HanhKhachDTO hanhKhach) {
        return true;
    }
}
