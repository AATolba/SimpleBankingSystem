package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
@Document
@AllArgsConstructor
public class User {
    @Id
    private int id;
    private String name,email,contactNumber,accountNumber;
    private int age;
    private long balance;
    private ArrayList<String> transHistory = new ArrayList<>();
    private ArrayList<Integer> transactions;


}