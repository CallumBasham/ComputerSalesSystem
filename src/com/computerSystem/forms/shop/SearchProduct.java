package com.computerSystem.forms.shop;

import com.computerSystem.Main;
import com.computerSystem.models.classes.Product;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchProduct {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    @FXML private ImageView searchProduct_ImageView_proejctImage;
    @FXML private Button searchProduct_Button_viewProduct;
    @FXML private Label searchProduct_Label_productName;
    @FXML private Label searchProduct_Label_productBasePrice;
    @FXML private Label searchProduct_Label_productCategory;

    private Product sentProduct;


    public void setup(Product product) {
        sentProduct = product;
        searchProduct_ImageView_proejctImage.setImage(product.getProductImage());
        searchProduct_Label_productName.setText(product.getProductName());
        searchProduct_Label_productBasePrice.setText(String.valueOf(product.getProductBasePrice()));
        searchProduct_Label_productCategory.setText(product.getProductCategory());
    }

    @SuppressWarnings("Duplicates")
    @FXML private void handleProductClick(Event ev) {
        if(Main.localUser.userAccount.getAuthenticated()){
            try {
                FXMLLoader newLoader = masterController.getCustomControl("shop/ProductView.fxml");
                Parent root = newLoader.load();
                Stage newStage = new Stage();
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("Amend Lol");
                Scene scn = new Scene(root, 800, 400);
                newStage.setScene(scn);
                //newStage.setAlwaysOnTop(true);
                ProductView controller = newLoader.getController();
                controller.setup(sentProduct);
                newStage.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION, "You must login/create account before going further.", ButtonType.OK);
            al.showAndWait();
        }

    }

    //more
}
