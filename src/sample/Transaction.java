package sample;

import java.sql.Timestamp;

public class Transaction {
    private float transAmount;
    private Timestamp transDate;
    private String ReAccount;
    private int IDAccount;

    public Transaction(float transAmount, Timestamp transDate, String reAccount, int IDAccount) {
        this.transAmount = transAmount;
        this.transDate = transDate;
        this.ReAccount = reAccount;
        this.IDAccount = IDAccount;
    }

    public float getTransAmount() {
        return transAmount;
    }

    public Timestamp getTransDate() {
        return transDate;
    }

    public String getReAccount() {
        return ReAccount;
    }

    public int getIDAccount() {
        return IDAccount;
    }

}
