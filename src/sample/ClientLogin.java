package sample;

import java.sql.Timestamp;

public class ClientLogin {
    private String login;
    private String password;
    private boolean status;
    private Timestamp lastLogin;

    public ClientLogin(String login, String password, boolean status, Timestamp lastLogin) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.lastLogin = lastLogin;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean getStatus() {
        return status;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }
}
