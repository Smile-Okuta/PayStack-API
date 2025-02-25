package com.paystackPayment.paystack.controller;

import com.paystackPayment.paystack.models.requests.UserRequest;
import com.paystackPayment.paystack.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<?> initializePayment(@RequestBody UserRequest userRequest){

        String response = userService.createUser(userRequest);
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.OK);
    }

}
