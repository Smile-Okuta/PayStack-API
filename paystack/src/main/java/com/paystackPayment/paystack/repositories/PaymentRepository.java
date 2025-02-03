package com.paystackPayment.paystack.repositories;

import com.paystackPayment.paystack.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository< PaymentModel,Long> {
}
