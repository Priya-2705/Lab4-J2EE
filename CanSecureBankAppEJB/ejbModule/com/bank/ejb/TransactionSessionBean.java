package com.bank.ejb;

import jakarta.ejb.Stateful;
import java.io.Serializable;

@Stateful
public class TransactionSessionBean implements TransactionSessionRemote, Serializable {

    private String accountId;
    private double sessionBalance;

    public void beginSession(String accountId) {
        this.accountId = accountId;
        this.sessionBalance = AccountServiceBean.getAccountBalance(accountId); // static getter
    }

    public void deposit(double amount) {
        sessionBalance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= sessionBalance) {
            sessionBalance -= amount;
            return true;
        }
        return false;
    }

    public double getSessionBalance() {
        return sessionBalance;
    }

    public void commit() {
        AccountServiceBean.updateAccountBalance(accountId, sessionBalance); // static setter
    }

    public void cancel() {
        sessionBalance = AccountServiceBean.getAccountBalance(accountId); // reset
    }
}