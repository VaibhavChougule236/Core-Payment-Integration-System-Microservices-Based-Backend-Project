package com.hulkhiretech.payments.pojo.res;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private String status;
    private String provider;
    private String amount;
    private String currency;
    private String redirectUrl;
}
