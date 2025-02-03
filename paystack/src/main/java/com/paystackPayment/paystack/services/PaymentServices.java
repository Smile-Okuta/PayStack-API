package com.paystackPayment.paystack.services;

import com.paystackPayment.paystack.request.PaymentRequest.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaymentServices {
    Map<String, Object > makePayment(String email, double amount);
}
