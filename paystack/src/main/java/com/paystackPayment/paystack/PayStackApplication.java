package com.paystackPayment.paystack;

import com.paystackPayment.paystack.models.AppUser;
import com.paystackPayment.paystack.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayStackApplication implements CommandLineRunner {

	private AppUserRepository appUserRepository;


    public static void main(String[] args) {
		SpringApplication.run(PayStackApplication.class, args);
	}

	@Override
	public void run(String[] args){
		appUserRepository.save(new AppUser(1L,"Smile Okuta", "babysmilex4real@gmail.com", true));
	}

}
