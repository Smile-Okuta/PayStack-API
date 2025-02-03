package com.paystackPayment.paystack.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paystackPayment.paystack.models.AppUser;
import com.paystackPayment.paystack.models.PaymentModel;
import com.paystackPayment.paystack.repositories.AppUserRepository;
import com.paystackPayment.paystack.repositories.PaymentRepository;
import com.paystackPayment.paystack.services.PaymentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentServices {

    private  String PAYSTACK_URL = "https://api.paystack.co/transaction/initialize";
    private  String SECRET_KEY = "Bearer sk_test_f0e01e260e0a9b7e557c97b380477e99338ab3a8"; // Replace with your Paystack secret key
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
            headers.set("Authorization", SECRET_KEY);
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("amount", amount * 100);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(PAYSTACK_URL, HttpMethod.POST, entity, String.class);

        try {
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            if (jsonResponse.get("status").asBoolean()) {

                PaymentModel payment = new PaymentModel();
                payment.setUser(user);
                payment.setEmail(email);
                payment.setAmount(amount);
                payment.setSuccessful(true);
                paymentRepository.save(payment);

            } else {
                return Map.of("error", "Payment initialization failed", "paystackResponse", jsonResponse);
            }
        } catch (Exception e) {
            return Map.of("error", "Error parsing response: " + e.getMessage());
        }
        return requestBody;
    }



};
