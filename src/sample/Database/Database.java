package sample.Database;

import sample.Account;
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
    private static final String selectClientAccount = "SELECT * FROM account WHERE IDClient LIKE ?";
    private static final String insertNewAccount = "INSERT INTO account (accNum, amount, IDClient) VALUES (?, ?, ?)";


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
                Client client = new Client(result.getString(1), result.getString(2), result.getString(3), result.getInt(4));
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

    public List<Account> selectAccount(int ClientID) {
        Connection conn = getConn();
        System.out.println(ClientID);
        try {
            PreparedStatement stm = conn.prepareStatement(selectClientAccount);
            stm.setInt(1, ClientID);

            ResultSet result = stm.executeQuery();
            List<Account> accounts = new ArrayList<>();

            while (result.next()) {
                Account acc = new Account(result.getInt(1), result.getString(2), result.getFloat(3), result.getInt(4));
                accounts.add(acc);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConn(conn);
        return null;
    }

    public void inserNewAccount(int ClientID, String accNum, float money) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(insertNewAccount);
            stm.setString(1, accNum);
            stm.setFloat(2, money);
            stm.setInt(3, ClientID);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
