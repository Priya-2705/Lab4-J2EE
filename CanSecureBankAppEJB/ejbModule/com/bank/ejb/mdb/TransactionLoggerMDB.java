package com.bank.ejb.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/TransactionQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
    }
)
public class TransactionLoggerMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                System.out.println("Received Transaction: " + json);
                logToFile(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void logToFile(String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaction_log.txt", true))) {
            writer.write(data);
            writer.newLine();
        }
    }
}