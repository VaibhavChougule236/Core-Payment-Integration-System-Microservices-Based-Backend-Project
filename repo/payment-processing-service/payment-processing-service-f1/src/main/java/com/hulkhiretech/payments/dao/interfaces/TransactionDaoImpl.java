package com.hulkhiretech.payments.dao.interfaces;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDao {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public boolean createTransaction(TransactionEntity entity) {
		log.info("Creating transaction in DAO layer");
		log.info("TransactionEntity: {}", entity);

		String sql = "INSERT INTO payments.Transaction ("
				+ "userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, "
				+ "amount, currency, merchantTransactionReference, txnReference, providerReference, "
				+ "errorCode, errorMessage, retryCount) "
				+ "VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, "
				+ ":amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, "
				+ ":errorCode, :errorMessage, :retryCount)";

		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);

		int rowsAffected = namedParameterJdbcTemplate.update(sql, paramSource);
		log.info("Rows affected by transaction creation: {}", rowsAffected);
		return rowsAffected == 1;
	}

	@Override
	public boolean updateTransaction(TransactionEntity entity) {
		log.info("Updating transaction in DAO layer");
		log.info("TransactionEntity: {}", entity);

		String sql = "UPDATE payments.Transaction SET " + "txnStatusId = :txnStatusId, "
				+ "providerReference = :providerReference " + "WHERE txnReference = :txnReference";

//		Map<String, Object> params = new HashMap<>();
//		params.put("txnStatusId", entity.getTxnStatusId());
//		params.put("providerReference", entity.getProviderReference());

		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);

		int rowsAffected = namedParameterJdbcTemplate.update(sql, paramSource);
		log.info("Rows affected by transaction update: {}", rowsAffected);

		return rowsAffected > 0;

	}

	@Override
	public TransactionEntity getTransactionByReference(String txnReference) {
		log.info("Fetching transaction by reference: {}", txnReference);

		String sql = "SELECT * FROM payments.Transaction WHERE txnReference = :txnReference";

		Map<String, Object> params = new HashMap<>();
		params.put("txnReference", txnReference);

		TransactionEntity entity = namedParameterJdbcTemplate.queryForObject(sql, params,
				new BeanPropertyRowMapper<>(TransactionEntity.class));

		log.info("Fetched transaction Entity: {}", entity);

		return entity;
	}

}
