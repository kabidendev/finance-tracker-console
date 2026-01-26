package com.company.models.dto;

import java.sql.Timestamp;

public class TransactionFullView {
    private int id;
    private String type;
    private double amount;
    private Timestamp createdAt;
    private String comment;

    private String fromAccountName;
    private String toAccountName;

    private String categoryName;
    private String categoryType;

    public TransactionFullView() {}

    public TransactionFullView(int id, String type, double amount, Timestamp createdAt, String comment,
                               String fromAccountName, String toAccountName,
                               String categoryName, String categoryType) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
        this.comment = comment;
        this.fromAccountName = fromAccountName;
        this.toAccountName = toAccountName;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Timestamp getCreatedAt() { return createdAt; }
    public String getComment() { return comment; }
    public String getFromAccountName() { return fromAccountName; }
    public String getToAccountName() { return toAccountName; }
    public String getCategoryName() { return categoryName; }
    public String getCategoryType() { return categoryType; }

    public void setId(int id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setComment(String comment) { this.comment = comment; }
    public void setFromAccountName(String fromAccountName) { this.fromAccountName = fromAccountName; }
    public void setToAccountName(String toAccountName) { this.toAccountName = toAccountName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setCategoryType(String categoryType) { this.categoryType = categoryType; }

    @Override
    public String toString() {
        return "TransactionFullView{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", createdAt='" + createdAt + '\'' +
                ", comment='" + comment + '\'' +
                ", fromAccountName='" + fromAccountName + '\'' +
                ", toAccountName='" + toAccountName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }
}
