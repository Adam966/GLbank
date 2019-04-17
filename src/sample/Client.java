package sample;

public class Client {
    private String fName;
    private String lName;
    private String email;
    private int ID;

    public Client(String fName, String lName, String email, int ID) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.ID = ID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public int getID() { return ID; }
}
