package com.paystackPayment.paystack.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserRequest {
    private String name;
    private String email;

}
