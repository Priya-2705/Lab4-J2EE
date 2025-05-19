package com.bank.ejb;

import jakarta.ejb.Remote;

@Remote
public interface AccountServiceRemote {
     public boolean deposit(String accountId, double amount);
     public boolean withdraw(String accountId, double amount);
     public double checkBalance(String accountId);
}
