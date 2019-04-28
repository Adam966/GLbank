package sample;

public class Card {
    private String PIN;
    private boolean active;
    private int expireY;
    private int expireM;
    private int IDAccount;
    private String cardNum;
    private int ID;

    public Card(String PIN, boolean active, int expireY, int expireM, int IDAccount, String cardNum, int ID) {
        this.PIN = PIN;
        this.active = active;
        this.expireY = expireY;
        this.expireM = expireM;
        this.IDAccount = IDAccount;
        this.cardNum = cardNum;
        this.ID = ID;
    }

    public String getPIN() {
        return PIN;
    }

    public boolean isActive() {
        return active;
    }

    public int getExpireY() {
        return expireY;
    }

    public int getExpireM() {
        return expireM;
    }

    public int getIDAccount() {
        return IDAccount;
    }

    public String getCardNum() {
        return cardNum;
    }

    public int getID() {
        return ID;
    }
}
