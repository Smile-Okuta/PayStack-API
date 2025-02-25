package com.paystackPayment.paystack.models.requests;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserRequest {
    private String name;
    private String email;

}
