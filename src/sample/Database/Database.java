package sample.Database;

import sample.Client;
import sample.Employee;
import sample.Globals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database dab = new Database();
    private Database() {};
    public static Database getInstance() { return dab; }
    private static final String checkLoginSQL = "SELECT * FROM employee INNER JOIN loginEmployee ON employee.ID = loginEmployee.IDEmployee WHERE login LIKE ? AND password LIKE ?";
    private static final String selectAllClients = "SELECT * FROM client";
    private static final String insertNewClient = "INSERT INTO client (fname, lname, email) VALUES (?, ?, ?)";
    private static final String selectClientAccount = "SELECT * FROM client INNER JOIN account ON client.ID = account.IDClient WHERE client.ID LIKE ?";


    private Connection getConn()
    {
        Connection conn;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
            conn = DriverManager.getConnection(Globals.host, Globals.userName, Globals.password);
            return conn;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void closeConn(Connection conn){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Employee checkLogin(String login, String pass) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(checkLoginSQL);
            stm.setString(1, login);
            stm.setString(2, pass);

            ResultSet result = stm.executeQuery();

            if(result.next() == true) {
                Employee employee = new Employee(result.getString("fname"), result.getString("lname"), result.getInt("position"));
                closeConn(conn);
                employee.getfName();
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConn(conn);
        return null;
    }

    public List<Client> getAllClients() {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(selectAllClients);

            ResultSet result = stm.executeQuery();
            List<Client> clients = new ArrayList<>();

            while (result.next()) {
                Client client = new Client(result.getString(1), result.getString(2), result.getString(3), result.getInt(4), null, 0);
                clients.add(client);
            }
            closeConn(conn);
            return clients;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        closeConn(conn);
        return null;
    }

    public void insertNewClient(String fName, String lName, String email) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(insertNewClient);
            stm.setString(1, fName);
            stm.setString(2, lName);
            stm.setString(3, email);

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConn(conn);
    }

    public void selectAccount(int ClientID) {
        Connection conn = getConn();
        System.out.println(ClientID);
        try {
            PreparedStatement stm = conn.prepareStatement(selectClientAccount);
            stm.setInt(1, ClientID);

            ResultSet result = stm.executeQuery();
            result.next();
            System.out.println(result.getString(6));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConn(conn);
    }
}
