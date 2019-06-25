package computerSystem.forms;

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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Master extends Application {

    public static void initMaster()
    {
        launch();
    }

    private Parent root;
    @FXML private AnchorPane MasterContainer;

    @Override public void start(Stage primaryStage) throws Exception{
        //Initialize the JavaFX Application
        root = FXMLLoader.load(getClass().getResource("Master.fxml"));
        primaryStage.setTitle("3CS Computers");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setMinHeight(400); primaryStage.setMinWidth(600);
        primaryStage.show();

        //Load the default "Home" page onto the above
        loadPage("Home.fxml");
    }

    //Container Methods
    private void loadPage(String FXMLFile) throws IOException {
        AnchorPane pageContainer = (AnchorPane) root.lookup("#loaderAnchorPane");
        pageContainer.getChildren().removeAll();
        AnchorPane LoadingPane = FXMLLoader.load(getClass().getResource("Home.fxml"));
        //LoadingPane.setPrefWidth(pageContainer.getWidth());
        //LoadingPane.setPrefHeight(pageContainer.getHeight());
        AnchorPane.setTopAnchor(LoadingPane, 0.0);
        AnchorPane.setBottomAnchor(LoadingPane, 0.0);
        AnchorPane.setLeftAnchor(LoadingPane, 0.0);
        AnchorPane.setRightAnchor(LoadingPane, 0.0);
        pageContainer.getChildren().add((LoadingPane));
    }

    //Events
    @FXML private void handleClick(Event ev) {
        System.out.println("clicc");
        HBox box = (HBox)ev.getSource();
        for (Node ctrl: box.getChildren()) {
            if(ctrl instanceof Label)
            {
                ((Label) ctrl).setText("Clicked");
            }
        }
    }



}
