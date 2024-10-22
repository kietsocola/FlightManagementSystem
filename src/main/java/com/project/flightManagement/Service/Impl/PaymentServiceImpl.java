package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.Service.PaymentService;
import com.project.flightManagement.Util.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    public URL createPaymentUrl(int amount, String orderInfo, HttpServletRequest request) throws Exception {
        // Các tham số cơ bản
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayUtils.getRandomNumber(8); // Tạo mã giao dịch ngẫu nhiên
        String vnp_IpAddr = VNPayUtils.getIpAddress(request);
        String vnp_TmnCode = VNPayUtils.vnp_TmnCode;
        String orderType = "other";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100L)); // Số tiền nhân với 100 (VNĐ)
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", URLEncoder.encode(orderInfo, StandardCharsets.UTF_8));
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn"); // Có thể đổi thành "en" nếu cần
        vnp_Params.put("vnp_ReturnUrl", VNPayUtils.vnp_Returnurl); // URL để xử lý sau thanh toán
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Thời gian tạo và hết hạn
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15); // Thời gian hết hạn là 15 phút
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo chuỗi hash và query
        return VNPayUtils.buildPaymentUrl(vnp_Params);
    }
}
