package com.hulkhiretech.payments.constant;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class EndPoints {
	public static final String PAYMENT_BASE_URI = "/payment";

	public static final String PROCESS_PAYMENT = "/initiate";

	public static final String SUCCESS_PAYMENT = "/success/{paymentId}";

	public static final String FAIL_PAYMENT = "/fail/{paymentId}";

	public static final String V1_TRUSTLY_DEPOSITS = "/v1/trustly/deposits";

}
