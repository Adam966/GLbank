package sample;

public class Client {
    private String fName;
    private String lName;
    private String email;

    private int accNum;
    private float amount;
    private int accID;

    public Client(String fName, String lName, String email ,int accID ,int accNum, float money) {
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

    public int getAccNum() {
        return accNum;
    }

    public float getMoney() {
        return amount;
    }

    public int getClientID() {
        return accID;
    }

    public String getEmail() {
        return email;
    }
}
