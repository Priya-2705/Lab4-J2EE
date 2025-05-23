CREATE DATABASE IF NOT EXISTS bankdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DROP DATABASE bankdb;

USE bankdb;

DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  customerId INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY (customerId)
) ENGINE=InnoDB;

CREATE TABLE account (
  accountId INT NOT NULL AUTO_INCREMENT,
  balance DOUBLE NOT NULL,
  customer_id INT,
  PRIMARY KEY (accountId),
  CONSTRAINT fk_account_customer FOREIGN KEY (customer_id)
    REFERENCES customer (customerId)
    ON DELETE CASCADE
) ENGINE=InnoDB;

SELECT * FROM Account;

SELECT * FROM Customer;

SHOW TABLES;