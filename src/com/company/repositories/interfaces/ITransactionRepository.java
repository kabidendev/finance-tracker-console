package com.company.repositories.interfaces;

import com.company.models.Transaction;
import com.company.models.dto.TransactionFullView;

import java.util.List;
public interface ITransactionRepository extends IRepository<Transaction> {
    boolean create(Transaction transaction);
    List<Transaction> getByUserId(int userId);
    List<TransactionFullView> getFullByUserId(int userId);
}
