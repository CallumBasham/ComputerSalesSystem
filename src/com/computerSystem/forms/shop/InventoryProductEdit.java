package com.computerSystem.forms.shop;

import com.computerSystem.Main;
import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Product;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;


public class InventoryProductEdit {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    @FXML private AnchorPane anchorPane_Container;

    @FXML private Label inventorypProductEdit_Label_productID;

    @FXML private ImageView inventorypProductEdit_ImageView_productImage;
    @FXML private Button inventorypProductEdit_Button_productImage;

    @FXML private TextField inventorypProductEdit_TextField_productName;

    @FXML private CheckBox inventorypProductEdit_CheckBox_productActive;

    @FXML private TextField inventorypProductEdit_TextField_productDescription;

    @FXML private TextField inventorypProductEdit_TextField_productBasePrice;

    @FXML private ChoiceBox inventorypProductEdit_ChoiceBox_productCategory;

    @FXML private Button inventorypProductEdit_Button_save;

    @FXML private Button inventorypProductEdit_Button_cancel;

    private Product sentProduct;

    public void initalize(){
        System.out.println("InventoryProductEdit Initialized");
    }

    public void setup(Product product) {
        anchorPane_Container.getStylesheets().clear();
        anchorPane_Container.getStylesheets().add(masterController.getCurrentSheet());


        sentProduct =  product;
        inventorypProductEdit_Label_productID.setText("Product ID: " + product.getProductID());
        inventorypProductEdit_ImageView_productImage.setImage(product.getProductImage());
        inventorypProductEdit_TextField_productName.setText(product.getProductName());
        inventorypProductEdit_CheckBox_productActive.setSelected(product.getProductActive());
        inventorypProductEdit_TextField_productDescription.setText(product.getProductDescription());
        inventorypProductEdit_TextField_productBasePrice.setText(String.valueOf(product.getProductBasePrice()));
        inventorypProductEdit_ChoiceBox_productCategory.getSelectionModel().select(product.getProductCategory());
    }

    @FXML private void handle_Cancel_Click(Event ev){
        ((Stage)((Button)ev.getSource()).getScene().getWindow()).close();
    }

    @FXML private void handle_Save_Click(Event ev){
        Main.localShop.localProductsList.remove(sentProduct);
        sentProduct.setProductName(inventorypProductEdit_TextField_productName.getText());
        sentProduct.setProductActive(inventorypProductEdit_CheckBox_productActive.isSelected());
        sentProduct.setProductImage(inventorypProductEdit_ImageView_productImage.getImage());
        sentProduct.setProductDescription(inventorypProductEdit_TextField_productDescription.getText());
        sentProduct.setProductBasePrice(Double.valueOf(inventorypProductEdit_TextField_productBasePrice.getText()));
        sentProduct.setProductCategory((String)inventorypProductEdit_ChoiceBox_productCategory.getSelectionModel().getSelectedItem());
        Main.localShop.localProductsList.add(sentProduct);
        DatabaseInteraction.StoredProcedures.NonQuery.updateProduct(sentProduct);
        ((Stage)((Button)ev.getSource()).getScene().getWindow()).close();
    }

    @FXML private void handle_Upload_Click(Event ev){
        try{
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
            jfc.showDialog(null, "Select Product Image");
            jfc.setVisible(true);

            File file = jfc.getSelectedFile();
            System.out.println(file.toURI().toString());
            Image img = new Image(file.toURI().toString());
            inventorypProductEdit_ImageView_productImage.setImage(img);
            sentProduct.setProductImageFile(file);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
