package com.codingblocks.noida.billsplit.model;

import java.util.Set;

/**
 * Created by hp on 5/15/2018.
 */
public class Transaction {
    public int id;
    public String payer;
    public int amount;
    public String note;

    public Transaction(String payer, int amount, String note) {
        this.payer = payer;
        this.amount = amount;
        this.note = note;
    }
}
