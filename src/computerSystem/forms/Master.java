package computerSystem.forms;

import computerSystem.Main;
import computerSystem.forms.accounts.Account;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Master extends Application {

    public static void initMaster()
    {
        launch();
    }

    //These are static as they may need to be accessed by sub-controllers
    public static Stage stage;
    public static Scene scene;
    public static Parent root;

    //Master FXML Controls
    @FXML private AnchorPane MasterContainer;
    @FXML private AnchorPane loaderAnchorPane;
    @FXML private MenuButton userAccountButton;

    @Override public void start(Stage _primaryStage) throws Exception{
        //Initialize the JavaFX Application
        stage = _primaryStage;
        root = FXMLLoader.load(getClass().getResource("Master.fxml"));
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);

        //Set Defaults
        stage.setTitle("3CS Computers");
        stage.setMinHeight(400); stage.setMinWidth(600);
        stage.show();

        //Load the default "Home" page onto the above
        loadPage("Home.fxml"); //TODO - Switch to Home.fxml

        System.out.println("--[[ UI Finished Loading! ]]--");
    }

    //Container Methods
    @SuppressWarnings("Duplicates")
    public void loadPage(String FXMLFile) {
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
        displayMasterDetails();
    }


    //Events
    @FXML private void handleClick(Event ev) {
        HBox box = (HBox)ev.getSource();
            if(box.getId().equals("Sidebar_btnHome")) {
                System.out.println("Home Clicked!");
                loadPage("Home.fxml");
            } else if(box.getId().equals("Sidebar_btnAbout")) {
                System.out.println("About Clicked!");
                loadPage("About.fxml");
            } else {
                System.out.println("Unknown Clicked!");
                loadPage("Temp_Placeholder.fxml");
            }

    }

    @FXML private void accountHandleClick(Event ev) {
        if(!Main.localUser.userAccount.getAuthenticated()){
            loadPage("accounts/Login.fxml");
        }
    }

    private void displayMasterDetails(){
        MenuButton userBtn = (MenuButton) root.lookup("#userAccountButton");
        if(Main.localUser.userAccount.getAuthenticated()) {
            userBtn.setText(Main.localUser.userAccount.getUsername());
            if(userBtn.getItems().toArray().length == 0) {

                MenuItem menuitemEditAccount = new MenuItem("Edit Account");
                menuitemEditAccount.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        loadPage("accounts/Account.fxml");
                    }
                });
                userBtn.getItems().add(menuitemEditAccount);

                MenuItem menuitemSignOut = new MenuItem("Sign Out");
                menuitemSignOut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Main.localUser.signOut();
                        userBtn.getItems().clear();
                        loadPage("Home.fxml");
                    }
                });
                userBtn.getItems().add(menuitemSignOut);

            }
        } else {
            userBtn.setText("Login");
        }
    }

    public FXMLLoader getCustomControl(String FXMLFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customControls/AddressItem.fxml"));
            //Node returnNode = FXMLLoader.load(getClass().getResource("customControls/AddressItem.fxml"));
            //return new Object[] {returnNode, };
            return loader;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
