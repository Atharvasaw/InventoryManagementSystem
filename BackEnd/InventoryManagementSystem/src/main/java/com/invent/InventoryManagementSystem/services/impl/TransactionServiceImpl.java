package com.invent.InventoryManagementSystem.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.dtos.TransactionDTO;
import com.invent.InventoryManagementSystem.dtos.TransactionRequest;
import com.invent.InventoryManagementSystem.enums.TransactionStatus;
import com.invent.InventoryManagementSystem.enums.TransactionType;
import com.invent.InventoryManagementSystem.exceptions.NameValueRequiredException;
import com.invent.InventoryManagementSystem.exceptions.NotFoundException;
import com.invent.InventoryManagementSystem.models.Product;
import com.invent.InventoryManagementSystem.models.Supplier;
import com.invent.InventoryManagementSystem.models.Transaction;
import com.invent.InventoryManagementSystem.models.User;
import com.invent.InventoryManagementSystem.repositories.ProductRepository;
import com.invent.InventoryManagementSystem.repositories.SupplierRepository;
import com.invent.InventoryManagementSystem.repositories.TransactionRepository;
import com.invent.InventoryManagementSystem.services.TransactionService;
import com.invent.InventoryManagementSystem.services.UserService;
import com.invent.InventoryManagementSystem.specification.TransactionFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

	 @Autowired
	 private TransactionRepository transactionRepository;
	 @Autowired
	 private ProductRepository productRepository;
	 @Autowired
	 private SupplierRepository supplierRepository;
	 @Autowired
	 private UserService userService;
	 @Autowired
	 private ModelMapper modelMapper;
	 
	@Override
	public Response purchase(TransactionRequest transactionRequest) {
		// TODO Auto-generated method stub
		Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if (supplierId == null) throw new NameValueRequiredException("Supplier Id is Required");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Purchase Made successfully")
                .build();
	}

	@Override
	public Response sell(TransactionRequest transactionRequest) {
		// TODO Auto-generated method stub
		 Long productId = transactionRequest.getProductId();
	        Integer quantity = transactionRequest.getQuantity();

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new NotFoundException("Product Not Found"));

	        User user = userService.getCurrentLoggedInUser();

	        //update the stock quantity and re-save
	        product.setStockQuantity(product.getStockQuantity() - quantity);
	        productRepository.save(product);


	        //create a transaction
	        Transaction transaction = Transaction.builder()
	                .transactionType(TransactionType.SALE)
	                .status(TransactionStatus.COMPLETED)
	                .product(product)
	                .user(user)
	                .totalProducts(quantity)
	                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
	                .description(transactionRequest.getDescription())
	                .note(transactionRequest.getNote())
	                .build();

	        transactionRepository.save(transaction);
	        return Response.builder()
	                .status(200)
	                .message("Product Sale successfully made")
	                .build();


	}

	@Override
	public Response returnToSupplier(TransactionRequest transactionRequest) {
		// TODO Auto-generated method stub
		Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if (supplierId == null) throw new NameValueRequiredException("Supplier Id is Required");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);


        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .status(TransactionStatus.PROCESSING)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Product Returned in progress")
                .build();
	}

	@Override
	public Response getAllTransactions(int page, int size, String filter) {
		// TODO Auto-generated method stub
		 Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

	        //user the Transaction specification
	        Specification<Transaction> spec = TransactionFilter.byFilter(filter);
	        Page<Transaction> transactionPage = transactionRepository.findAll(spec, pageable);

	        List<TransactionDTO> transactionDTOS = modelMapper.map(transactionPage.getContent(), new TypeToken<List<TransactionDTO>>() {
	        }.getType());

	        transactionDTOS.forEach(transactionDTO -> {
	            transactionDTO.setUser(null);
	            transactionDTO.setProduct(null);
	            transactionDTO.setSupplier(null);
	        });

	        return Response.builder()
	                .status(200)
	                .message("success")
	                .transactions(transactionDTOS)
	                
	                .totalPages(transactionPage.getTotalPages())
	                .build();

	}

	@Override
	public Response getAllTransactionById(Long id) {
		// TODO Auto-generated method stub
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        transactionDTO.getUser().setTransactions(null);

        return Response.builder()
                .status(200)
                .message("success")
                .transaction(transactionDTO)
                .build();

	}

	@Override
	public Response getAllTransactionByMonthAndYear(int month, int year) {
		// TODO Auto-generated method stub
		 List<Transaction> transactions = transactionRepository.findAll(TransactionFilter.byMonthAndYear(month, year));

	        List<TransactionDTO> transactionDTOS = modelMapper.map(transactions, new TypeToken<List<TransactionDTO>>() {
	        }.getType());

	        transactionDTOS.forEach(transactionDTO -> {
	            transactionDTO.setUser(null);
	            transactionDTO.setProduct(null);
	            transactionDTO.setSupplier(null);
	        });

	        return Response.builder()
	                .status(200)
	                .message("success")
	                .transactions(transactionDTOS)
	                .build();
	}

	@Override
	public Response updateTransactionStatus(Long transactionId, TransactionStatus status) {
		// TODO Auto-generated method stub
		Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));

        existingTransaction.setStatus(status);
        existingTransaction.setUpdateAt(LocalDateTime.now());

        transactionRepository.save(existingTransaction);

        return Response.builder()
                .status(200)
                .message("Transaction Status Successfully Updated")
                .build();
	}

}
