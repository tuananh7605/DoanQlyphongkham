package com.example.doanphongkham.Manager;

import java.io.Serializable;

public class Invoice implements Serializable {
    public String name;
    public String birthDate;
    public String phone;
    public String gender;
    public String department;
    public String condition;
    public String examNumber;
    public int total;
    public String date;

    public Invoice(String name, String birthDate, String phone, String gender,
                   String department, String condition, String examNumber, int total) {
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.gender = gender;
        this.department = department;
        this.condition = condition;
        this.examNumber = examNumber;
        this.total = total;
    }
}
