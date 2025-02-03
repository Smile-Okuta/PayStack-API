package com.paystackPayment.paystack.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paystackPayment.paystack.models.AppUser;
import com.paystackPayment.paystack.models.PaymentModel;
import com.paystackPayment.paystack.models.PaymentStatus;
import com.paystackPayment.paystack.repositories.AppUserRepository;
import com.paystackPayment.paystack.repositories.PaymentRepository;
import com.paystackPayment.paystack.services.PaymentServices;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Configuration
@PropertySource("classpath:secret.properties")
@RequiredArgsConstructor
@Service
@Getter
public class PaymentServiceImpl implements PaymentServices {
    @Value("${paystack.apiUrl}")
    private  String payStackApiUrl;

    @Value("${paystack.secreteKey}")
    private  String payStackSecreteKey;

    private  RestTemplate restTemplate;
    private AppUserRepository appUserRepository;
    private ObjectMapper objectMapper;
    private PaymentRepository paymentRepository;

    @Override
    public Map<String, Object> makePayment(String email, double amount) {


        Optional<AppUser> userExist = appUserRepository.findByEmail(email);
        if (userExist.isEmpty()) {
            return Map.of("error", "User not found");
        }
        AppUser user = userExist.get();


        HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", payStackSecreteKey);
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("amount", amount * 100);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(payStackApiUrl, HttpMethod.POST, entity, String.class);

        try {
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            if (jsonResponse.get("status").asBoolean()) {

                String paymentUrl = jsonResponse.get("data").get("authorization_url").asText();

                PaymentModel payment = new PaymentModel();
                payment.setUser(user);
                payment.setEmail(email);
                payment.setAmount(amount);
                payment.setStatus(PaymentStatus.PENDING);
                paymentRepository.save(payment);


                return Map.of(
                        "message", "Payment initialized",
                        "paymentUrl", paymentUrl
                );


            } else {
                return Map.of("error", "Payment initialization failed", "PayStackResponse", jsonResponse);
            }
        } catch (Exception e) {
            return Map.of("error", "Error parsing response: " + e.getMessage());
        }

    }



};
