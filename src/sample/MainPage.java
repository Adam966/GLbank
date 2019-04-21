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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainPage {
    private Employee signedIn;

    public Button logOutbtn;
    public Text loggedAs;
    public Text date;

    public ComboBox list;

    public Text name;
    public Text surname;
    public Text email;

    public Text money;
    public ComboBox accountList;

    public ComboBox cardList;
    public Text cardPin;
    public Text status;
    public Text expireDate;

    public Text login;
    public Text password;
    public Text lastLogin;
    public Text IBstatus;
    public Button resetBtn;

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

    public void setDate() {
        LocalDate today = LocalDate.now();
        date.setText(today.getDayOfMonth() + "/" + today.getMonth() + "/" + today.getYear());
    }

    public void listAllClients() {
        List<Client> clients;
        clients = database.getAllClients();

        ObservableList<String> clientBox = FXCollections.observableArrayList();

        for ( Client cl : clients) {
            clientBox.add(cl.getfName() + " " + cl.getlName());
        }
        list.setItems(clientBox);
        System.out.println("Size: " + list.getItems().size());
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
        List<Client> clients = database.getAllClients();
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        System.out.println(list.getItems().size());
        database.inserNewAccount(clients.get(list.getItems().indexOf(list.getValue())).getID(), String.valueOf(number), 0);
    }

    public void createCard(MouseEvent mouseEvent) {
        long cardNum = (long) Math.floor(Math.random() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;

        Random random = new Random();
        int pin = random.nextInt(10000);
        System.out.println(list.getItems().size());
        List<Account> accounts = database.selectAccount(list.getItems().indexOf(list.getValue()) + 1);


        for (Account acc: accounts) {
            if (acc.getAccNum().equals(accountList.getValue())) {
                LocalDate today = LocalDate.now();
                database.insertNewCard(String.valueOf(pin), 1, today.getYear() + 3, today.getMonthValue(), acc.getID(), String.valueOf(cardNum));
            }
        }
    }

    public void chooseAccount(MouseEvent mouseEvent) throws SQLException {
        List<Client> clients = database.getAllClients();
        List<Account> accounts = database.selectAccount(clients.get(list.getItems().indexOf(list.getValue())).getID());

        ObservableList<String> accountBox = FXCollections.observableArrayList();

        for ( Account acc : accounts) {
            accountBox.add(acc.getAccNum());
        }

        accountList.setItems(accountBox);

        for (Account acc : accounts) {
            if (acc.getAccNum().equals(accountList.getValue())) {
                money.setText(String.format("%.02f" , acc.getMoney()));
            }
            else {
                money.setText("0.00");
            }
        }
    }

    public void showCards() {
        List<Card> cards = database.selectAllCards(String.valueOf(accountList.getValue()));

        ObservableList<String> cardsBox = FXCollections.observableArrayList();

        for ( Card card : cards) {
            cardsBox.add(card.getCardNum());
        }

        cardList.setItems(cardsBox);

        cardPin.setText(cards.get(cardList.getItems().indexOf(cardList.getValue())).getPIN());
        if (cards.get(cardList.getItems().indexOf(cardList.getValue())).isActive()) {
            status.setText("aktívna");
        }
        else {
            status.setText("neaktívna");
        }
        expireDate.setText(cards.get(cardList.getItems().indexOf(cardList.getValue())).getExpireM() + "/" + cards.get(cardList.getItems().indexOf(cardList.getValue())).getExpireY());
    }

    public void resetClientAccount(MouseEvent mouseEvent) {

    }

    public void showClientLogin() {
        ClientLogin cl = database.selectClientLogin(list.getItems().indexOf(list.getValue()) + 1);

        login.setText(cl.getLogin());
        password.setText(cl.getPassword());

        Date date = new Date(cl.getLastLogin().getTime());
        lastLogin.setText(String.valueOf(date));

        if (cl.getStatus()) {
            IBstatus.setText("aktívný");
        }
        else {
            IBstatus.setText("zablokovany");
        }
    }
}

