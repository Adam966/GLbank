package sample;

import java.sql.Timestamp;

public class CardTransaction {
    private Timestamp transCardDate;
    private float transCardAmount;
    private int IDCard;

    public CardTransaction(Timestamp transCardDate, float transCardAmount, int IDCard) {
        this.transCardDate = transCardDate;
        this.transCardAmount = transCardAmount;
        this.IDCard = IDCard;
    }

    public Timestamp getTransCardDate() {
        return transCardDate;
    }

    public float getTransCardAmount() {
        return transCardAmount;
    }

    public int getIDCard() {
        return IDCard;
    }
}
