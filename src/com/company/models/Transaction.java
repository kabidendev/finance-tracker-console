package com.company.models;

import com.company.models.enums.TransactionType;

import java.sql.Timestamp;

public class Transaction {
    private Integer id;
    private int userId;
    private TransactionType type;
    private double amount;
    private Integer categoryId;
    private Integer accountFromId;
    private Integer accountToId;
    private Timestamp createdAt;
    private String comment;



    public Transaction(Integer id, int userId, TransactionType type, double amount,
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

    public Integer getId() {
        return id; }
    public int getUserId() {
        return userId; }
    public TransactionType getType() {
        return type; }
    public double getAmount() {
        return amount; }
    public Integer getCategoryId() {
        return categoryId; }
    public Integer getAccountFromId() {
        return accountFromId; }
    public Integer getAccountToId() {
        return accountToId; }
    public Timestamp getCreatedAt() {
        return createdAt; }
    public String getComment() {
        return comment; }

    public void setId(Integer id) {
        this.id = id; }
    public void setUserId(int userId) {
        this.userId = userId; }
    public void setType(TransactionType type) {
        this.type = type; }
    public void setAmount(double amount) {
        this.amount = amount; }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId; }
    public void setAccountFromId(Integer accountFromId) {
        this.accountFromId = accountFromId; }
    public void setAccountToId(Integer accountToId) {
        this.accountToId = accountToId; }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt; }
    public void setComment(String comment) {
        this.comment = comment; }
}


