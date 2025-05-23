package com.cansecurebank;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

import com.bank.ejb.AccountServiceRemote;
import com.bank.ejb.model.Customer;
import com.bank.ejb.model.Account;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @EJB
    private AccountServiceRemote accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Create a new customer
            Customer customer = accountService.createCustomer("Alice Smith", "alice@example.com");

            // Create an account for that customer
            Account account = accountService.createAccount(
                String.valueOf(customer.getCustomerId()), // Convert Integer ID to String
                500.0
            );

            // Print success message
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Customer and Account Created Successfully!</h2>");
            resp.getWriter().println("<p>Customer ID: " + customer.getCustomerId() + "</p>");
            resp.getWriter().println("<p>Account ID: " + account.getAccountId() + "</p>");
            resp.getWriter().println("<a href='index.jsp'>Back to Home</a>");
            resp.getWriter().println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Error</h2>");
            resp.getWriter().println("<p>" + e.getMessage() + "</p>");
            resp.getWriter().println("<a href='index.jsp'>Back to Home</a>");
            resp.getWriter().println("</body></html>");
        }
    }
}