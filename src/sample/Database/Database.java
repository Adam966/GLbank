package sample.Database;

import sample.Employee;
import sample.Globals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static final String CHECK_USER = "SELECT * FROM employeeID;";

    private static Database dab = new Database();

    private Database() {
    }

    public static Database getInstance() {
        return dab;
    }

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

    public Employee getEmployee(String log, String pass) {
        Connection conn = getConn();

        try {
            PreparedStatement stm = conn.prepareStatement(CHECK_USER);

            stm.executeUpdate();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
