package com.bank.ejb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.ejb.Stateless;

@Stateless
public class AccountServiceBean implements AccountServiceRemote, AccountServiceLocal {
     
	private static Map<String, Double> accountStore = new ConcurrentHashMap<>();
	
	static {
		accountStore.put("100045", 1000.00);
		accountStore.put("100046", 250.00);
		accountStore.put("100047", 1500.00);
	}

	@Override
	public boolean deposit(String accountId, double amount) {
	    if (accountId != null && !accountId.isEmpty() && amount >= 0) {
	        double currentBalance = accountStore.getOrDefault(accountId, 0.0);
	        accountStore.put(accountId, currentBalance + amount);
	        return true;
	    }
	    return false;
	}

	@Override
	public boolean withdraw(String accountId, double amount) {
		// TODO Auto-generated method stub
		if (accountId != null && !accountId.isEmpty() && amount>=0 && amount <= accountStore.get(accountId)) {
			double balance = accountStore.get(accountId);
			double newBalance = balance - amount;
			accountStore.put(accountId, newBalance);
			return true;
		}
		return false;
	}

	@Override
	public double checkBalance(String accountId) {
		// TODO Auto-generated method stub
		return accountStore.getOrDefault(accountId, 0d);
	}
	
	public static double getAccountBalance(String accountId) {
	    return accountStore.getOrDefault(accountId, 0.0);
	}

	public static void updateAccountBalance(String accountId, double newBalance) {
	    accountStore.put(accountId, newBalance);
	}
}