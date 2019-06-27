package computerSystem.forms;

import computerSystem.forms.accounts.Account;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Master extends Application {

    public static void initMaster()
    {
        launch();
    }

    public static Parent root;
    private static Stage primaryStage;
    @FXML private AnchorPane MasterContainer;
    @FXML private AnchorPane loaderAnchorPane;

    @Override public void start(Stage _primaryStage) throws Exception{
        //Initialize the JavaFX Application
        primaryStage = _primaryStage;
        root = FXMLLoader.load(getClass().getResource("Master.fxml"));
        primaryStage.setTitle("3CS Computers");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setMinHeight(400); primaryStage.setMinWidth(600);
        primaryStage.show();

        //Load the default "Home" page onto the above
        loadPage("Home.fxml"); //TODO - Switch to Home.fxml
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
    }

    @SuppressWarnings("Duplicates")
    public void loadPage(String FXMLFile, AnchorPane pageContainer) {
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
        System.out.println("Account clicked");
        //if not logged in do stuff
        //else do nothing and display menu options

        /*Stage dia = new Stage();
        dia.initModality(Modality.NONE);
        dia.initOwner(primaryStage);
        Label lb = new Label();
        lb.setText("Hello there");

        Scene diaSce = new Scene(lb, 300, 200);
        dia.setScene(diaSce);
        dia.show();*/

        //loadPage("accounts/Account.fxml");

        loadPage("accounts/Login.fxml");
    }
}
