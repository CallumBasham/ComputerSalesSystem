package computerSystem.forms.accounts;

import computerSystem.Main;
import computerSystem.database.DatabaseInteraction;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
                        masterController.loadPage("Home.fxml");
                    } else {
                        System.out.println("Credentials incorrect!");
                    }
                } else {
                    System.out.println("Fields have not been validated!");
                }
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
                                createTxtField_Email.getText(),
                                createTxtField_Phone.getText(),
                                createPssField_Pass.getText(),
                                1
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

    @FXML protected void initialize() {
        System.out.println("Accounts Initialized");
    }

}
