package com.bank.ejb;

import java.util.List;
import java.util.Set;

import com.bank.ejb.model.Account;
import com.bank.ejb.model.Customer;

import jakarta.ejb.Remote;

@Remote
public interface AccountServiceRemote {
     public boolean deposit(String accountId, double amount);
     public boolean withdraw(String accountId, double amount);
     public double checkBalance(String accountId);
     
     Customer createCustomer(String name, String email);
     Account createAccount(String customerId, double initialBalance);
     List<Account> getCustomerAccounts(String customerId);
     Set<String> getAccountCodes(String customerId);
}
