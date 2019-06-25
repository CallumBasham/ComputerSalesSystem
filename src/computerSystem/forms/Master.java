package computerSystem.forms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Master extends Application {

    public static void initMaster()
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("Master.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Master.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setMinHeight(400); primaryStage.setMinWidth(600);
        primaryStage.show();
    }


}
