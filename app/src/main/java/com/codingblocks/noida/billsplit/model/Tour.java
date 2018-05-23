package com.codingblocks.noida.billsplit.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hp on 5/15/2018.
 */
public class Tour {
    public String id;
    public String name;
    public Set<String> users;

    public List<Transaction> transactions;

    public Tour(){
        users = new HashSet<>();
        transactions = new ArrayList<>();
    }

    public Tour(String id, String name) {
        this.id = id;
        this.name = name;
        users = new HashSet<>();
        transactions = new ArrayList<>();
    }
}
