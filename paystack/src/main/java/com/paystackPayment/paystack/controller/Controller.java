package com.paystackPayment.paystack.controller;

import com.paystackPayment.paystack.dto.request.PaymentRequest;
import com.paystackPayment.paystack.services.interfaces.PaymentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class Controller {

@Autowired
private PaymentServices paymentServices;

@PostMapping("/initialize")
public ResponseEntity<?> initializePayment(@RequestBody PaymentRequest paymentRequest){

        Map<String, Object> response = paymentServices.makePayment(paymentRequest);
        return new ResponseEntity<>(paymentServices.makePayment(paymentRequest), HttpStatus.OK);
}

}