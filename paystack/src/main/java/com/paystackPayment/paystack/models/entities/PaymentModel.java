package com.paystackPayment.paystack.models.entities;

import com.paystackPayment.paystack.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private AppUser user;
    private String email;
    private BigDecimal amount;
    private PaymentStatus status;

}
