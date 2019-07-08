package com.computerSystem.forms.accounts;

import com.computerSystem.forms.customControls.*;
import com.computerSystem.Main;
import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Address;
import com.computerSystem.models.classes.Order;
import com.computerSystem.models.classes.Product;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Date;

public class Account {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    //Login Form
    @FXML private TextField             loginTxtField_Username;
    @FXML private ProgressIndicator     loginTxtField_Username_Icon;
    @FXML private PasswordField         loginPssField_Pass;
    @FXML private ProgressIndicator     loginPssField_Pass_Icon;

    //Create Form
    @FXML private TextField             createTxtField_Username;
    @FXML private ProgressIndicator     createTxtField_Username_Icon;
    @FXML private TextField             createTxtField_Email;
    @FXML private ProgressIndicator     createTxtField_Email_Icon;
    @FXML private TextField             createTxtField_Phone;
    @FXML private ProgressIndicator     createTxtField_Phone_Icon;
    @FXML private ChoiceBox             createChcBx_Title;
    @FXML private ProgressIndicator     createChcBx_Sex_Icon;
    @FXML private TextField             createTxtField_Forename;
    @FXML private ProgressIndicator     createTxtField_Forename_Icon;
    @FXML private TextField             createTxtField_Surname;
    @FXML private ProgressIndicator     createTxtField_Surname_Icon;
    @FXML private PasswordField         createPssField_Pass;
    @FXML private ProgressIndicator     createPssField_Pass_Icon;

    //Account Form
    @FXML private AnchorPane masterAnchorPane;
    //  Tab - Account
    @FXML private ImageView accountImageView_tabAccount_userIcon;
    @FXML private Button accountButton_tabAccount_userIcon;

    @FXML private TextField accountTextField_tabAccount_username;
    @FXML private ProgressIndicator accountTextField_tabAccount_username_Icon;

    @FXML private TextField accountTextField_tabAccount_email;
    @FXML private ProgressIndicator accountTextField_tabAccount_email_Icon;

    @FXML private TextField accountTextField_tabAccount_phoneNumber;
    @FXML private ProgressIndicator accountTextField_tabAccount_phoneNumber_Icon;

    @FXML private CheckBox accountCheckBox_tabAccount_canContact;
    //  Tab - Client
    @FXML private ChoiceBox accountChoiceBox_tabAccount_title;


    @FXML private TextField accountTextField_tabAccount_forename;
    @FXML private ProgressIndicator accountTextField_tabAccount_forename_Icon;

    @FXML private TextField accountTextField_tabAccount_surname;
    @FXML private ProgressIndicator accountTextField_tabAccount_surname_Icon;
    //    //  Tab - Address
    @FXML private VBox                  accountAddressContainer;
    //  Tab - Card
    @FXML private TextField accountTextField_tabAccount_CardNumber;
    @FXML private ProgressIndicator accountTextField_tabAccount_CardNumber_Icon;

    @FXML private TextField accountTextField_tabAccount_CardMonth;
    @FXML private TextField accountTextField_tabAccount_CardYear;

    @FXML private ProgressIndicator accountTextField_tabAccount_CardMonthYear_Icon;

    //  Tab - Orders
    @FXML private VBox                  accountOrdersContainer;

    int AddressIDIncrementer = 0;

    @FXML protected void initialize() {
        System.out.println("Accounts Initialized");
    }

