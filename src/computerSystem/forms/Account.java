package computerSystem.forms;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Account {
    @FXML private AnchorPane masterAnchorPane;
    private Parent root;

    public void loadLogin(Parent _root) {
        root = _root;
        AnchorPane _masterAnchorPane = (AnchorPane) root.lookup("#masterAnchorPane");
        VBox vBox = new VBox();
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
                loadCreate();

            }
        });
        hboxOptions.getChildren().add((btnCreate));

        vBox.getChildren().add((hboxOptions));
        _masterAnchorPane.getChildren().add(vBox);
    }

    public void loadCreate() {
        System.out.println("Loading Create Menu");
    }

    public void loadLoggedIn() {

    }
}
