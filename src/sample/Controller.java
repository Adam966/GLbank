package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;

import java.awt.*;

public class Controller {
    public TextField login;
    public PasswordField pass;

    public void logIn(ActionEvent actionEvent) {

    }

    public String getLogin() {
        String name = login.getText();
        return name;
    }

    public String getPass() {
        String password = pass.getText();
        return password;
    }
}
