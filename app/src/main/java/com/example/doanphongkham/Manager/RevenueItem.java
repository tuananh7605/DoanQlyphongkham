package com.example.doanphongkham.Manager;

public class RevenueItem {
    private String date;
    private String type;
    private int amount;

    public RevenueItem(String date, String type, int amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
