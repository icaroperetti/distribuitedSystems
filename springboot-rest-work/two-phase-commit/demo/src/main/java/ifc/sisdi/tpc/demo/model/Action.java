package ifc.sisdi.tpc.demo.model;

public class Action {
    private int id;
    private String operation; // deposit or withdraw
    private int account; // account number
    private double value; // amount to deposit or withdraw

    public Action(int id, String operation, int account, double value) {
        super();
        this.id = id;
        this.operation = operation;
        this.account = account;
        this.value = value;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
