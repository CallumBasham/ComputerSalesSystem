package com.computerSystem.forms.shop;

import com.computerSystem.Main;
import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Order;
import com.computerSystem.models.classes.Product;
import com.computerSystem.models.classes.productOptionalExtras.FirstClassDelivery;
import com.computerSystem.models.classes.productOptionalExtras.GiftWrapping;
import com.computerSystem.models.classes.productOptionalExtras.Warranty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductView {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    private Product sentProduct;

    List<String> existingExtras = new ArrayList<>();

    @FXML private AnchorPane productViewMasterContainer;
    @FXML private VBox productView_VBox_productExtrasContainer;
    @FXML private ImageView productView_ImageView_productImage;
    @FXML private Button productView_Button_purchase;
    @FXML private Button productView_Button_cancel;
    @FXML private Label productView_Label_productName;
    @FXML private Label productView_Label_productDescription;
    @FXML private TextField productView_TextField_productTotalPrice;
    @FXML private TextField productView_TextField_productCategory;
    @FXML private TextField productView_TextField_productQuantity;

    public void setup(Product product) {
        productViewMasterContainer.getStylesheets().clear();
        productViewMasterContainer.getStylesheets().add(masterController.getCurrentSheet());
        sentProduct = product;
        productView_ImageView_productImage.setImage(product.getProductImage());
        productView_Label_productName.setText(product.getProductName());
        productView_Label_productDescription.setText(product.getProductDescription());
        productView_TextField_productTotalPrice.setText(String.valueOf(product.getProductBasePrice()));
        productView_TextField_productCategory.setText(product.getProductCategory());

        //Force text only input
        productView_TextField_productQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    productView_TextField_productQuantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        addDefaultOptionalExtraButton();
    }

    @FXML private void handleButtonClick_cancel(Event ev) {((Stage)((Button)ev.getSource()).getScene().getWindow()).close(); }


    @FXML private void handleButtonClick_purchase(Event ev) {
        Order newOrder = getOrder();
        Main.localUser.userOrders.add(newOrder);
        newOrder.saveOrder();

        Alert al = new Alert(Alert.AlertType.INFORMATION, "Your order has been placed!\nThanks for shopping with 3CS Computers\nWould you like a receipt?", ButtonType.YES, ButtonType.NO);
        al.showAndWait();
        if(al.getResult() == ButtonType.YES) {

            JFileChooser jfc = new JFileChooser();
            JFrame newframe = new JFrame();
            jfc.setDialogTitle("Specify a file to save");

            int userSeect = jfc.showSaveDialog(newframe);

            if(userSeect == JFileChooser.APPROVE_OPTION){
                File filetoSave = jfc.getSelectedFile();
                System.out.println("Saving to: " + filetoSave.getPath());

                try{
                    String fileWIthTXT = filetoSave.getPath() + ".txt";
                    System.out.println("Saving to: " + fileWIthTXT);
                    PrintWriter writer = new PrintWriter(fileWIthTXT, StandardCharsets.UTF_8);

                    writer.println("3CS Computers: Customer receipt\n");
                    writer.println("Your Order ID: " + newOrder.getOrderID());
                    writer.println("Product Name: " + sentProduct.getProductName());
                    writer.println("Quantity Requested: " + newOrder.getOrderQuantity());
                    writer.println("Total Price: " + newOrder.getOrderTotalPrice());
                    writer.println();
                    writer.println("Thank you for placing your order today (" + new Date() + ")\nWe look forward to your future visits!");

                    writer.close();
                    writer.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            //jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //jfc.showDialog(null, "Select Folder");


            //File file = jfc.getSelectedFile();
            //System.out.println(file.toURI().toString());
            //javafx.scene.image.Image img = new Image(file.toURI().toString());
            //inventorypProductEdit_ImageView_productImage.setImage(img);
            //sentProduct.setProductImageFile(file);
        }
        ((Stage)((Button)ev.getSource()).getScene().getWindow()).close();
    }

    @FXML private void handleQuantityChange(Event ev) {
        performCalculations();
    }

    @SuppressWarnings("Duplicates")
    private void addDefaultOptionalExtraButton(){
        HBox parentBox = new HBox();
        parentBox.setSpacing(5);
        parentBox.setId("optionalExtraAdderHBox");
        parentBox.setAlignment(Pos.TOP_CENTER);

        Label caption = new Label("Add Extra");
        caption.setStyle("-fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold; -fx-min-width: 100;");
        HBox.setHgrow(caption, Priority.ALWAYS);
        parentBox.getChildren().add(caption);

        ChoiceBox extrasList = new ChoiceBox();
        extrasList.setStyle("-fx-min-width: 250;");
        HBox.setHgrow(extrasList, Priority.ALWAYS);
        extrasList.getItems().setAll("Gift Wrapping", "Warranty", "First Class Delivery");
        extrasList.setOnAction(event -> {
            performCalculations();
        });
        parentBox.getChildren().add(extrasList);

        Button extraAdder = new Button("Add");
        extraAdder.setStyle("-fx-min-width: 150;");
        HBox.setHgrow(extraAdder, Priority.ALWAYS);
        extraAdder.setOnAction(event -> {
            if(extrasList.getSelectionModel().getSelectedItem() != null){
                if(!existingExtras.contains(extrasList.getSelectionModel().getSelectedItem().toString())){
                    existingExtras.add(extrasList.getSelectionModel().getSelectedItem().toString());
                    addOptionalExtraItem(extrasList.getSelectionModel().getSelectedItem().toString(), getInheritedExtraProduct(extrasList.getSelectionModel().getSelectedItem().toString()));
                }

            }
        });
        parentBox.getChildren().add(extraAdder);

        productView_VBox_productExtrasContainer.getChildren().clear();
        existingExtras.clear();
        productView_VBox_productExtrasContainer.getChildren().add(parentBox);
    }

    private String[] getInheritedExtraProduct(String selectedCategory){
        String[] returnOptions = new String[]{};
        switch(selectedCategory){
            case"Gift Wrapping":
                GiftWrapping giftWrapping = new GiftWrapping(sentProduct);
                returnOptions = giftWrapping.getCompiledOptions();
                break;
            case"Warranty":
                Warranty warranty = new Warranty(sentProduct);
                returnOptions = warranty.getCompiledOptions();
                break;
            case"First Class Delivery":
                FirstClassDelivery firstClassDelivery = new FirstClassDelivery(sentProduct);
                returnOptions = firstClassDelivery.getCompiledOptions();
                break;
        }
        return returnOptions;
    }

    @SuppressWarnings("Duplicates")
    private void addOptionalExtraItem(String selectedItem, String[] extraOptions) {
        HBox parentBox = new HBox();
        parentBox.setSpacing(5);
        parentBox.setId("optionalExtraHBox");
        parentBox.setAlignment(Pos.TOP_CENTER);

        Label caption = new Label(selectedItem);
        caption.setStyle("-fx-text-fill: white; -fx-font-size: 12; -fx-font-weight: bold; -fx-min-width: 100;");
        HBox.setHgrow(caption, Priority.ALWAYS);
        parentBox.getChildren().add(caption);

        ChoiceBox extrasList = new ChoiceBox();
        extrasList.setStyle("-fx-min-width: 250;");
        HBox.setHgrow(extrasList, Priority.ALWAYS);
        extrasList.getItems().setAll(FXCollections.observableArrayList(extraOptions));
        extrasList.setOnAction(event -> {
            performCalculations();
        });
        parentBox.getChildren().add(extrasList);

        Button extraRemover = new Button("Remove");
        extraRemover.setStyle("-fx-min-width: 150;");
        HBox.setHgrow(extraRemover, Priority.ALWAYS);
        extraRemover.setOnAction(event -> {
            productView_VBox_productExtrasContainer.getChildren().remove(parentBox);
            existingExtras.remove(caption.getText());
            performCalculations();
        });

        parentBox.getChildren().add(extraRemover);

        productView_VBox_productExtrasContainer.getChildren().add(parentBox);
    }

    private void performCalculations(){
        if(productView_TextField_productQuantity.getText().length() > 0) {
            double calculatedTotalSellingPrice = sentProduct.getProductBasePrice();
            calculatedTotalSellingPrice = calculatedTotalSellingPrice * Integer.parseInt(productView_TextField_productQuantity.getText().replaceAll("[\\D]", ""));
            calculatedTotalSellingPrice = calculatedTotalSellingPrice + getExtraCalculations();
            productView_TextField_productTotalPrice.setText(String.valueOf(calculatedTotalSellingPrice));
        }
    }

    private double getExtraCalculations() {
        double totalPrice = 0;
        for(Node element : productView_VBox_productExtrasContainer.getChildren()){
            if(element instanceof HBox){
                if(element.getId() != null) {
                    if(element.getId() == "optionalExtraHBox")
                    {
                        for(Node subelement : ((HBox) element).getChildren()){
                            if(subelement instanceof Label){

                            } else if(subelement instanceof ChoiceBox) {
                                ChoiceBox subBox = (ChoiceBox)subelement;
                                if(subBox.getSelectionModel().getSelectedItem().toString().length() > 0) {
                                    String[] textSections = subBox.getSelectionModel().getSelectedItem().toString().replace(" ", "").split(":");
                                    totalPrice = totalPrice + Double.parseDouble(textSections[1]);
                                }
                            }
                        }
                    }
                }
            }
        }
        return totalPrice;
    }

    private Order getOrder() {
        Order order = new Order();
        order.setOrderID(DatabaseInteraction.StoredProcedures.Scalar.getNextMaxOrderID());
        order.setUserID(Main.localUser.userAccount.getUserID());
        order.setProductID(sentProduct.getProductID());
        order.setOrderQuantity(Integer.parseInt(productView_TextField_productQuantity.getText().replaceAll("[\\D]", "")));
        order.setOrderTotalPrice(Double.parseDouble(productView_TextField_productTotalPrice.getText()));
        order.setOrderExtras(getCompileData());
        return order;
    }

    private String getCompileData() {
        String extraData = "";
        for(Node element : productView_VBox_productExtrasContainer.getChildren()){
            if(element instanceof HBox){
                if(element.getId() != null) {
                    if(element.getId() == "optionalExtraHBox")
                    {
                        for(Node subelement : ((HBox) element).getChildren()){
                            if(subelement instanceof Label){
                                if(extraData.length() == 0){
                                    extraData = ((Label) subelement).getText() + "-";
                                } else {
                                    extraData = extraData + "," + ((Label) subelement).getText() + "-";
                                }
                            } else if(subelement instanceof ChoiceBox) {
                                ChoiceBox subBox = (ChoiceBox)subelement;
                                if(subBox.getSelectionModel().getSelectedItem().toString().length() > 0) {
                                    extraData = extraData + subBox.getSelectionModel().getSelectedItem().toString();
                                }
                            }
                        }
                    }
                }
            }
        }
        return extraData;
    }

}
