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
    private static final String selectAllClients = "SELECT * FROM client INNER JOIN account ON client.ID = Account.IDClient";

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
                Client client = new Client(result.getString(1), result.getString(2), result.getString(3), result.getInt(5), result.getInt(6), Float.valueOf(result.getInt(7)));
                clients.add(client);
            }
            return clients;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
