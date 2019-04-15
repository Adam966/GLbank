package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Database.Database;

import java.io.IOException;
import java.util.List;

public class MainPage {
    private Employee signedIn;

    public Button logOutbtn;
    public Text loggedAs;

    public ComboBox list;

    public Text name;
    public Text surname;
    public Text email;

    public Text accNum;
    public Text money;

    Database database = Database.getInstance();

    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 802 , 605);
            Stage stage = new Stage();
            stage.setTitle("GL bank");
            stage.setScene(scene);
            stage.show();

            Stage logout = (Stage) logOutbtn.getScene().getWindow();
            logout.close();
        } catch (IOException e) { }
    }

    public void whoIsLogged() {
        loggedAs.setText(signedIn.getfName() + " " + signedIn.getlName());
    }

    public void setEmployee(Employee signedIn) {
        this.signedIn = signedIn;
    }

    public void listAllClients() {
        List<Client> clients;
        clients = database.getAllClients();

        ObservableList<String> clientBox = FXCollections.observableArrayList();

        for (int i = 0; i < clients.size(); i++) {
            clientBox.add(clients.get(i).getfName() + " " + clients.get(i).getlName());
        }

        list.setItems(clientBox);
    }

    public void newClient() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("windows/newClientForm.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 520 , 225);
            Stage stage = new Stage();

            stage.setTitle("GL bank");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {}
    }

    public void showClient() {
        List<Client> clients = database.getAllClients();
        name.setText(clients.get(list.getItems().indexOf(list.getValue())).getfName());
        surname.setText(clients.get(list.getItems().indexOf(list.getValue())).getlName());
        email.setText(clients.get(list.getItems().indexOf(list.getValue())).getEmail());
    }

    public void createNewAcc(MouseEvent mouseEvent) {
        //accNum.setText(String.valueOf((long)(Math.random()*100000)));
    }

    public void createCard(MouseEvent mouseEvent) {
    }
}

