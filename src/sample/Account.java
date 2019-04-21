package sample;

public class Account {
    private String accNum;
    private float money;
    private int IDClient;
    private int ID;

    public Account(int ID, String accNum, float money, int IDClient) {
        this.ID = ID;
        this.accNum = accNum;
        this.money = money;
        this.IDClient = IDClient;
    }

    public String getAccNum() {
        return accNum;
    }

    public float getMoney() {
        return money;
    }

    public int getIDClient() {
        return IDClient;
    }

    public int getID() { return ID; }
}
