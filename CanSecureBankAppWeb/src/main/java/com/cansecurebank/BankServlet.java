package com.cansecurebank;

import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Set;

import com.bank.ejb.AccountServiceRemote;
import com.bank.ejb.model.Account;
import com.bank.ejb.model.Customer;

@WebServlet("/bank")
public class BankServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private AccountServiceRemote accountService;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String message = "";
		
		try {
			switch (action) {
				case "createCustomer":
					String name = request.getParameter("name");
					String email = request.getParameter("email");
					Customer customer = accountService.createCustomer(name, email);
					message = "Customer created successfully. ID: " + customer.getCustomerId();
					break;

				case "createAccount":
					String custId = request.getParameter("customerId");
					double initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
					Account account = accountService.createAccount(custId, initialBalance);
					message = "Account created successfully. ID: " + account.getAccountId();
					break;

				case "listAccounts":
					String listCustId = request.getParameter("customerId");
					Set<String> accountCodes = accountService.getAccountCodes(listCustId);
					request.setAttribute("accounts", accountCodes);
					request.getRequestDispatcher("list.jsp").forward(request, response);
					return;

				case "deposit":
					String depositId = request.getParameter("accountId");
					double depositAmount = Double.parseDouble(request.getParameter("amount"));
					accountService.deposit(depositId, depositAmount);
					double newBalance = accountService.checkBalance(depositId);
					message = "Deposited $" + depositAmount + ". New balance: $" + newBalance;
					break;

				case "withdraw":
					String withdrawId = request.getParameter("accountId");
					double withdrawAmount = Double.parseDouble(request.getParameter("amount"));
					accountService.withdraw(withdrawId, withdrawAmount);
					double balanceAfterWithdraw = accountService.checkBalance(withdrawId);
					message = "Withdrew $" + withdrawAmount + ". New balance: $" + balanceAfterWithdraw;
					break;

				case "balance":
					String balanceId = request.getParameter("accountId");
					double balance = accountService.checkBalance(balanceId);
					message = "Current balance: $" + balance;
					break;

				default:
					message = "Unknown action!";
			}
		} catch (Exception e) {
			message = "Error occurred: " + e.getMessage();
			e.printStackTrace();
		}

		request.setAttribute("message", message);
		request.getRequestDispatcher("result.jsp").forward(request, response);
	}
}