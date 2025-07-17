package com.example.doanphongkham.KeToan;

public class RevenueItem {
    private int id;
    private String date;
    private double amount;

    public RevenueItem(int id, String date, double amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public double getAmount() { return amount; }
}
