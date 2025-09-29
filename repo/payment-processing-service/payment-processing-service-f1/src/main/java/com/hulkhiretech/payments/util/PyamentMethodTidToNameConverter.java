package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentMethodEnum;

public class PyamentMethodTidToNameConverter extends AbstractConverter<Integer, String> {

	@Override
	protected String convert(Integer source) {
		return PaymentMethodEnum.fromId(source).getName();
	}

}
