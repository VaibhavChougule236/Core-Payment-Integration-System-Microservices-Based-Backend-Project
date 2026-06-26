package com.hulkhiretech.payments.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.entity.TransactionEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransactionDaoImpl implements TransactionDao {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public boolean createTransaction(TransactionEntity entity) {

		log.info("Creating transaction with txnReference: {}", entity.getTxnReference());

		log.debug("Transaction Entity: {}", entity);

		String sql = """
				INSERT INTO payments.Transaction (
				    userId,
				    paymentMethodId,
				    providerId,
				    paymentTypeId,
				    txnStatusId,
				    amount,
				    currency,
				    merchantTransactionReference,
				    txnReference,
				    providerReference,
				    errorCode,
				    errorMessage,
				    retryCount
				)
				VALUES (
				    :userId,
				    :paymentMethodId,
				    :providerId,
				    :paymentTypeId,
				    :txnStatusId,
				    :amount,
				    :currency,
				    :merchantTransactionReference,
				    :txnReference,
				    :providerReference,
				    :errorCode,
				    :errorMessage,
				    :retryCount
				)
				""";

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(entity);

		int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

		log.info("Rows inserted: {}", rowsAffected);

		return rowsAffected == 1;
	}

	@Override
	public boolean updateTransaction(TransactionEntity entity) {

		log.info("Updating transaction with txnReference: {}", entity.getTxnReference());

		log.debug("Transaction Entity: {}", entity);

		String sql = """
				UPDATE payments.Transaction
				SET
				    txnStatusId = :txnStatusId,
				    providerReference = :providerReference,
				    errorCode = :errorCode,
				    errorMessage = :errorMessage,
				    retryCount = :retryCount
				WHERE txnReference = :txnReference
				""";

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(entity);

		int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

		log.info("Rows updated: {}", rowsAffected);

		return rowsAffected == 1;
	}

	@Override
	public TransactionEntity getTransactionByReference(String txnReference) {

		log.info("Fetching transaction for txnReference: {}", txnReference);

		String sql = """
				SELECT *
				FROM payments.Transaction
				WHERE txnReference = :txnReference
				""";

		Map<String, Object> params = new HashMap<>();
		params.put("txnReference", txnReference);

		TransactionEntity entity = namedParameterJdbcTemplate.queryForObject(sql, params,
				new BeanPropertyRowMapper<>(TransactionEntity.class));

		log.debug("Fetched Transaction Entity: {}", entity);

		return entity;
	}
}