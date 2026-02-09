package com.company.models;

import com.company.models.enums.TransactionType;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.exceptions.NotFoundException;
import com.company.exceptions.ValidationException;
import com.company.repositories.AccountRepository;  // ← реальная реализация, не интерфейс

import java.sql.Timestamp;

public class IncomeTransaction extends Transaction {

    public IncomeTransaction(int userId, double amount, Integer categoryId,
                             Integer accountToId, String comment) {
        super(userId, TransactionType.INCOME, amount, categoryId, null, accountToId, comment);
    }

    public IncomeTransaction(Integer id, int userId, double amount, Integer categoryId,
                             Integer accountToId, Timestamp createdAt, String comment) {
        super(id, userId, TransactionType.INCOME, amount, categoryId, null, accountToId, createdAt, comment);
    }

    @Override
    public void execute(AccountRepository accountRepo)
            throws NotEnoughMoneyException, NotFoundException, ValidationException {

        Account to = accountRepo.getById(getAccountToId());   // ← getById, не findById

        if (to == null) {
            throw new NotFoundException("Счёт для зачисления дохода не найден: " + getAccountToId());
        }

        if (getAmount() <= 0) {
            throw new ValidationException("Сумма дохода должна быть больше 0");
        }

        double newBalance = to.getBalance() + getAmount();
        boolean success = accountRepo.updateBalance(to.getId(), newBalance);  // ← используем updateBalance

        if (!success) {
            throw new RuntimeException("Не удалось обновить баланс счёта дохода");
        }
    }
}