package com.company.models;

import com.company.models.enums.TransactionType;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.exceptions.NotFoundException;
import com.company.exceptions.ValidationException;
import com.company.repositories.AccountRepository;

import java.sql.Timestamp;

public class ExpenseTransaction extends Transaction {

    public ExpenseTransaction(int userId, double amount, Integer categoryId,
                              Integer accountFromId, String comment) {
        super(userId, TransactionType.EXPENSE, amount, categoryId, accountFromId, null, comment);
    }

    public ExpenseTransaction(Integer id, int userId, double amount, Integer categoryId,
                              Integer accountFromId, Timestamp createdAt, String comment) {
        super(id, userId, TransactionType.EXPENSE, amount, categoryId, accountFromId, null, createdAt, comment);
    }

    @Override
    public void execute(AccountRepository accountRepo)
            throws NotEnoughMoneyException, NotFoundException, ValidationException {

        Account from = accountRepo.getById(getAccountFromId());

        if (from == null) {
            throw new NotFoundException("Счёт для списания расхода не найден: " + getAccountFromId());
        }

        if (getAmount() <= 0) {
            throw new ValidationException("Сумма расхода должна быть больше 0");
        }

        if (from.getBalance() < getAmount()) {
            throw new NotEnoughMoneyException("Недостаточно средств на счёте");
        }

        double newBalance = from.getBalance() - getAmount();
        boolean success = accountRepo.updateBalance(from.getId(), newBalance);

        if (!success) {
            throw new RuntimeException("Не удалось обновить баланс счёта расхода");
        }
    }
}