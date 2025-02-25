package com.paystackPayment.paystack;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("payStack")
@Configuration
@Data
public class PayStackProperties {
    private String baseUrl;
}
