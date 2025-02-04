package com.paystackPayment.paystack;

import com.paystackPayment.paystack.dto.request.PaymentRequest;
import com.paystackPayment.paystack.dto.response.PaymentResponse;
import com.paystackPayment.paystack.services.PaymentServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PaystackApplicationTests {

    @Mock
	private PaymentServices paymentServices;

    @InjectMocks
    private PaystackApplicationTests paystackApplicationTests;


    @BeforeEach
    void setUp() {
        paystackApplicationTests = new PaystackApplicationTests();
    }

	@Test
	void testInitializePayment() {
		PaymentRequest request = new PaymentRequest("customer@email.com", 200.0);
		when(paymentServices.makePayment(any())).thenReturn(Map.of("status", "success"));

		Map<String, Object> response = paymentServices.makePayment(request);


		assertNotNull("Status should not be null", response);
		assertEquals("success", response.get("status"), response.get("authorization_url"));
	}

}
