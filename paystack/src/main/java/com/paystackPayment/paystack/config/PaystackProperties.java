package com.paystackPayment.paystack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("paystack")
@Configuration
@Data
public class PaystackProperties {
    private String baseUrl;
}