    @FXML private void handleButtonClick(Event ev) {
        Button _handledObect = (Button)ev.getSource();
        switch(_handledObect.getId()){
            case "loginBtn_Create":
                masterController.loadPage("accounts/Create.fxml");
                break;
            case "loginBtn_Login":
                if(passedLoginValidation()) {
                    if(DatabaseInteraction.StoredProcedures.Scalar.isLoginCredentialsCorrect(loginTxtField_Username.getText(), loginPssField_Pass.getText())) {
                        System.out.println("Authenticated!");
                        Main.localUser.userAccount.setUsername(loginTxtField_Username.getText());
                        Main.localUser.userAccount.setAuthenticated(true);
                        Main.localUser.populateDetails();
                        masterController.loadPage("Home.fxml");
                    } else {
                        System.out.println("Credentials incorrect!");
                    }
                } else {
                    System.out.println("Fields have not been validated!");
                }
                break;
            case "loginBtn_Forgot":
                masterController.loadPage("accounts/Forgot.fxml");
                break;
            case "createBtn_Login":
                masterController.loadPage("accounts/Login.fxml");
                break;
            case "createBtn_Create":
                //POST TO DATABASE
                if(passedCreateValidation()) {
                    System.out.println("All Fields Validated on Format");
                    //CHECK DB FOR DUPLICATES
                    boolean isDupe = DatabaseInteraction.StoredProcedures.Scalar.isUsernameDuplicate(createTxtField_Username.getText());
                    if(!isDupe) {
                        System.out.println("All Fields Validated on Database");
                        boolean isSuccess = DatabaseInteraction.StoredProcedures.NonQuery.isPostNewUser(
                               createTxtField_Username.getText(),
                               createPssField_Pass.getText(),
                               0,
                               createTxtField_Email.getText(),
                               createTxtField_Phone.getText(),
                               0,
                                (String)createChcBx_Title.getValue(),
                                createTxtField_Forename.getText(),
                                createTxtField_Surname.getText()
                        );
                        if(isSuccess) {
                            System.out.println("Posted to Database");
                            masterController.loadPage("Home.fxml");
                            System.out.println("Loaded Home");
                        } else {
                            System.out.println("Failure creating account!");
                        }
                    }
                    else {
                        System.out.println("Is a duplicate!");
                    }

                } else {
                    System.out.println("One or more fields incorrect!");
                }
                break;
            case "accountBtn_SaveAccount":
                Main.localUser.userAccount.setUsername(accountTextField_tabAccount_username.getText());
                Main.localUser.userAccount.setEmail(accountTextField_tabAccount_email.getText());
                Main.localUser.userAccount.setPhone(accountTextField_tabAccount_phoneNumber.getText());
                Main.localUser.userAccount.setCanContact(accountCheckBox_tabAccount_canContact.isSelected());
                Main.localUser.updateDatabase_UserAccount();
                break;
            case "accountBtn_SaveClient":
                Main.localUser.userClient.setTitle((String)accountChoiceBox_tabAccount_title.getSelectionModel().getSelectedItem());
                Main.localUser.userClient.setForename(accountTextField_tabAccount_forename.getText());
                Main.localUser.userClient.setSurname(accountTextField_tabAccount_surname.getText());
                Main.localUser.updateDatabase_UserClient();
                break;
            case "accountBtn_SaveCard":
                Main.localUser.userCards.setCardNumber(accountTextField_tabAccount_CardNumber.getText());
                Main.localUser.userCards.setExpiryYear(Integer.parseInt(accountTextField_tabAccount_CardYear.getText()));
                Main.localUser.userCards.setExpiryMonth(Integer.parseInt(accountTextField_tabAccount_CardMonth.getText()));
                Main.localUser.updateDatabase_UserCard();
                break;
        }
    }

