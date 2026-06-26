package com.hulkhiretech.payments.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.service.impl.statushandler.CreatedStatusHandler;
import com.hulkhiretech.payments.service.impl.statushandler.FailedStatusHandler;
import com.hulkhiretech.payments.service.impl.statushandler.InitiatedStatusHandler;
import com.hulkhiretech.payments.service.impl.statushandler.PendingStatusHandler;
import com.hulkhiretech.payments.service.interfaces.TransactionStatusHandler;
import com.hulkhiretech.payments.service.impl.statushandler.SuccessStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionStatusFactory {
	
	private final ApplicationContext applicationContext;
	
	public TransactionStatusHandler getTransactionStatusHandler(
	        TransactionStatusEnum txnStatusEnum) {

	    log.info("Fetching handler for transaction status: {}", txnStatusEnum);

	    switch (txnStatusEnum) {

	        case CREATED:
	            return applicationContext.getBean(CreatedStatusHandler.class);

	        case INITIATED:
	            return applicationContext.getBean(InitiatedStatusHandler.class);

	        case PENDING:
	            return applicationContext.getBean(PendingStatusHandler.class);

	        case SUCCESS:
	            return applicationContext.getBean(SuccessStatusHandler.class);

	        case FAILED:
	            return applicationContext.getBean(FailedStatusHandler.class);

	        default:
	            throw new IllegalArgumentException(
	                    "Unsupported transaction status: " + txnStatusEnum);
	    }
	}

}
