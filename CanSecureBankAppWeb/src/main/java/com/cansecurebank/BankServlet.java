package com.cansecurebank;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.bank.ejb.AccountServiceRemote;

@WebServlet("/bank")
public class BankServlet extends HttpServlet {
    private double mockBalance = 1000.0;
    
    @EJB
    private AccountServiceRemote accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String message = "";
        String accountId = "100045";

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
        }
        
        request.setAttribute("message", message);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}