package com.paystackPayment.paystack.services;

import com.paystackPayment.paystack.models.requests.UserRequest;
import com.paystackPayment.paystack.models.entities.AppUser;
import com.paystackPayment.paystack.repositories.AppUserRepository;
import com.paystackPayment.paystack.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private  AppUserRepository appUserRepository;

    @Override
    public String createUser(UserRequest createUser) {
//         if (appUserRepository.existsByEmail(createUser.getEmail())){
//             throw new IllegalArgumentException("Email already exist");
//         }
        AppUser user = new AppUser();
         user.setName(createUser.getName());
         user.setEmail(createUser.getEmail());
         appUserRepository.save(user);
        return "User Saved Successfully";
    }
}
