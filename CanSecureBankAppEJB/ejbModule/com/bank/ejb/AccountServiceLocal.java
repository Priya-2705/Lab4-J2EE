package com.bank.ejb;

import jakarta.ejb.Local;

@Local
public interface AccountServiceLocal {
    boolean deposit(String accountId, double amount);
    boolean withdraw(String accountId, double amount);
    double checkBalance(String accountId);
}