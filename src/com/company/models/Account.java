package com.company.models;

public class Account {
    private Integer id;
    private int userId;
    private String name;
    private double balance;

    public Account() {}

    public Account(Integer id, int userId, String name, double balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public Integer getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    public void setId(Integer id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setBalance(double balance) { this.balance = balance; }
}

