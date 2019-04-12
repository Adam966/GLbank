package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Database.Database;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
    public TextField name;
    public PasswordField pass;
    public Text failedLogin;

    public void logIn(ActionEvent actionEvent) {
        String logName = name.getText();
        String logPass = pass.getText();

        Database database = Database.getInstance();
        Employee employee = database.checkLogin(logName, logPass);

        if (employee instanceof Employee) {
            System.out.println("Succes");
            mainPage(database.checkLogin(logName, logPass));
        } else
            failedLogin.setText("Wrong password or Name");
    }

    public void mainPage(Employee employee)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("mainPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 802 , 605);
            Stage stage = new Stage();
            stage.setTitle("GL bank");
            stage.setScene(scene);
            stage.show();

            Stage old = (Stage) name.getScene().getWindow();
            old.close();
        } catch (IOException e) { }
    }
}
