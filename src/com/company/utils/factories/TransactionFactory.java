package com.company.utils.factories;

import com.company.models.Transaction;
import com.company.models.enums.TransactionType;

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
        );
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
        );
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
        );
    }
}