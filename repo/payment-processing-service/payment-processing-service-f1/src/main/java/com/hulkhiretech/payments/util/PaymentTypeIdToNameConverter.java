package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentTypeEnum;

public class PaymentTypeIdToNameConverter extends AbstractConverter<Integer, String> {

	@Override
	protected String convert(Integer source) {
		return PaymentTypeEnum.fromCode(source).getLabel();
	}

}
