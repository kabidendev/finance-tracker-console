package com.company.controllers;

import com.company.controllers.interfaces.IReportController;
import com.company.models.dto.TransactionFullView;
import com.company.repositories.interfaces.ITransactionRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ReportController implements IReportController {

    private final ITransactionRepository transactionRepository;

    public ReportController(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionFullView> getLastNTransactions(int userId, int n) {
        return transactionRepository.getFullByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(TransactionFullView::getCreatedAt).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getTopExpenseCategories(int userId) {
        return transactionRepository.getFullByUserId(userId)
                .stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .collect(Collectors.groupingBy(
                        TransactionFullView::getCategoryName,
                        Collectors.summingDouble(TransactionFullView::getAmount)
                ));
    }
}