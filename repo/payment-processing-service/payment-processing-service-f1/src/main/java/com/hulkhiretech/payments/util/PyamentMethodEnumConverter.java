package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentMethodEnum;

public class PyamentMethodEnumConverter extends AbstractConverter<String, Integer> {

	@Override
	protected Integer convert(String source) {
		return PaymentMethodEnum.fromLabel(source).getId();
	}

	

}
