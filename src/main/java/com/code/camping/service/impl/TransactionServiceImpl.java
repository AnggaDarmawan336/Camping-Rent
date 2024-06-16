package com.code.camping.service.impl;

import com.code.camping.entity.Product;
import com.code.camping.entity.Transaction;
import com.code.camping.entity.User;
import com.code.camping.entity.Wallet;
import com.code.camping.repository.TransactionRepository;
import com.code.camping.repository.WalletRepository;

import com.code.camping.service.ProductService;
import com.code.camping.service.TransactionService;
import com.code.camping.service.UserService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.GeneralSpecification;
import com.code.camping.utils.dto.request.TransactionRequest;
import com.code.camping.utils.dto.request.WalletRequest;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transaction_repository;
    private final WalletService wallet_service;
    private final ProductService product_service;

    @Override
    public Transaction create(TransactionRequest request,String id) {

        Wallet wallet = wallet_service.fineByUserId(id);
        String product_id = request.getProduct_id();
        Product product = product_service.getById(product_id);
        WalletRequest wallet_request = new WalletRequest();

        
       
        Integer product_price = product.getPrice();
        Integer balance = wallet.getBalance();
        Integer total_price = request.getDay() * product_price;

        if (balance >= total_price && id.equals(wallet_service.fineByUserId(id).getUser().getId())) {

            wallet_request.setId(wallet.getId());
            wallet_request.setBalance(wallet.getBalance());
            wallet_request.setUser_id(id);
            wallet_request.setWalletType(wallet.getWalletType());
            wallet_request.setBalance(balance - total_price);
            request.setUser_id(id);
            wallet_service.update(wallet_request);
           

            return transaction_repository.saveAndFlush(request.convert());
        } 

        throw new NullPointerException("TOP UP DULU BOS");
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, TransactionRequest request) {
        Specification<Transaction> specification = GeneralSpecification.get_specification(request);
        return transaction_repository.findAll(specification, pageable);
    }

    @Override
    public Transaction getById(String id) {
        return transaction_repository.findById(id)
        .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "User with id " + id + " is not found"));
    }

    @Override
    public Transaction update(TransactionRequest request) {
        return transaction_repository.saveAndFlush(request.convert());
    }

    @Override
    public void delete(String id) {
        this.getById(id);
        transaction_repository.deleteById(id);
    }

    @Override
    public List<Transaction> findByUserId(String userId) {
        return transaction_repository.findByUserId(userId);
        
    }
}
