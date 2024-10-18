package com.project.flightManagement.Service;

import com.project.flightManagement.DTO.HanhKhachDTO.HanhKhachDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    public boolean checkPaymentStatus(HanhKhachDTO hanhKhach);
}
