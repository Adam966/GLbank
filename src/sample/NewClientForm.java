package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.Database;

public class NewClientForm {

    public TextField fname;
    public TextField lname;
    public TextField email;
    public TextField IBName;

    Database database = Database.getInstance();

    public void createClient(ActionEvent actionEvent) {
        database.insertNewClient(fname.getText(), lname.getText(), email.getText());
    }

    public String createIBClient() {
        String chars = "ab7c0de2fgh1ijklm4n3opq5rst6uv8wxyz9";
        String password = "";

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0)
                password+= Character.toString(chars.charAt((int)(Math.floor(Math.random() * 36))));
            else
                password+= Character.toString(chars.charAt((int)(Math.floor(Math.random() * 36)))).toUpperCase();
        }
        return password;
    }

    public void closeWindow() {
        Stage setClient = (Stage) fname.getScene().getWindow();
        setClient.close();
    }
}
