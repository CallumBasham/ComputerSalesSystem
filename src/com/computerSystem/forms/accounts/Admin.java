//THIS SECTION ONLY DEALS WITH THE USER "ACCOUNT" CLASS FOR BETTER DATA PROTECTION!
package com.computerSystem.forms.accounts;

import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Account;
import com.computerSystem.models.classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Admin {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    private List<com.computerSystem.models.classes.Account> userAccounts = new ArrayList<com.computerSystem.models.classes.Account>();
    private ObservableList<com.computerSystem.models.classes.Account> userAccountsObservable = FXCollections.observableArrayList();

    @FXML private AnchorPane anchorPane_Admin_Master;
    @FXML private TableView tableView_Admin_UserTable;

    @FXML public void initialize() {
        System.out.println("Admin Accounts Initialized");
        //Populate the data set
        userAccounts = DatabaseInteraction.StoredProcedures.Tabular.getAllAccounts();
        populateColumnHeaders();
        populateUserAccountsObservable();
    }

    private void populateColumnHeaders() {
        TableColumn columnUserID = new TableColumn("User ID");//Column Header
        columnUserID.setCellValueFactory(new PropertyValueFactory<com.computerSystem.models.classes.Account, Integer>("UserID")); //Column Value

        TableColumn columnUsername = new TableColumn("Username");//Column Header
        columnUsername.setCellValueFactory(new PropertyValueFactory<com.computerSystem.models.classes.Account, Integer>("Username")); //Column Value

        TableColumn columnAccountType = new TableColumn("Account Type");//Column Header
        columnAccountType.setCellValueFactory(new PropertyValueFactory<com.computerSystem.models.classes.Account, Integer>("AccountType")); //Column Value

        TableColumn columnEmail = new TableColumn("Email");//Column Header
        columnEmail.setCellValueFactory(new PropertyValueFactory<com.computerSystem.models.classes.Account, Integer>("Email")); //Column Value

        TableColumn columnPhone = new TableColumn("Phone Number");//Column Header
        columnPhone.setCellValueFactory(new PropertyValueFactory<com.computerSystem.models.classes.Account, Integer>("Phone")); //Column Value

        TableColumn columnCanContact = new TableColumn("Can Contact");//Column Header
        columnCanContact.setCellValueFactory(new PropertyValueFactory<com.computerSystem.models.classes.Account, Integer>("CanContact")); //Column Value

        tableView_Admin_UserTable.getColumns().addAll(columnUserID, columnUsername, columnAccountType, columnEmail, columnPhone, columnCanContact);
    }

    @FXML private void handleAddUser(Event ev) {

    }

    private void populateUserAccountsObservable() {
        userAccountsObservable.clear();
        userAccountsObservable.addAll(userAccounts);
        tableView_Admin_UserTable.setItems(userAccountsObservable);
        tableView_Admin_UserTable.refresh();
    }
}
