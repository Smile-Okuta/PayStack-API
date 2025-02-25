package com.paystackPayment.paystack.services.interfaces;

import com.paystackPayment.paystack.models.requests.PaymentRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaymentServices {
    Map<String, Object > makePayment(PaymentRequest paymentRequest);

}
