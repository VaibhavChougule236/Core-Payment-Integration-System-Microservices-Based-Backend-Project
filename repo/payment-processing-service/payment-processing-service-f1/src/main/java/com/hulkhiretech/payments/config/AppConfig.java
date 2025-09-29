package com.hulkhiretech.payments.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.util.*;
import org.modelmapper.PropertyMap;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Use STRICT matching to avoid fuzzy/ambiguous matches
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE);

		Converter<String, Integer> transactionStatusEnumConverter = new TransactionStatusEnumConverter();
		Converter<String, Integer> providerEnumConverter = new ProviderEnumConverter();
		Converter<String, Integer> pyamentMethodEnumConverter = new PyamentMethodEnumConverter();
		Converter<String, Integer> pyamentTypeEnumConverter = new PaymentTypeEnumConverter();

		modelMapper.addMappings(new PropertyMap<TransactionDTO, TransactionEntity>() {
			@Override
			protected void configure() {
				using(transactionStatusEnumConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
				using(providerEnumConverter).map(source.getProvider(), destination.getProviderId());
				using(pyamentMethodEnumConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
				using(pyamentTypeEnumConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
			}
		});

		Converter<Integer, String> paymentMethodTidToNameConverter = new PyamentMethodTidToNameConverter();
		Converter<Integer, String> transactionStatusIdToNameConverter = new TransactionStatusIdToNameConverter();
		Converter<Integer, String> providerIdToNameConverter = new ProviderIdToNameConverter();
		Converter<Integer, String> paymentTypeIdToNameConverter = new PaymentTypeIdToNameConverter();

		modelMapper.addMappings(new PropertyMap<TransactionEntity, TransactionDTO>() {
			@Override
			protected void configure() {
				using(paymentMethodTidToNameConverter).map(source.getPaymentMethodId(), destination.getPaymentMethod());
				using(transactionStatusIdToNameConverter).map(source.getTxnStatusId(), destination.getTxnStatus());
				using(providerIdToNameConverter).map(source.getProviderId(), destination.getProvider());
				using(paymentTypeIdToNameConverter).map(source.getPaymentTypeId(), destination.getPaymentType());
			}
		});

		return modelMapper;
	}

	@Bean
	RestClient restClientConfig() {
		return RestClient.create();
	}
}
