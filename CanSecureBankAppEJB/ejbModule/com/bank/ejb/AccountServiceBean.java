package com.bank.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bank.ejb.model.Account;
import com.bank.ejb.model.Customer;

import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateful(name = "ejb/AccountServiceBean")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccountServiceBean implements AccountServiceRemote {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public boolean deposit(String accountId, double amount) {
        Account account = em.find(Account.class, Integer.parseInt(accountId));
        account.setBalance(account.getBalance() + amount);
        return true;
    }

    @Override
    public boolean withdraw(String accountId, double amount) {
        Account account = em.find(Account.class, Integer.parseInt(accountId));
        if (account.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient funds");
        account.setBalance(account.getBalance() - amount);
        return true;
    }

    @Override
    public double checkBalance(String accountId) {
        Account account = em.find(Account.class, Integer.parseInt(accountId));
        return account.getBalance();
    }

    @Override
    public Customer createCustomer(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        em.persist(customer);
        return customer;
    }

    @Override
    public Account createAccount(String customerId, double initialBalance) {
        Customer customer = em.find(Customer.class, Integer.parseInt(customerId));
        if (customer == null)
            throw new IllegalArgumentException("Customer not found");

        Account account = new Account();
        account.setBalance(initialBalance);
        account.setCustomer(customer);
        customer.getAccounts().add(account);

        em.persist(account);
        return account;
    }

    @Override
    public List<Account> getCustomerAccounts(String customerId) {
        return em.createQuery("SELECT a FROM Account a WHERE a.customer.customerId = :cid", Account.class)
                 .setParameter("cid", Integer.parseInt(customerId))
                 .getResultList();
    }

    @Override
    public Set<String> getAccountCodes(String customerId) {
        List<Account> accounts = getCustomerAccounts(customerId);
        Set<String> accountIds = new HashSet<>();
        for (Account account : accounts) {
            accountIds.add(String.valueOf(account.getAccountId()));
        }
        return accountIds;
    }
}