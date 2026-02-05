package com.company.utils.factories;

import com.company.models.Transaction;
import com.company.models.enums.TransactionType;
import com.company.repositories.AccountRepository;

public class TransactionFactory {


    public static Transaction createIncome(
            Integer userId,
            Integer accountToId,
            Integer categoryId,
            double amount,
            String comment) {

        return new Transaction(
                null,
                userId,
                TransactionType.INCOME,
                amount,
                categoryId,
                null,
                accountToId,
                null,
                comment
        ) {
            @Override
            public void execute(AccountRepository accountRepo) {

            }
        };
    }


    public static Transaction createExpense(
            Integer userId,
            Integer accountFromId,
            Integer categoryId,
            double amount,
            String comment) {

        return new Transaction(
                null,
                userId,
                TransactionType.EXPENSE,
                amount,
                categoryId,
                accountFromId,
                null,
                null,
                comment
        ) {
            @Override
            public void execute(AccountRepository accountRepo) {

            }
        };
    }


    public static Transaction createTransfer(
            Integer userId,
            Integer fromAccountId,
            Integer toAccountId,
            double amount,
            String comment) {

        return new Transaction(
                null,
                userId,
                TransactionType.TRANSFER,
                amount,
                null,
                fromAccountId,
                toAccountId,
                null,
                comment
        ) {
            @Override
            public void execute(AccountRepository accountRepo) {

            }
        };
    }
}