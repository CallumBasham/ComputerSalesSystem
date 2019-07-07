package com.computerSystem.forms.shop;

import com.computerSystem.Main;
import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Date;


public class Inventory {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    @FXML TableView shopTableView_Inventory_tbProducts;
    private ObservableList<Product> shopTableView_Inventory_tbProducts_DataSource;

    @FXML Button shopButton_Inventory_addProduct;

    @FXML protected void initialize() {
        shopTableView_Inventory_tbProducts_DataSource = FXCollections.observableArrayList();

        System.out.println("Inventory Initialized");

        TableColumn columnProductID = new TableColumn("Product ID");//Column Header
        columnProductID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProductID")); //Column Value

        TableColumn columnProductActive = new TableColumn("Product Active");//Column Header
        columnProductActive.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("ProductActive"));

        TableColumn columnProductName = new TableColumn("Product Name");//Column Header
        //columnProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName")); //Column Value
        columnProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));

        TableColumn columnProductDescription = new TableColumn("Product Description");//Column Header
        columnProductDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductDescription")); //Column Value

        TableColumn columnProductBasePrice = new TableColumn("Product Base Price");//Column Header
        columnProductBasePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("ProductBasePrice")); //Column Value

        TableColumn columnProductCategory = new TableColumn("Product Category");//Column Header
        columnProductCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductCategory")); //Column Value

        TableColumn columnTest = new TableColumn("Options");//Column Header
        columnTest.setCellFactory(new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                    public TableCell call(final TableColumn<Product, String> param) {
                        final TableCell<Product, String> cell = new TableCell<Product, String>() {

                            //Button btn = new Button("Delete Product");
                            HBox parentHBox = new HBox();

                            public void updateItem(String item, boolean empty) {

                                parentHBox.getChildren().clear();

                                Button deleteProduct = new Button("Delete Product");
                                parentHBox.getChildren().add(deleteProduct);

                                Button editProduct = new Button("Edit Changes");
                                parentHBox.getChildren().add(editProduct);

                                //super.updateItem(item, empty);
                                if (!empty) {
                                    deleteProduct.setOnAction(event -> {
                                        Product currentProduct = getTableView().getItems().get(getIndex());
                                        System.out.println(currentProduct.getProductName()+ "   " + currentProduct.getProductBasePrice());
                                        //person.setProductName("Delete Requested lol");
                                        Alert al = new Alert(Alert.AlertType.CONFIRMATION, "About to delete: " + currentProduct.getProductName() + " (ID: " + currentProduct.getProductID() + ")\nAre you sure?", ButtonType.YES, ButtonType.CANCEL);
                                        al.showAndWait();
                                        if(al.getResult() == ButtonType.YES) {
                                            //TODO - Make sure it does not have active orders
                                            //shopTableView_Inventory_tbProducts_DataSource.remove(currentProduct);
                                            int getLastLocation = super.getIndex();
                                            Main.localShop.localProductsList.remove(currentProduct);
                                            getProductItems();
                                            DatabaseInteraction.StoredProcedures.NonQuery.deleteProduct(currentProduct);
                                            shopTableView_Inventory_tbProducts.scrollTo(getLastLocation);
                                            //TODO - Finally delete from the database as well
                                            //hopTableView_Inventory_tbProducts.refresh();
                                        }
                                    });
                                    editProduct.setOnAction(event -> {
                                        try {
                                            Product product = getTableView().getItems().get(getIndex());
                                            System.out.println(product.getProductName() + "   " + product.getProductBasePrice());
                                            FXMLLoader newLoader = masterController.getCustomControl("shop/InventoryProductEdit.fxml");
                                            Parent root = newLoader.load();
                                            Stage newStage = new Stage();
                                            newStage.initModality(Modality.APPLICATION_MODAL);
                                            newStage.setTitle("Amend Lol");
                                            Scene scn = new Scene(root, 800, 400);
                                            newStage.setScene(scn);
                                            //newStage.setAlwaysOnTop(true);
                                            InventoryProductEdit controller = newLoader.getController();
                                            controller.setup(product);
                                            newStage.showAndWait();
                                            getProductItems();
                                            shopTableView_Inventory_tbProducts.scrollTo(product);
                                        } catch (Exception ex) {
                                            masterController.printFullException(ex);
                                        }
                                    });
                                    setGraphic(parentHBox);
                                    //setText("rip");
                                }
                            }
                        };
                        return cell;
                    }
                });

        getProductItems();
        shopTableView_Inventory_tbProducts.getColumns().addAll(columnProductID, columnProductActive, columnProductName, columnProductDescription, columnProductBasePrice, columnProductCategory, columnTest);

        System.out.println("Inventory Has loaded default data");
    }

    @FXML private void handleClick_addProduct(Event ev) {
        Product newProduct = new Product(DatabaseInteraction.StoredProcedures.Scalar.getNextMaxProductID(), false, "New Item", "Created: " + new Date(), 0, "");
        Main.localShop.localProductsList.add(newProduct);
        DatabaseInteraction.StoredProcedures.NonQuery.postNewProduct(newProduct);
        getProductItems();
        shopTableView_Inventory_tbProducts.scrollTo(shopTableView_Inventory_tbProducts.getItems().toArray().length);
    }

    private void getProductItems() {
        shopTableView_Inventory_tbProducts_DataSource.clear();
        shopTableView_Inventory_tbProducts_DataSource.addAll(Main.localShop.localProductsList);
        shopTableView_Inventory_tbProducts.setItems(shopTableView_Inventory_tbProducts_DataSource);
        shopTableView_Inventory_tbProducts.refresh();
    }

}
