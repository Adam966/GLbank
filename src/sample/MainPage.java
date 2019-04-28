package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Database.Database;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainPage {
    private Employee signedIn;

    private List<Account> accounts;
    private Account account;

    private List<Card> cards;
    private Card card;

    private Client client;
    private List<Client> clients;

    private Transaction transaction;
    private List<Transaction> transactions;

    private CardTransaction cardTransaction;
    private List<CardTransaction> cardTransactions;

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
    public Text newPassword;

    public TextField moneyIns;
    public TextField moneyWidth;
    public Text lesserMoney;

    public Text transAmount;
    public Text transDate;
    public ComboBox listMonth;
    public ComboBox listTransaction;
    public ComboBox listCardTransaction;
    public Text cardAmount;

    Database database = Database.getInstance();

    /////////////////////////////////////////////////// SECONDARY FUNCTIONS ////////////////////////////////////////////
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

    ////////////////////////////////////////////////// CLIENT FUNCTIONS ////////////////////////////////////////////////
    public void listAllClients() {
        clients = database.getAllClients();

        ObservableList<String> clientBox = FXCollections.observableArrayList();

        for ( Client cl : clients) {
            clientBox.add(cl.getfName() + " " + cl.getlName());
        }
        list.setItems(clientBox);
    }

    public Client selectedClient() {
        client = clients.get(list.getItems().indexOf(list.getValue()));
        return client;
    }

    public void createNewClient() {
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
        name.setText(selectedClient().getfName());
        surname.setText(selectedClient().getlName());
        email.setText(selectedClient().getEmail());
    }

    //////////////////////////////////////////// ACCOUNT FUNCTIONS /////////////////////////////////////////////////////
    public void createNewAcc(MouseEvent mouseEvent) {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        database.inserNewAccount(selectedAccount().getID(), String.valueOf(number), 0);
    }

    public void listAllAccounts(MouseEvent mouseEvent) {
        accounts = database.selectAccount(selectedClient().getID());

        ObservableList<String> accountBox = FXCollections.observableArrayList();

        for ( Account acc : accounts) {
            accountBox.add(acc.getAccNum());
        }

        accountList.setItems(accountBox);
    }

    public void showAccount() {
        if (selectedAccount().getMoney() > 0)
            money.setText(String.format("%.02f" , selectedAccount().getMoney()));
        else
            money.setText("0.00");
    }

    public Account selectedAccount() {
        account = accounts.get(accountList.getItems().indexOf(accountList.getValue()));
        return account;
    }

    public void insertMoney(ActionEvent actionEvent) {
        database.insertMoney(Float.valueOf(moneyIns.getText()) + selectedAccount().getMoney(), selectedAccount().getID());
        moneyIns.clear();
    }

    public void moneyWithdraw(ActionEvent actionEvent) {
        if (selectedAccount().getMoney() < Float.valueOf(moneyWidth.getText()))
            lesserMoney.setText("On account is not  enough money.");
        else {
            database.insertMoney(selectedAccount().getMoney() - Float.valueOf(moneyWidth.getText()), selectedAccount().getID());
            moneyWidth.clear();
        }
    }

    //////////////////////////////////////////////////// CARD FUNCTIONS ////////////////////////////////////////////////
    public void listAllCards() {
        cards = database.selectAllCards(String.valueOf(accountList.getValue()));

        ObservableList<String> cardsBox = FXCollections.observableArrayList();

        for ( Card card : cards) {
            cardsBox.add(card.getCardNum());
        }

        cardList.setItems(cardsBox);
    }

    public Card selectedCard() {
        card = cards.get(cardList.getItems().indexOf(cardList.getValue()));
        return card;
    }

    public void showCard() {
        cardPin.setText(selectedCard().getPIN());
        status.setText(String.valueOf(selectedCard().isActive()));
        expireDate.setText(selectedCard().getExpireM() + "/" + selectedCard().getExpireY());
    }


    public void createCard(MouseEvent mouseEvent) {
        long cardNum = (long) Math.floor(Math.random() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;

        Random random = new Random();
        int pin = random.nextInt(10001);

        LocalDate today = LocalDate.now();

        database.insertNewCard(String.valueOf(pin), 1, today.getYear() + 3, today.getMonthValue(), selectedAccount().getID(), String.valueOf(cardNum));
    }

    //////////////////////////////////////////////////// IB FUNCTIONS //////////////////////////////////////////////////
    public void showClientLogin() {
        ClientLogin cl = database.selectClientLogin(selectedClient().getID());

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

    public void resetClientAccount(MouseEvent mouseEvent) {
        ClientLogin cl = database.selectClientLogin(selectedClient().getID());

        if (!cl.getStatus()) {
            String chars = "ab7c0de2fgh1ijklm4n3opq5rst6uv8wxyz9";
            String password = "";

            for (int i = 0; i < 8; i++) {
                if (i % 2 == 0)
                    password+= Character.toString(chars.charAt((int)(Math.floor(Math.random() * 36))));
                else
                    password+= Character.toString(chars.charAt((int)(Math.floor(Math.random() * 36)))).toUpperCase();
            }
            database.insertNewAccountPassword(selectedClient().getID(), password);
            newPassword.setText("Account was reseted and new password is " + password);
        }
        else
            newPassword.setText("Account is active");
    }

    ////////////////////////////////////////// TRANSACTION FUNCTIONS ///////////////////////////////////////////////////
    public void showTransaction(ActionEvent actionEvent) {
        transAmount.setText(String.valueOf(selectedTransaction().getTransAmount()));

        Date date = new Date(selectedTransaction().getTransDate().getTime());
        transDate.setText(String.valueOf(date));
    }

    public Transaction selectedTransaction() {
        transaction = transactions.get(listTransaction.getItems().indexOf(listTransaction.getValue()));
        return transaction;
    }

    public void listAllTransaction(MouseEvent mouseEvent) {
        transactions = database.selectAllTransaction(selectedAccount().getID());

        ObservableList<String> transactionBox = FXCollections.observableArrayList();

        for ( Transaction tr : transactions) {
            transactionBox.add(tr.getReAccount());
        }

        listTransaction.setItems(transactionBox);
    }

    public void listAllCardTransaction(MouseEvent mouseEvent) {
        cardTransactions = database.selectAllCardTransaction(selectedCard().getID());

        ObservableList<String> transactionCardBox = FXCollections.observableArrayList();

        for ( CardTransaction ctr : cardTransactions) {
            Date date = new Date(selectedCardTransaction().getTransCardDate().getTime());
            transactionCardBox.add(String.valueOf(date));
        }

        listCardTransaction.setItems(transactionCardBox);
    }

    public CardTransaction selectedCardTransaction() {
        cardTransaction = cardTransactions.get(listCardTransaction.getItems().indexOf(listCardTransaction.getValue()));
        return cardTransaction;
    }

    public void showCardTransaction(ActionEvent actionEvent) {
        cardAmount.setText(String.valueOf(selectedCardTransaction().getTransCardAmount()));
    }
}

