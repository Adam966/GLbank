package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Database.Database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewClientForm {

    public TextField fname;
    public TextField lname;
    public TextField email;
    public TextField IBName;
    public TextField password;
    public TextField Repassword;
    public Text wrongPass;
    public Text regex;

    Database database = Database.getInstance();

    public void createClient(ActionEvent actionEvent) {
        String pattern = "[A-Za-z0-9]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password.getText());

        if (m.matches()) {
            if (password.getText().equals(Repassword.getText())) {
                database.insertNewClient(fname.getText(), lname.getText(), email.getText(), password.getText(), IBName.getText());
                regex.setText("");
                closeWindow();
            }
            else
                wrongPass.setText("Passwords are diffrent");
        } else
            regex.setText("Password must contain upper and lower letters and numbers");



    }

    public void closeWindow() {
        Stage setClient = (Stage) fname.getScene().getWindow();
        setClient.close();
    }
}
