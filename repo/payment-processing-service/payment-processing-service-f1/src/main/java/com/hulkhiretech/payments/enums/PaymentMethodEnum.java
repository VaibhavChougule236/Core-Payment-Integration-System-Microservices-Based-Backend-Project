package com.hulkhiretech.payments.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethodEnum {
	APM(1, "APM");

	private final int id;
	private final String name;
	
	public static PaymentMethodEnum fromLabel(String label) {
		for (PaymentMethodEnum method : PaymentMethodEnum.values()) {
			if (method.getName().equalsIgnoreCase(label)) {
				return method;
			}
		}
		throw new IllegalArgumentException("No PaymentMethodEnum found for label: " + label);
	}
	
	public static PaymentMethodEnum fromId(int id) {
		for (PaymentMethodEnum method : PaymentMethodEnum.values()) {
			if (method.getId() == id) {
				return method;
			}
		}
		throw new IllegalArgumentException("No PaymentMethodEnum found for id: " + id);
	}
}