    @FXML private void handleChangeIconClick(Event ev) {
        try{
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
            jfc.showDialog(null, "Select Profile Picture");
            jfc.setVisible(true);

            File file = jfc.getSelectedFile();
            System.out.println(file.toURI().toString());
            Image img = new Image(file.toURI().toString());

            accountImageView_tabAccount_userIcon.setImage(img);
            Main.localUser.userAccount.setUserImage(img);
            Main.localUser.userAccount.setUserImageFile(file);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML private void handleTitlePaneClick(Event ev) {
        TitledPane sentPane = (TitledPane)ev.getSource();
        try {
            switch(sentPane.getText()) {
                case "Account":
                    if(sentPane.isExpanded() == true) {
                        //Dynamically update data here
                        populateTab_Account();
                    }
                    break;
                case "Client":
                    if(sentPane.isExpanded() == true) {
                        //Dynamically update data here
                        populateTab_Client();
                    }
                    break;
                case "Address":
                    if(sentPane.isExpanded() == true) {
                        if(accountAddressContainer.getChildren().toArray().length == 0) {
                            //Add an entry for each address
                            for (com.computerSystem.models.classes.Address addr : Main.localUser.userAddresses) {
                                addNewAddressItem(addr);
                            }
                            addNewAddressOptions();
                        }
                    } else {
                        accountAddressContainer.getChildren().clear();
                    }
                    break;
                case "Cards":
                    if(sentPane.isExpanded() == true) {
                        //Dynamically update data here
                        populateTab_Cards();
                    }
                    break;
                case "Orders":
                    if(sentPane.isExpanded() == true) {
                        if(accountOrdersContainer.getChildren().toArray().length == 0) {
                            populateTab_Orders();
                        }
                    } else {
                        accountOrdersContainer.getChildren().clear();
                    }
                    break;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML private void handleTextChanged(Event ev) {
        if(ev.getSource() instanceof TextField){
            TextField _handledObject = (TextField)ev.getSource();
            switch (_handledObject.getId()){
                //Login Form
                case "loginTxtField_Username":
                    String verifiedUsername = Main.localUser.userAccount.verifyUsernameFormat(_handledObject.getText());
                    if(verifiedUsername.length() > 0) {
                        setVerificationProgress(loginTxtField_Username_Icon, true, verifiedUsername);
                    } else {
                        setVerificationProgress(loginTxtField_Username_Icon, false, verifiedUsername);
                    }
                    break;
                case "loginPssField_Pass":
                    String verifiedPassword = Main.localUser.userAccount.verifyPasswordFormat(_handledObject.getText());
                    if(verifiedPassword.length() > 0) {
                        setVerificationProgress(loginPssField_Pass_Icon, true, verifiedPassword);
                    } else {
                        setVerificationProgress(loginPssField_Pass_Icon, false, verifiedPassword);
                    }
                    break;
                //Create Form
                case "createTxtField_Username":
                    String verifiedUsername2 = Main.localUser.userAccount.verifyUsernameFormat(_handledObject.getText());
                    if(verifiedUsername2.length() > 0) {
                        setVerificationProgress(createTxtField_Username_Icon, true, verifiedUsername2);
                    } else {
                        setVerificationProgress(createTxtField_Username_Icon, false, verifiedUsername2);
                    }
                    break;
                case "createTxtField_Email":
                    String verifiedEmail = Main.localUser.userAccount.verifyEmailFormat(_handledObject.getText());
                    if(verifiedEmail.length() > 0) {
                        setVerificationProgress(createTxtField_Email_Icon, true, verifiedEmail);
                    } else {
                        setVerificationProgress(createTxtField_Email_Icon, false, verifiedEmail);
                    }
                    break;
                case "createTxtField_Phone":
                    String verifiedPhone = Main.localUser.userAccount.verifyPhoneFormat(_handledObject.getText());
                    if(verifiedPhone.length() > 0) {
                        setVerificationProgress(createTxtField_Phone_Icon, true, verifiedPhone);
                    } else {
                        setVerificationProgress(createTxtField_Phone_Icon, false, verifiedPhone);
                    }
                    break;
                case "createTxtField_Forename":
                    String verifiedForename = Main.localUser.userClient.verifyForenameFormat(_handledObject.getText());
                    if(verifiedForename.length() > 0) {
                        setVerificationProgress(createTxtField_Forename_Icon, true, verifiedForename);
                    } else {
                        setVerificationProgress(createTxtField_Forename_Icon, false, verifiedForename);
                    }
                    break;
                case "createTxtField_Surname":
                    String verifiedSurname = Main.localUser.userClient.verifySurnameFormat(_handledObject.getText());
                    if(verifiedSurname.length() > 0) {
                        setVerificationProgress(createTxtField_Surname_Icon, true, verifiedSurname);
                    } else {
                        setVerificationProgress(createTxtField_Surname_Icon, false, verifiedSurname);
                    }
                    break;
                case "createPssField_Pass":
                    String verifiedPassword2 = Main.localUser.userAccount.verifyPasswordFormat(_handledObject.getText());
                    if(verifiedPassword2.length() > 0) {
                        setVerificationProgress(createPssField_Pass_Icon, true, verifiedPassword2);
                    } else {
                        setVerificationProgress(createPssField_Pass_Icon, false, verifiedPassword2);
                    }
                    break;
            }
        }
    }

    @FXML private void handleOnAction(ActionEvent ev) {
        setVerificationProgress(createChcBx_Sex_Icon, false, "");
    }

    private boolean passedLoginValidation(){
        if(loginTxtField_Username_Icon.getOpacity() == 0 && loginPssField_Pass_Icon.getOpacity() == 0) { return true; } else { return false; }
    }

    private boolean passedCreateValidation(){
        if(
                createTxtField_Username_Icon.getOpacity() == 0 &&
                        createTxtField_Email_Icon.getOpacity() == 0 &&
                        createTxtField_Phone_Icon.getOpacity() == 0 &&
                        createChcBx_Sex_Icon.getOpacity() == 0 &&
                        createTxtField_Forename_Icon.getOpacity() == 0 &&
                        createTxtField_Surname_Icon.getOpacity() == 0 &&
                        createPssField_Pass_Icon.getOpacity() == 0
        ) { return true; } else { return false; }
    }

    private void setVerificationProgress(ProgressIndicator _control, Boolean _hasErrors, String _errMsg) {
        if(_hasErrors) {
            _control.setStyle("-fx-progress-color: #ff0000;");
            _control.setTooltip(new Tooltip(_errMsg));
            _control.setOpacity(1);
        } else {
            _control.setStyle("-fx-progress-color: lightblue;");
            _control.setTooltip(null);
            _control.setOpacity(0);
        }
    }

    private void populateTab_Orders() {
        accountOrdersContainer.getChildren().clear();
        for (Order order:Main.localUser.userOrders) {
            VBox newBox = new VBox();
            accountOrdersContainer.setVgrow(newBox, Priority.ALWAYS);
            newBox.getStyleClass().add("VBox_Accounts_OrderItem");
            newBox.setSpacing(2);

            newBox.getChildren().add(new Label("Order ID: " + order.getOrderID()));
            newBox.getChildren().add(new Label("Order Price: " + order.getOrderTotalPrice()));
            newBox.getChildren().add(new Label("Order Quantity: " + order.getOrderQuantity()));
            for(Product prod:Main.localShop.localProductsList) {
                if(prod.getProductID() == order.getProductID()){
                    newBox.getChildren().add(new Label("Product Name: " + prod.getProductName()));
                    newBox.getChildren().add(new Label("Product Category: " + prod.getProductCategory()));
                }
            }
            accountOrdersContainer.getChildren().add(newBox);
        }
    }

    private void populateTab_Account(){
        accountTextField_tabAccount_username.setText(Main.localUser.userAccount.getUsername());
        accountTextField_tabAccount_email.setText(Main.localUser.userAccount.getEmail());
        accountTextField_tabAccount_phoneNumber.setText(Main.localUser.userAccount.getPhone());
        accountCheckBox_tabAccount_canContact.setSelected(Main.localUser.userAccount.getCanContact());
        accountImageView_tabAccount_userIcon.setImage(Main.localUser.userAccount.getUserImage());
    }

    private void populateTab_Client(){
        accountChoiceBox_tabAccount_title.getSelectionModel().select(Main.localUser.userClient.getTitle());
        accountTextField_tabAccount_forename.setText(Main.localUser.userClient.getForename());
        accountTextField_tabAccount_surname.setText(Main.localUser.userClient.getSurname());
    }

    private void populateTab_Cards(){
        accountTextField_tabAccount_CardNumber.setText( Main.localUser.userCards.getCardNumber());
        accountTextField_tabAccount_CardYear.setText( Main.localUser.userCards.getExpiryYear().toString());
        accountTextField_tabAccount_CardMonth.setText( Main.localUser.userCards.getExpiryMonth().toString());
    }

    private void addNewAddressItem (com.computerSystem.models.classes.Address addr) throws  Exception {
        AddressIDIncrementer++;
        FXMLLoader instantiateAddressItem = masterController.getCustomControl("customControls/AddressItem.fxml");
        VBox loadAddressItem = instantiateAddressItem.load();
        if(addr != null) {
            loadAddressItem.setId(Main.localUser.userAccount.getUserID() + "-" + addr.getAddressID());
        } else {
            loadAddressItem.setId(Main.localUser.userAccount.getUserID() + "-" + new Date().toString());
        }
        AddressItem controlAddressItem = instantiateAddressItem.getController();
        controlAddressItem.implimentDefaultData(addr, AddressIDIncrementer);
        accountAddressContainer.getChildren().add(loadAddressItem);
    }

    private void addNewAddressOptions () {
        VBox returnBox = new VBox(); returnBox.getStyleClass().add("vboxAddressContainer"); returnBox.setVgrow(accountAddressContainer, Priority.ALWAYS);
        HBox hboxOptions = new HBox(); hboxOptions.getStyleClass().add("stanrdardHBox"); hboxOptions.setStyle("-fx-alignment: CENTER;");
        Button btnNewAddr = new Button("Add New Addresss");
        btnNewAddr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                returnBox.getChildren().remove(hboxOptions);
                try { addNewAddressItem(null); } catch (Exception ex) {System.out.println(ex.getMessage());}
                addNewAddressOptions();
            }
        });
        hboxOptions.getChildren().add(btnNewAddr);

        Button btnSaveChanges = new Button("Save Changes");
        btnSaveChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO - save here
                AddressIDIncrementer = 0;
                updateAddressData();
            }
        });
        hboxOptions.getChildren().add(btnSaveChanges);

