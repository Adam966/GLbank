package sample.Database;

import sample.*;

import java.lang.reflect.Executable;
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
    private static final String insertNewCard = "INSERT INTO card (PIN, active, expireY, expireM, IDAccount, cardNum) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String selectAllCards = "SELECT * FROM card INNER JOIN account ON account.ID = card.IDAccount WHERE accNum LIKE ?";
    private static final String selectClientLogin = "SELECT * FROM loginClient INNER JOIN loginHistory ON loginClient.ID = loginHistory.IDLoginClient WHERE IDClient LIKE ?";
    private static final String insertAccountStatus = "UPDATE loginClient SET status = ? WHERE IDClient LIKE ?";
    private static final String insertNewAccountPassword = "UPDATE loginClient SET password = ? WHERE IDClient  LIKE ?";
    private static final String insertNewIBClient = "INSERT INTO loginClient (login, password, IDClient) VALUE (?, ?, @@Identity)";
    private static final String insertMoney = "UPDATE account SET amount =  ? WHERE ID LIKE ?";
    private static final String setSelectAllTransaction = "SELECT * FROM transaction WHERE IDAccount LIKE ?";
    private static final String setSelectAllCardTransaction = "SELECT * FROM cardTrans WHERE IDCard LIKE ?";

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

    public void insertNewClient(String fName, String lName, String email, String name, String pass) {
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

        try {
            PreparedStatement stm = conn.prepareStatement(insertNewIBClient);
            stm.setString(1, name);
            stm.setString(2, pass);

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        closeConn(conn);
    }

    public List<Account> selectAccount(int ClientID) {
        Connection conn = getConn();
        System.out.println("Select Account: " + ClientID);

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
        closeConn(conn);
    }

    public void insertNewCard(String pin, int active, int year, int month, int accID, String cardNum) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(insertNewCard);
            stm.setString(1, pin);
            stm.setInt(2, active);
            stm.setInt(3, year);
            stm.setInt(4, month);
            stm.setInt(5, accID);
            stm.setString(6, cardNum);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConn(conn);
    }

    public List<Card> selectAllCards(String cardNum) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(selectAllCards);
            stm.setString(1, cardNum);

            ResultSet rs = stm.executeQuery();
            List<Card> cards = new ArrayList<>();

            while (rs.next()) {
                Card card = new Card(rs.getString(2), rs.getBoolean(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(1));
                cards.add(card);
            }
            return cards;
        }catch (Exception e) {
            e.printStackTrace();
        }
        closeConn(conn);
        return null;
    }

    public ClientLogin selectClientLogin(int IDClient) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(selectClientLogin);
            stm.setInt(1, IDClient);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                ClientLogin cl = new ClientLogin(rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getTimestamp(6));
                return cl;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertNewAccountPassword(int IDClient, String pass) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(insertNewAccountPassword);
            stm.setString(1, pass);
            stm.setInt(2, IDClient);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stm = conn.prepareStatement(insertAccountStatus);
            stm.setBoolean(1, true);
            stm.setInt(2, IDClient);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMoney(float money, int IDAccount) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(insertMoney);
            stm.setFloat(1, money);
            stm.setInt(2, IDAccount);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> selectAllTransaction(int IDAcc) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(setSelectAllTransaction);
            stm.setInt(1, IDAcc);

            ResultSet rs = stm.executeQuery();

            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                Transaction tr = new Transaction(rs.getFloat(2), rs.getTimestamp(3), rs.getString(4), rs.getInt(5));
                transactions.add(tr);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CardTransaction> selectAllCardTransaction(int IDCard) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(setSelectAllCardTransaction);
            stm.setInt(1, IDCard);

            ResultSet rs = stm.executeQuery();

            List<CardTransaction> cardTransactions = new ArrayList<>();
            while (rs.next()) {
                CardTransaction ctr = new CardTransaction(rs.getTimestamp(1), rs.getFloat(2), rs.getInt(3));
                cardTransactions.add(ctr);
            }
            return cardTransactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
