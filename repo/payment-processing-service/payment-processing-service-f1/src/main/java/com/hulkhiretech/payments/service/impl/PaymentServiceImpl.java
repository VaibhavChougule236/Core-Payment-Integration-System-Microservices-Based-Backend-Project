package com.hulkhiretech.payments.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulkhiretech.payments.constant.ErrorEnum;
import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.exception.TrustlyProviderException;
import com.hulkhiretech.payments.factory.TransactionStatusFactory;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.CreateTxnResponse;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
import com.hulkhiretech.payments.pojo.PaymentResponse;
import com.hulkhiretech.payments.service.PaymentServiceHelper;
import com.hulkhiretech.payments.service.PaymentStatusService;
import com.hulkhiretech.payments.service.impl.statushandler.CreatedStatusHandler;
import com.hulkhiretech.payments.service.interfaces.PaymentService;
import com.hulkhiretech.payments.trustlyProvider.TrustlyProviderDepositResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final TransactionDao transactionDao;

	private final ModelMapper modelMapper;

	private final CreatedStatusHandler createdStatusHandler;

	private final TransactionStatusFactory transactionStatusFactory;

	private final PaymentStatusService paymentStatusService;

	private final PaymentServiceHelper paymentServiceHelper;

	private final HttpServiceEngine httpServiceEngine;

	@Override
	@Transactional
	public CreateTxnResponse createPayment(CreateTxnRequest createTxnRequest) {
		log.info("Received payment request | createTxnRequest: {}", createTxnRequest);

		String txnReference = UUID.randomUUID().toString();
		String txnStatus = TransactionStatusEnum.CREATED.getLabel();
		log.info("Generated txnReference: {}, txnStatus: {}", txnReference, txnStatus);

		TransactionDTO txnDTO = modelMapper.map(createTxnRequest, TransactionDTO.class);
		log.info("Mapped CreateTxnRequest to txnDTO: {}", txnDTO);

		txnDTO.setTxnStatus(txnStatus);
		txnDTO.setTxnReference(txnReference);

		txnDTO = paymentStatusService.updatePayment(txnDTO);
		log.info("Updated txnDTO after processing: {}", txnDTO);

		CreateTxnResponse response = new CreateTxnResponse();
		response.setTxnReference(txnDTO.getTxnReference());
		response.setTxnStatus(txnDTO.getTxnStatus());
		log.info("Mapped txnDTO to CreateTxnResponse: {}", response);

		return response;
	}

	@Override
	@Transactional
	public PaymentResponse initiatePayment(String txnReference, InitiateTxnRequest initiatePaymentRequest) {

		log.info("Initiating payment for txnReference: {}, initiateTxnRequest: {}", txnReference,
				initiatePaymentRequest);

		TransactionEntity txnEntity = transactionDao.getTransactionByReference(txnReference);
		log.info("Fetched transaction entity: {}", txnEntity);

		TransactionDTO txnDTO = modelMapper.map(txnEntity, TransactionDTO.class);
		log.info("Mapped TransactionEntity to TransactionDTO: {}", txnDTO);

		txnDTO.setTxnStatus(TransactionStatusEnum.INITIATED.name());
		txnDTO = paymentStatusService.updatePayment(txnDTO);

		log.info("Processed transactionDTO after initiation: {}", txnDTO);

		HttpRequest request = paymentServiceHelper.prepareInitiateRequest(txnDTO, initiatePaymentRequest);

		TrustlyProviderDepositResponse responseObj = null;
		try {
			ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(request);

			responseObj = paymentServiceHelper.processResponse(httpResponse);
			log.info("Processed DepositResponse: {}", responseObj);

		} catch (TrustlyProviderException e) {// Failure in processing the response
			log.error("Error processing Trustly response: {}", e.getMessage(), e);
			txnDTO.setTxnStatus(TransactionStatusEnum.FAILED.name());
			txnDTO.setErrorCode(e.getErrorCode());
			txnDTO.setErrorMessage(e.getErrorMessage());

			paymentStatusService.updatePayment(txnDTO);
			log.info("Transaction status updated to FAILED with error: {}", e.getErrorMessage());
			throw e;
		} catch (Exception e) {
			log.error("Error processing Trustly response: {}", e.getMessage(), e);
			txnDTO.setTxnStatus(TransactionStatusEnum.FAILED.name());
			txnDTO.setErrorCode(ErrorEnum.ERROR_PROCESSING_TRUSTLY_RESPONSE.getErrorCode());
			txnDTO.setErrorMessage(ErrorEnum.ERROR_PROCESSING_TRUSTLY_RESPONSE.getErrorMessage());

			paymentStatusService.updatePayment(txnDTO);
			log.info("Transaction status updated to FAILED with error: {}",
					ErrorEnum.ERROR_PROCESSING_TRUSTLY_RESPONSE.getErrorMessage());
			throw e;
		}

		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTxnReference(txnDTO.getTxnReference());
		paymentResponse.setUrl(responseObj.getUrl());

		txnDTO.setTxnStatus(TransactionStatusEnum.PENDING.name());
		txnDTO.setProviderReference(responseObj.getOrderid());
		txnDTO = paymentStatusService.updatePayment(txnDTO);

		paymentResponse.setTxnStatus(txnDTO.getTxnStatus());
		log.info("Returning responseObj: {}", responseObj);

		return paymentResponse;

	}

}