        returnBox.getChildren().add(hboxOptions);
        accountAddressContainer.getChildren().add(returnBox);
    }

    private void updateAddressData() {
        int multipleBillingAddress = 0;
        for (Node node: accountAddressContainer.getChildren()) {
            if(node instanceof VBox) {
                VBox nodeBox = (VBox)node;
                if(nodeBox.getId() != null){
                    if(nodeBox.getId().startsWith(Integer.toString(Main.localUser.userAccount.getUserID()))){
                        Object userData = node.getUserData();
                        if(userData instanceof AddressItem) {
                            AddressItem itm = (AddressItem)userData;
                            if(itm.getBillingAddress()) {multipleBillingAddress++;}
                        }
                    }
                }
            }
        }
        if(multipleBillingAddress > 1) {
            Alert al = new Alert(Alert.AlertType.INFORMATION, "Two or more addresses with Billing Address Flag Selected\nYou are only allowed one billing address!", ButtonType.OK);
            al.showAndWait();
        } else {
            for (Node node: accountAddressContainer.getChildren()) {
                if(node instanceof VBox) {
                    VBox nodeBox = (VBox)node;
                    if(nodeBox.getId() != null){
                        if(nodeBox.getId().startsWith(Integer.toString(Main.localUser.userAccount.getUserID()))){
                            Object userData = node.getUserData();
                            if(userData instanceof AddressItem) {
                                AddressItem itm = (AddressItem)userData;
                                System.out.println("Located Address Interface: " + itm.getAddressID());
                                updatelocalUserAddress(itm);
                            }else {
                                System.out.println("data not found...");
                            }
                        }
                    }
                }
            }
            Main.localUser.updateDatabase_userAddresses();
        }
    }

    private void updatelocalUserAddress(AddressItem controller) {
        boolean AddressLocated = false;

        //If the address does exist, amend it
        for (Address addr:Main.localUser.userAddresses) {
            if(addr.getAddressID() == controller.getAddressID()){
                AddressLocated = true;
                addr.setPostcode(controller.getPostcode());
                addr.setCountry(controller.getCountry());
                addr.setTownCityRegion(controller.getTownCityRegion());
                addr.setHouseName(controller.getHouseName());
                addr.setBillingAddress(controller.getBillingAddress());
            }
        }

        //If the address does not yet exist, create new entry
        if(!AddressLocated) {
            Address newAddress = new Address();
            newAddress.setAddressID(controller.getAddressID());
            newAddress.setPostcode(controller.getPostcode());
            newAddress.setCountry(controller.getCountry());
            newAddress.setTownCityRegion(controller.getTownCityRegion());
            newAddress.setHouseName(controller.getHouseName());
            newAddress.setBillingAddress(controller.getBillingAddress());
            Main.localUser.userAddresses.add(newAddress);
        }
    }


}
