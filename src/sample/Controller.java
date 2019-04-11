package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import sample.Database.Database;
import javafx.scene.control.TextField;

public class Controller {
    public TextField name;
    public PasswordField pass;

    public void logIn(ActionEvent actionEvent) {
        String logName = name.getText();
        String logPass = pass.getText();

        Database database = Database.getInstance();
        Employee employee = database.checkLogin(logName, logPass);

    }
}
