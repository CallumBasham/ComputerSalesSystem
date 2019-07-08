//THIS SECTION ONLY DEALS WITH THE USER "ACCOUNT" CLASS FOR BETTER DATA PROTECTION!
package com.computerSystem.forms.accounts;

import com.computerSystem.Main;
import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Account;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

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
        columnUserID.setCellValueFactory(new PropertyValueFactory<Account, Integer>("UserID")); //Column Value

        TableColumn columnUsername = new TableColumn("Username");//Column Header
        columnUsername.setCellValueFactory(new PropertyValueFactory<Account, Integer>("Username")); //Column Value

        TableColumn columnAccountType = new TableColumn("Account Type");//Column Header
        columnAccountType.setCellValueFactory(new PropertyValueFactory<Account, Boolean>("AccountType")); //Column Value

        TableColumn columnEmail = new TableColumn("Email");//Column Header
        columnEmail.setCellValueFactory(new PropertyValueFactory<Account, Integer>("Email")); //Column Value

        TableColumn columnPhone = new TableColumn("Phone Number");//Column Header
        columnPhone.setCellValueFactory(new PropertyValueFactory<Account, Integer>("Phone")); //Column Value

        TableColumn columnCanContact = new TableColumn("Can Contact");//Column Header
        columnCanContact.setCellValueFactory(new PropertyValueFactory<Account, Integer>("CanContact")); //Column Value

        TableColumn columnChangeAccountType = new TableColumn("Change Account Type");//Column Header
        columnChangeAccountType.setCellFactory(new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
            public TableCell call(final TableColumn<Account, String> param) {
                final TableCell<Account, String> cell = new TableCell<Account, String>() {

                    //Button btn = new Button("Delete Product");
                    HBox parentHBox = new HBox();

                    public void updateItem(String item, boolean empty) {

                        parentHBox.getChildren().clear();

                        Button editProduct = new Button("Change Type");
                        parentHBox.getChildren().add(editProduct);

                        if (!empty) {
                            editProduct.setOnAction(event -> {
                                Account currentAccount = getTableView().getItems().get(getIndex());
                                System.out.println(currentAccount.getUsername()+ "   " + currentAccount.getAccountType());
                                currentAccount.setAccountType(!currentAccount.getAccountType());
                                if(!DatabaseInteraction.StoredProcedures.NonQuery.updateUserAccountType(currentAccount)){
                                    Alert al = new Alert(Alert.AlertType.CONFIRMATION, "You may not remove the primary admin account permissions, changes reverted!'", ButtonType.OK);
                                    al.show();
                                    currentAccount.setAccountType(!currentAccount.getAccountType());
                                }
                                populateUserAccountsObservable();

                            });
                            setGraphic(parentHBox);
                        }
                    }
                };
                return cell;
            }
        });

        TableColumn columnResetPassword = new TableColumn("Reset Password");//Column Header
        columnResetPassword.setCellFactory(new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
            public TableCell call(final TableColumn<Account, String> param) {
                final TableCell<Account, String> cell = new TableCell<Account, String>() {

                    //Button btn = new Button("Delete Product");
                    HBox parentHBox = new HBox();

                    public void updateItem(String item, boolean empty) {

                        parentHBox.getChildren().clear();

                        Button editProduct = new Button("Reset Password");
                        parentHBox.getChildren().add(editProduct);

                        if (!empty) {
                            editProduct.setOnAction(event -> {
                                Account currentAccount = getTableView().getItems().get(getIndex());
                                System.out.println(currentAccount.getUsername()+ "   " + currentAccount.getAccountType());
                                DatabaseInteraction.StoredProcedures.NonQuery.updateResetUserAccountPassword(currentAccount);
                                DatabaseInteraction.StoredProcedures.NonQuery.updateResetUserAccountPassword(currentAccount);
                                Alert al = new Alert(Alert.AlertType.CONFIRMATION, "User ( " + currentAccount.getUserID() + " ): " + currentAccount.getUsername() + " password has been reset to 'Password'", ButtonType.OK);
                                al.show();
                            });
                            setGraphic(parentHBox);
                        }
                    }
                };
                return cell;
            }
        });

        tableView_Admin_UserTable.getColumns().addAll(columnUserID, columnUsername, columnAccountType, columnEmail, columnPhone, columnCanContact, columnChangeAccountType, columnResetPassword);
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
