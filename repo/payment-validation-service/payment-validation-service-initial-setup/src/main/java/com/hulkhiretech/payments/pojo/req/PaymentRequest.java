package com.hulkhiretech.payments.pojo.req;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String amount;
    private String currency;
    private String paymentMethod;  // e.g., "CARD"
    private String paymentType;    // e.g., "SALE"
    private String provider;       // e.g., "TRUSTLY"
    private String customerId;
    private String email;
    private String phone;
}