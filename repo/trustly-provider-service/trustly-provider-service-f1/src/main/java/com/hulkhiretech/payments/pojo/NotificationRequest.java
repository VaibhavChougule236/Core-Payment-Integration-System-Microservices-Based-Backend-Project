package com.hulkhiretech.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String txnReference;

    private String providerReference;

    private String status;

    private String errorCode;

    private String errorMessage;
}