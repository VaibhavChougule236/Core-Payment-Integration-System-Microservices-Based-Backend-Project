package com.cpt.payments.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
	private String txnReference;
	private String providerReference;
	private String txnStatus;
	private String errorCode;
	private String errorMessage;
}
