package com.bank.ejb;

import jakarta.ejb.Remote;

@Remote
public interface TransactionSessionRemote {
    void beginSession(String accountId);
    void deposit(double amount);
    boolean withdraw(double amount);
    double getSessionBalance();
    void commit();
    void cancel();
}