package com.bank.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bank.ejb.model.Account;
import com.bank.ejb.model.Customer;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.Queue;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateful(name = "ejb/AccountServiceBean")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@JMSDestinationDefinition(
    name = "java:/jms/queue/TransactionQueue",
    interfaceName = "jakarta.jms.Queue",
    destinationName = "TransactionQueue"
)
public class AccountServiceBean implements AccountServiceRemote {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/TransactionQueue")
    private Queue transactionQueue;

    private void sendTransactionMessage(String accountId, String type, double amount) {
        try (JMSContext context = connectionFactory.createContext()) {
            JsonObject json = Json.createObjectBuilder()
                    .add("accountId", accountId)
                    .add("type", type)
                    .add("amount", amount)
                    .add("timestamp", System.currentTimeMillis())
                    .build();
            context.createProducer().send(transactionQueue, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deposit(String accountId, double amount) {
        Account account = em.find(Account.class, Integer.parseInt(accountId));
        account.setBalance(account.getBalance() + amount);
        sendTransactionMessage(accountId, "Deposit", amount);
        return true;
    }

    @Override
    public boolean withdraw(String accountId, double amount) {
        Account account = em.find(Account.class, Integer.parseInt(accountId));
        if (account.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient funds");
        account.setBalance(account.getBalance() - amount);
        sendTransactionMessage(accountId, "Withdraw", amount);
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