package computerSystem.forms.accounts;

import computerSystem.forms.Master;
import computerSystem.forms.customControls.*;
import computerSystem.Main;
import computerSystem.database.DatabaseInteraction;
import computerSystem.models.classes.Address;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    computerSystem.forms.Master masterController = new computerSystem.forms.Master();

    @FXML private AnchorPane masterAnchorPane;

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
    @FXML private ChoiceBox             createChcBx_Sex;
    @FXML private ProgressIndicator     createChcBx_Sex_Icon;
    @FXML private TextField             createTxtField_Forename;
    @FXML private ProgressIndicator     createTxtField_Forename_Icon;
    @FXML private TextField             createTxtField_Surname;
    @FXML private ProgressIndicator     createTxtField_Surname_Icon;
    @FXML private PasswordField         createPssField_Pass;
    @FXML private ProgressIndicator     createPssField_Pass_Icon;

    //Account Form
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
    @FXML private ProgressIndicator accountChoiceBox_tabAccount_title_Icon;

    @FXML private TextField accountTextField_tabAccount_forename;
    @FXML private ProgressIndicator accountTextField_tabAccount_forename_Icon;

    @FXML private TextField accountTextField_tabAccount_Surname;
    @FXML private ProgressIndicator accountTextField_tabAccount_Surname_Icon;
    //    //  Tab - Address
    @FXML private VBox                  accountAddressContainer;
    //  Tab - Orders
    @FXML private VBox                  accountOrdersContainer;



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
                                (String)createChcBx_Sex.getValue(),
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
        }
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
                            for (computerSystem.models.classes.Address addr : Main.localUser.userAddresses) {
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
                            accountOrdersContainer.getChildren().add(new Label("Lol memes3"));
                        }
                    } else {
                        accountOrdersContainer.getChildren().clear();
                    }
                    break;
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void populateTab_Account(){
        accountTextField_tabAccount_username.setText(Main.localUser.userAccount.getUsername());
        accountTextField_tabAccount_email.setText(Main.localUser.userAccount.getEmail());
        accountTextField_tabAccount_phoneNumber.setText(Main.localUser.userAccount.getPhone());
        accountCheckBox_tabAccount_canContact.setSelected(Main.localUser.userAccount.getCanContact());
    }

    private void populateTab_Client(){
        accountTextField_tabAccount_forename.setText(Main.localUser.userClient.getForename());
        accountTextField_tabAccount_Surname.setText(Main.localUser.userClient.getSurname());
    }

    private void populateTab_Cards(){

    }


    int AddressIDIncrementer = 0;

    private void addNewAddressItem (computerSystem.models.classes.Address addr) throws  Exception {
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

    @SuppressWarnings("Duplicates")
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



    /*private VBox getAddressControl(boolean isFinal, VBox parentContainer, computerSystem.models.classLocalUser.Address addr) {
        VBox returnBox = new VBox(); returnBox.getStyleClass().add("vboxAddressContainer"); returnBox.setVgrow(parentContainer, Priority.ALWAYS);

        if(isFinal) {
            HBox hBoxPostcode = new HBox(); hBoxPostcode.getStyleClass().add("stanrdardHBox"); hBoxPostcode.setStyle("-fx-alignment: CENTER;");
            Button btnNewAddr = new Button("Add New Addresss");
            btnNewAddr.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    returnBox.getChildren().remove(hBoxPostcode);
                    accountAddressContainer.getChildren().add(accountAddressContainer.getChildren().toArray().length, getAddressControl(false, accountAddressContainer, null));
                    accountAddressContainer.getChildren().add(accountAddressContainer.getChildren().toArray().length, getAddressControl(true, accountAddressContainer, null));
                }
            });
            hBoxPostcode.getChildren().add(btnNewAddr);

            Button btnSaveChanges = new Button("Save Changes");
            btnSaveChanges.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //TODO - save here
                    System.out.println("Save changes to address here");
                }
            });
            hBoxPostcode.getChildren().add(btnSaveChanges);

            returnBox.getChildren().add(hBoxPostcode);
        } else {
            returnBox.getChildren().add(getAddressEntry(returnBox, "TextField", "Postcode", ""));
            returnBox.getChildren().add(getAddressEntry(returnBox, "ChoiceBox", "Country", ""));
            returnBox.getChildren().add(getAddressEntry(returnBox, "TextField", "Town/City/Region", ""));
            returnBox.getChildren().add(getAddressEntry(returnBox, "TextField", "House Name", ""));
            returnBox.getChildren().add(getAddressEntry(returnBox, "CheckBox", "Billing Address", ""));

            Button btnDeleteAddress = new Button("Delete Address");
            btnDeleteAddress.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    parentContainer.getChildren().remove(returnBox);
                }
            });
            returnBox.getChildren().add(btnDeleteAddress);
        }

        return returnBox;
    }*/

    /*private HBox getAddressEntry(Node Parent, String ObjectType, String Title, String Value) {
        HBox returnBox = new HBox(); returnBox.getStyleClass().add("stanrdardHBox"); returnBox.setHgrow(Parent, Priority.ALWAYS);
        Label lbl = new Label(Title); lbl.getStyleClass().add("standardLabel"); lbl.setMinWidth(150); returnBox.getChildren().add(lbl);
        switch(ObjectType) {
            case "TextField":
                TextField txtfld = new TextField(Value);
                txtfld.setPromptText("Please enter " + Title + " here");
                txtfld.setPrefWidth(300);
                returnBox.getChildren().add(txtfld);
                break;
            case "ChoiceBox":
                ChoiceBox chcBox = new ChoiceBox();
                chcBox.getItems().add("oof");
                chcBox.getItems().add("oof 2");
                chcBox.setPrefWidth(300);
                returnBox.getChildren().add(chcBox);
                break;
            case "CheckBox":
                CheckBox chkBox = new CheckBox();
                returnBox.getChildren().add(chkBox);
                break;
        }
        return returnBox;
    }*/


    @FXML protected void initialize() {
        System.out.println("Accounts Initialized");
    }

}
