package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.Database;

public class NewClientForm {

    public TextField fname;
    public TextField lname;
    public TextField email;

    public void createClient(ActionEvent actionEvent) {
        Database database = Database.getInstance();

        database.insertNewClient(fname.getText(), lname.getText(), email.getText());
    }

    public void closeWindow() {
        Stage setClient = (Stage) fname.getScene().getWindow();
        setClient.close();
    }
}
