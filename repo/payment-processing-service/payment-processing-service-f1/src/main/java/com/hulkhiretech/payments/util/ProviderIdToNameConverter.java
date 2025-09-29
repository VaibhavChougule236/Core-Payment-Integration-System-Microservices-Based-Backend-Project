package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentTypeEnum;
import com.hulkhiretech.payments.enums.ProviderEnum;

public class ProviderIdToNameConverter extends AbstractConverter<Integer,String> {

	@Override
	protected String convert(Integer source) {
		return ProviderEnum.fromCode(source).getLabel();
	}

}
