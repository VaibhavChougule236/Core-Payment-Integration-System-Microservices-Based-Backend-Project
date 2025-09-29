package com.hulkhiretech.payments.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    
    private Long id;

    private String amount;
    private String currency;
    private String paymentMethod;
    private String paymentType;
    private String provider;
    private String customerId;
    private String email;
    private String phone;

    private String status; // CREATED, SUCCESS, FAILED
    private String transactionId; // Assigned by payment gateway

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}