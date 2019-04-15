package sample;

public class Client {
    private String fName;
    private String lName;
    private String email;

    private String accNum;
    private float amount;
    private int accID;

    public Client(String fName, String lName, String email ,int accID ,String accNum, float money) {
        this.fName = fName;
        this.lName = lName;
        this.accNum = accNum;
        this.amount = money;
        this.accID = accID;
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getAccNum() {
        return accNum;
    }

    public float getMoney() {
        return amount;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getClientID() {
        return accID;
    }

    public String getEmail() {
        return email;
    }
}
