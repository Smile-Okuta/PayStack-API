package com.paystackPayment.paystack.request.PaymentRequest;

import lombok.Data;

@Data
public class PaymentRequest {
    private String email;
    private String amount;
}
