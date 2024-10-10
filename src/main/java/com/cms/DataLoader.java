package com.cms;


import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cms.entity.Transaction;
import com.cms.repository.TransactionRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public DataLoader(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        transactionRepository.saveAll(Arrays.asList(
                new Transaction("1", "123456", "654321", BigDecimal.valueOf(100), "IFSC001", "Alice", "Bob", "Pending"),
                new Transaction("2", "987654", "456789", BigDecimal.valueOf(200), "IFSC002", "Charlie", "Dave", "Pending")
        ));
    }
}
