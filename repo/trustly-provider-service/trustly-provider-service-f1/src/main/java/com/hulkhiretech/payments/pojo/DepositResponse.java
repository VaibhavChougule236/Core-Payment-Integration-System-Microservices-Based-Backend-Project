package com.hulkhiretech.payments.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DepositResponse {
	private String orderid;
	private String url;

}
