package computerSystem.forms.accounts;

import computerSystem.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import java.io.IOException;



public class Account {
    @FXML private AnchorPane masterAnchorPane;
    @FXML private ProgressIndicator loginTxtField_Username_Icon;
    @FXML private ProgressIndicator loginPssField_Pass_Icon;

    @FXML private void handleTextChanged(Event ev) {
        if(ev.getSource() instanceof TextField){
            TextField _handledObject = (TextField)ev.getSource();
            switch (_handledObject.getId()){
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
            }
        }
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













    /*public void loadSubPage(String _page)
    {
        AnchorPane pageContainer = (AnchorPane) root.lookup("#loaderAnchorPane");
        pageContainer.getChildren().clear();
        try {
            AnchorPane LoadingPane = FXMLLoader.load(getClass().getResource(FXMLFile));
            AnchorPane.setTopAnchor(LoadingPane, 0.0);
            AnchorPane.setBottomAnchor(LoadingPane, 0.0);
            AnchorPane.setLeftAnchor(LoadingPane, 0.0);
            AnchorPane.setRightAnchor(LoadingPane, 0.0);
            pageContainer.getChildren().add((LoadingPane));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public void loadLogin() {
        root = _root;
        AnchorPane _masterAnchorPane = (AnchorPane) root.lookup("#masterAnchorPane");*/

        /*VBox vBox = new VBox();
        AnchorPane.setTopAnchor(vBox, 50.0);
        AnchorPane.setBottomAnchor(vBox, 50.0);
        AnchorPane.setLeftAnchor(vBox, 100.0);
        AnchorPane.setRightAnchor(vBox, 100.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        Label lblUsernameTitle = new Label("Username or Email");
        lblUsernameTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        vBox.getChildren().add((lblUsernameTitle));

        TextField txtfldUsernameValue = new TextField();
        txtfldUsernameValue.setMaxHeight(20);
        txtfldUsernameValue.setMaxWidth (400);
        vBox.getChildren().add((txtfldUsernameValue));

        Label lblPasswordTitle = new Label("Password");
        lblPasswordTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        vBox.getChildren().add((lblPasswordTitle));

        PasswordField passfldPasswordValue = new PasswordField();
        passfldPasswordValue.setMaxHeight(20);
        passfldPasswordValue.setMaxWidth(400);
        vBox.getChildren().add((passfldPasswordValue));

        Pane pneFiller = new Pane();
        pneFiller.setMinHeight(20);
        vBox.getChildren().add(pneFiller);

        HBox hboxOptions = new HBox(10);
        hboxOptions.setMaxWidth(600);
        hboxOptions.setAlignment(Pos.CENTER);

        Button btnLogin = new Button("Login");
        btnLogin.setId("btnLogin_" + "loadLogin");
        btnLogin.setMinWidth(100); btnLogin.setMaxWidth(100);
        hboxOptions.getChildren().add((btnLogin));

        Button btnForgot = new Button("Forgot Login");
        btnLogin.setId("btnForgot" + "loadLogin");
        btnForgot.setMinWidth(100); btnForgot.setMaxWidth(100);
        hboxOptions.getChildren().add((btnForgot));

        Button btnCreate = new Button("Create Account");
        btnLogin.setId("btnCreate" + "loadLogin");
        btnCreate.setMinWidth(100); btnForgot.setMaxWidth(100);
        btnCreate.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AnchorPane _masterAnchorPane = (AnchorPane) root.lookup("#masterAnchorPane");
                _masterAnchorPane.getChildren().clear();
                loadCreate(root);

            }
        });
        hboxOptions.getChildren().add((btnCreate));

        vBox.getChildren().add((hboxOptions));
        _masterAnchorPane.getChildren().add(vBox);
    }

    public void loadCreate() {
        AnchorPane _masterAnchorPane = (AnchorPane) computerSystem.forms.Master.root.lookup("#masterAnchorPane");

        VBox vBox = new VBox();
        AnchorPane.setTopAnchor(vBox, 50.0);
        AnchorPane.setBottomAnchor(vBox, 50.0);
        AnchorPane.setLeftAnchor(vBox, 100.0);
        AnchorPane.setRightAnchor(vBox, 100.0);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        Label lblUsernameTitle = new Label("Username");
        lblUsernameTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        vBox.getChildren().add((lblUsernameTitle));

        TextField txtfldUsernameValue = new TextField();
        txtfldUsernameValue.setMaxHeight(20);
        txtfldUsernameValue.setMaxWidth (400);
        vBox.getChildren().add((txtfldUsernameValue));

        _masterAnchorPane.getChildren().add(vBox);
    }

    public void loadLoggedIn() {

    }*/
}
