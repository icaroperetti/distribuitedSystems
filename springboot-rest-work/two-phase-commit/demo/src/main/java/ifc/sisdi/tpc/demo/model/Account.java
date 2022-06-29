package ifc.sisdi.tpc.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private int accountNumber;
    private double balance;

    public Account(@JsonProperty("account") int accountNumber, @JsonProperty("balance") double balance) {
        super();
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccount() {
        return accountNumber;
    }

    public void setAccountNumber(int id) {
        this.accountNumber = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean balanceIsZero(double value) {
        return (this.balance - value) < 0;
    }

    public double withdraw(double value) {
        this.balance -= value;
        return this.balance;
    }

    public double deposit(double value) {
        this.balance += value;
        return this.balance;
    }
}
