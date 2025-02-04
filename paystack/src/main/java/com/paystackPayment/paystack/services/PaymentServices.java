package com.paystackPayment.paystack.services;

import com.paystackPayment.paystack.dto.request.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaymentServices {
    Map<String, Object > makePayment(PaymentRequest paymentRequest);


}
