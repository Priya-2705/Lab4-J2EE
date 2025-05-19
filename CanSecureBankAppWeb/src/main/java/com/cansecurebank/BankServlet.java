package com.cansecurebank;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.bank.ejb.AccountServiceRemote;
import com.bank.ejb.TransactionSessionRemote;

@WebServlet("/bank")
public class BankServlet extends HttpServlet {
    
    @EJB
    private AccountServiceRemote accountService; // Stateless bean

    @EJB
    private TransactionSessionRemote transactionSession; // Stateful bean

    @Override
    public void init() throws ServletException {
        super.init();
        // Optional: Initialize session with a fixed account
        transactionSession.beginSession("100045");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String message = "";
        String accountId = "100045"; // Simulated single-user for now

        try {
            if ("deposit".equals(action)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                accountService.deposit(accountId, amount);
                double updatedBalance = accountService.checkBalance(accountId);
                message = "Deposited $" + amount + ". New balance: $" + updatedBalance;

            } else if ("balance".equals(action)) {
                double balance = accountService.checkBalance(accountId);
                message = "Current balance: $" + balance;

            } else if ("withdraw".equals(action)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                boolean success = accountService.withdraw(accountId, amount);
                double balance = accountService.checkBalance(accountId);
                message = success ? "Withdrew $" + amount + ". New balance: $" + balance
                                  : "Withdrawal failed! Check balance.";

            } else if ("depositSession".equals(action)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                transactionSession.deposit(amount);
                message = "Deposited $" + amount + " in session.";

            } else if ("withdrawSession".equals(action)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                boolean success = transactionSession.withdraw(amount);
                message = success ? "Withdrew $" + amount + " in session." 
                                  : "Insufficient funds in session.";

            } else if ("sessionBalance".equals(action)) {
                double sessionBalance = transactionSession.getSessionBalance();
                message = "Session balance: $" + sessionBalance;

            } else if ("commit".equals(action)) {
                transactionSession.commit();
                message = "Transaction committed successfully.";

            } else if ("cancel".equals(action)) {
                transactionSession.cancel();
                message = "Transaction session cancelled.";

            } else if ("startSession".equals(action)) {
                transactionSession.beginSession(accountId);
                message = "Transaction session started for Account ID: " + accountId;
            }

        } catch (Exception e) {
            message = "Error occurred: " + e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}