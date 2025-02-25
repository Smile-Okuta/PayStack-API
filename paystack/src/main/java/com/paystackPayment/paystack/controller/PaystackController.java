package com.paystackPayment.paystack.controller;

import com.paystackPayment.paystack.models.requests.PaymentRequest;
import com.paystackPayment.paystack.services.interfaces.PaymentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaystackController {


private final PaymentServices paymentServices;

@PostMapping("/initialize")
public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest){

        Map<String, Object> response = paymentServices.makePayment(paymentRequest);
        return new ResponseEntity<>(paymentServices.makePayment(paymentRequest), HttpStatus.OK);
}

}