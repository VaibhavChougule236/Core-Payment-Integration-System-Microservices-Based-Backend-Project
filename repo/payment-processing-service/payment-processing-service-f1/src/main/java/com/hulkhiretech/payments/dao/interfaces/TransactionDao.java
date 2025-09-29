package com.hulkhiretech.payments.dao.interfaces;

import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;

public interface TransactionDao {

	public boolean createTransaction(TransactionEntity entity);

	public boolean updateTransaction(TransactionEntity entity);

	public TransactionEntity getTransactionByReference(String txnReference);

}
