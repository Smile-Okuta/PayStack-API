package com.paystackPayment.paystack.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paystackPayment.paystack.dto.request.PaymentRequest;
import com.paystackPayment.paystack.models.AppUser;
import com.paystackPayment.paystack.models.PaymentModel;
import com.paystackPayment.paystack.models.PaymentStatus;
import com.paystackPayment.paystack.repositories.AppUserRepository;
import com.paystackPayment.paystack.repositories.PaymentRepository;
import com.paystackPayment.paystack.services.PaymentServices;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Configuration
@PropertySource("classpath:secrets.properties")
@RequiredArgsConstructor
@Service
@Getter
public class PaymentServiceImpl implements PaymentServices {
    @Value("${paystack.apiUrl}")
    private  String payStackApiUrl;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServices.class);

    @Value("${paystack.secretKey}")
    private  String payStackSecreteKey;

    private  RestTemplate restTemplate;

    @Autowired
    private final AppUserRepository appUserRepository;

    private ObjectMapper objectMapper;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Map<String, Object> makePayment(PaymentRequest paymentRequest) {


        Optional<AppUser> userExist = appUserRepository.findByEmail(paymentRequest.getEmail());
        if (userExist.isEmpty()) {
            return Map.of("error", "User not found");
        }
        AppUser user = userExist.get();

        logger.info("user: {}", user);

        HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer" + payStackSecreteKey);
            headers.set("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", paymentRequest.getEmail());
            requestBody.put("amount", paymentRequest.getAmount());

        logger.info("Email: {}", paymentRequest.getEmail());
        logger.info("Amount: {}", paymentRequest.getAmount());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(payStackApiUrl, HttpMethod.POST, entity, String.class);

        try {
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            if (jsonResponse.get("status").asBoolean()) {

                String paymentUrl = jsonResponse.get("data").get("authorization_url").asText();

                PaymentModel payment = new PaymentModel();
                payment.setUser(user);
                payment.setEmail(paymentRequest.getEmail());
                payment.setAmount(paymentRequest.getAmount());
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

    @Override
    public String sendEmail(String message, String email) {
        return "";
    }


};
