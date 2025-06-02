package com.invent.InventoryManagementSystem.services;

import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.dtos.TransactionRequest;
import com.invent.InventoryManagementSystem.enums.TransactionStatus;

@Service
public interface TransactionService {
	
	Response purchase(TransactionRequest transactionRequest);

    Response sell(TransactionRequest transactionRequest);

    Response returnToSupplier(TransactionRequest transactionRequest);

    Response getAllTransactions(int page, int size, String filter);

    Response getAllTransactionById(Long id);

    Response getAllTransactionByMonthAndYear(int month, int year);

    Response updateTransactionStatus(Long transactionId, TransactionStatus status);

}
