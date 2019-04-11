package sample;

public class Employee {
    private String fName;
    private String lName;
    private int position;

    public Employee(String fName, String lName, int position) {
        this.fName = fName;
        this.lName = lName;
        this.position = position;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getPosition() {
        return position;
    }
}
