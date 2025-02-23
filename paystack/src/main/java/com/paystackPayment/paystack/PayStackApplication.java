package com.paystackPayment.paystack;

import com.paystackPayment.paystack.models.AppUser;
import com.paystackPayment.paystack.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayStackApplication {


	public static void main(String[] args) {
		SpringApplication.run(PayStackApplication.class, args);
	}


}
