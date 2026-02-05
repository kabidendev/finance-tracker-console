package com.company.models;

import com.company.models.enums.TransactionType;
import com.company.exceptions.NotEnoughMoneyException;
import com.company.exceptions.NotFoundException;
import com.company.exceptions.ValidationException;
import com.company.repositories.AccountRepository;

import java.sql.Timestamp;

public class TransferTransaction extends Transaction {

    public TransferTransaction(int userId, double amount,
                               Integer accountFromId, Integer accountToId, String comment) {
        super(userId, TransactionType.TRANSFER, amount, null, accountFromId, accountToId, comment);
    }

    public TransferTransaction(Integer id, int userId, double amount,
                               Integer accountFromId, Integer accountToId,
                               Timestamp createdAt, String comment) {
        super(id, userId, TransactionType.TRANSFER, amount, null, accountFromId, accountToId, createdAt, comment);
    }

    @Override
    public void execute(AccountRepository accountRepo)
            throws NotEnoughMoneyException, NotFoundException, ValidationException {

        Account from = accountRepo.getById(getAccountFromId());
        Account to   = accountRepo.getById(getAccountToId());

        if (from == null) {
            throw new NotFoundException("Счёт-источник для перевода не найден");
        }
        if (to == null) {
            throw new NotFoundException("Счёт-получатель для перевода не найден");
        }

        if (getAmount() <= 0) {
            throw new ValidationException("Сумма перевода должна быть больше 0");
        }

        if (from.getBalance() < getAmount()) {
            throw new NotEnoughMoneyException("Недостаточно средств на счёте-источнике");
        }

        // Списание с from
        double newFromBalance = from.getBalance() - getAmount();
        boolean successFrom = accountRepo.updateBalance(from.getId(), newFromBalance);

        if (!successFrom) {
            throw new RuntimeException("Не удалось списать средства с исходного счёта");
        }

        // Зачисление на to
        double newToBalance = to.getBalance() + getAmount();
        boolean successTo = accountRepo.updateBalance(to.getId(), newToBalance);

        if (!successTo) {
            // Здесь можно откатить списание, но для простоты оставим как есть
            throw new RuntimeException("Не удалось зачислить средства на целевой счёт");
        }
    }
}
