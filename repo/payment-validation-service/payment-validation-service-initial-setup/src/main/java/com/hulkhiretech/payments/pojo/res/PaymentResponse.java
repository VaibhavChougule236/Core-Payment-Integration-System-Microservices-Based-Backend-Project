package com.hulkhiretech.payments.pojo.res;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private String status;
    private String provider;
    private BigDecimal amount;
    private String currency;
    private String redirectUrl;
}
