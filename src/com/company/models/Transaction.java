package com.company.models;

import com.company.models.enums.TransactionType;
import com.company.repositories.AccountRepository;

import java.sql.Timestamp;

public abstract class Transaction {

    private Integer id;
    private final int userId;
    private final TransactionType type;
    private final double amount;
    private final Integer categoryId;
    private final Integer accountFromId;
    private final Integer accountToId;
    private final Timestamp createdAt;
    private final String comment;


    protected Transaction(int userId, TransactionType type, double amount,
                          Integer categoryId, Integer accountFromId, Integer accountToId,
                          String comment) {
        this.id = null;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.categoryId = categoryId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.comment = comment;
    }


    protected Transaction(Integer id, int userId, TransactionType type, double amount,
                          Integer categoryId, Integer accountFromId, Integer accountToId,
                          Timestamp createdAt, String comment) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.categoryId = categoryId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.createdAt = createdAt;
        this.comment = comment;
    }


    public abstract void execute(AccountRepository accountRepo);


    public Integer getId() { return id; }
    public int getUserId() { return userId; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getAccountFromId() { return accountFromId; }
    public Integer getAccountToId() { return accountToId; }
    public Timestamp getCreatedAt() { return createdAt; }
    public String getComment() { return comment; }


    public void setId(Integer id) {
        if (this.id != null) throw new IllegalStateException("ID уже установлен");
        this.id = id;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", from=" + accountFromId +
                ", to=" + accountToId +
                ", createdAt=" + createdAt +
                ", comment='" + comment + '\'' +
                '}';
    }
}


