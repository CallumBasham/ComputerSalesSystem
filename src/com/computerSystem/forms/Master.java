package com.computerSystem.forms;

import com.computerSystem.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class Master extends Application {

    //These are static as they may need to be accessed by sub-controllers
    public static Stage stage;
    public static Scene scene;
    public static Parent root;


    //Master FXML Controls
    @FXML private  AnchorPane masterContainer;
    @FXML private AnchorPane loaderAnchorPane;
    @FXML private MenuButton userAccountButton;
    @FXML private ImageView accountIcon;
    @FXML private ImageView imageview_UserIcon;

    //Starts the JavaFX Application
    public static void initMaster() { launch(); }

    //Default JavaFX method to initialize a JavaFX application, called by "launch()"
    @Override public void start(Stage _primaryStage) throws Exception{
        //Initialize the JavaFX Application
        stage = _primaryStage;
        root = FXMLLoader.load(getClass().getResource("Master.fxml"));
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        replaceCurrentStyle("styles/StandardStyles.css");

        //Set Defaults
        stage.setTitle("3CS Computers");
        stage.setMinHeight(400); stage.setMinWidth(600);
        stage.show();

        //Load the default "Home" page onto the above
        loadPage("Home.fxml");

        System.out.println("--[[ UI Finished Loading! ]]--");

        //Gather the data sets from the Database
        Main.localShop.importShopData();
    }

    //Method - Loads a page into the container
    public Object[] loadPage(String FXMLFile) {
        Object[] returnObjects;
        AnchorPane pageContainer = (AnchorPane) root.lookup("#loaderAnchorPane");
        pageContainer.getChildren().clear();
        try {
            //Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFile));
            AnchorPane LoadingPane = loader.load();
            Object controller = loader.getController();

            //Set Defaults
            AnchorPane.setTopAnchor(LoadingPane, 0.0);
            AnchorPane.setBottomAnchor(LoadingPane, 0.0);
            AnchorPane.setLeftAnchor(LoadingPane, 0.0);
            AnchorPane.setRightAnchor(LoadingPane, 0.0);
            pageContainer.getChildren().add((LoadingPane));
            returnObjects = new Object[] {LoadingPane, controller};
        } catch (Exception ex) {
            printFullException(ex);
            returnObjects = null;
        }
        displayMasterDetails();
        return returnObjects;
    }

    //Method - Loads an FXML Control and returns to caller for usage
    public FXMLLoader getCustomControl(String FXMLFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFile));
            //Node returnNode = FXMLLoader.load(getClass().getResource("customControls/AddressItem.fxml"));
            //return new Object[] {returnNode, };
            return loader;
        } catch (Exception ex) {
            printFullException(ex);
            return null;
        }
    }

    //Method - Updates the Master page
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

                if(Main.localUser.userAccount.getAccountType()){
                    HBox adminCtrl1 = (HBox) root.lookup("#adminTitle"); adminCtrl1.setVisible(true);
                    HBox adminCtrl2 = (HBox) root.lookup("#Sidebar_btnAccounts"); adminCtrl2.setVisible(true);
                    HBox adminCtrl3 = (HBox) root.lookup("#Sidebar_btnStock"); adminCtrl3.setVisible(true);
                } else {
                    HBox adminCtrl1 = (HBox) root.lookup("#adminTitle"); adminCtrl1.setVisible(false);
                    HBox adminCtrl2 = (HBox) root.lookup("#Sidebar_btnAccounts"); adminCtrl2.setVisible(false);
                    HBox adminCtrl3 = (HBox) root.lookup("#Sidebar_btnStock"); adminCtrl3.setVisible(false);
                }
            }
            if(Main.localUser.userAccount.getUserImage() != null) {
                ImageView userIcn = (ImageView) root.lookup("#imageview_UserIcon");
                userIcn.setImage(Main.localUser.userAccount.getUserImage());
            }
        } else {
            userBtn.setText("Login");
            HBox adminCtrl1 = (HBox) root.lookup("#adminTitle"); adminCtrl1.setVisible(false);
            HBox adminCtrl2 = (HBox) root.lookup("#Sidebar_btnAccounts"); adminCtrl2.setVisible(false);
            HBox adminCtrl3 = (HBox) root.lookup("#Sidebar_btnStock"); adminCtrl3.setVisible(false);
            ImageView userIcn = (ImageView) root.lookup("#imageview_UserIcon"); userIcn.setImage( new Image(getClass().getResourceAsStream("content/Icon_Account.png")));
        }

    }

    //Method - Writes entire exception: For debugging
    public void printFullException(Exception ex) {
        System.out.print("LOAD PAGE EXCEPTION:");
        System.out.print(ex.getMessage());
        System.out.print(ex.getCause());
        System.out.println(ex.getLocalizedMessage());
        System.out.println(ex.getStackTrace()[0]);
        int i = 0;
        while (i < ex.getStackTrace().length) {
            System.out.println(ex.getStackTrace()[i]);
            i++;
        }
    }

    //Event - Handles all Master sidebar button clicks (usually loads a page)
    @FXML private void handleClick(Event ev) {
        HBox box = (HBox)ev.getSource();
            if(box.getId().equals("Sidebar_btnHome")) {
                System.out.println("Home Clicked!");
                loadPage("Home.fxml");
            } else if(box.getId().equals("Sidebar_btnAbout")) {
                System.out.println("About Clicked!");
                loadPage("About.fxml");
            } else if(box.getId().equals("Sidebar_btnShop")) {
                System.out.println("Shop Clicked!");
                loadPage("shop/Shop.fxml");
            } else if(box.getId().equals("Sidebar_btnStock")) {
                System.out.println("Shop Clicked!");
                //Admin Only
                if(Main.localUser.userAccount.getAuthenticated() && Main.localUser.userAccount.getAccountType()){
                    loadPage("shop/Inventory.fxml");
                }
            } else if(box.getId().equals("Sidebar_btnAccounts")) {
                System.out.println("Admin Accounts Clicked!");
                //Admin Only
                if(Main.localUser.userAccount.getAuthenticated() && Main.localUser.userAccount.getAccountType()){
                    loadPage("accounts/Admin.fxml");
                }
            } else if(box.getId().equals("Sidebar_btnAccessibility")) {
                System.out.println("Mode Changed!");
                if(scene.getStylesheets().get(0).contains("StandardStyles.css")) {
                    replaceCurrentStyle("styles/StandardStylesGreen.css");
                } else if (scene.getStylesheets().get(0).contains("StandardStylesGreen.css")) {
                    replaceCurrentStyle("styles/StandardStylesLightMode.css");
                } else if (scene.getStylesheets().get(0).contains("StandardStylesLightMode.css")) {
                    replaceCurrentStyle("styles/StandardStyles.css");
                }
            } else {
                System.out.println("Unknown Clicked!");
            }
    }

    //Event - Handles the acocunt click
    @FXML private void accountHandleClick(Event ev) {
        if(!Main.localUser.userAccount.getAuthenticated()){
            loadPage("accounts/Login.fxml");
        }
    }

    private void replaceCurrentStyle(String newStyle) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(newStyle).toExternalForm());
    }

    public String getCurrentSheet(){
        return scene.getStylesheets().get(0);
    }
}
