package computerSystem.forms.shop;

import computerSystem.database.DatabaseInteraction;
import computerSystem.models.classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Inventory {

    @FXML TableView shopTableView_Inventory_tbProducts;

    @FXML protected void initialize() {
        System.out.println("Inventory Initialized");

        shopTableView_Inventory_tbProducts = new TableView<computerSystem.models.classes.Product>();

        TableColumn<computerSystem.models.classes.Product, Integer> columnProductID = new TableColumn<>("Product ID");//Column Header
        columnProductID.setMinWidth(50);
        columnProductID.setCellValueFactory(new PropertyValueFactory<>("ProductID")); //Column Value

        TableColumn<computerSystem.models.classes.Product, Integer> columnProductName = new TableColumn<>("Product Name");//Column Header
        columnProductName.setMinWidth(50);
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName")); //Column Value



        shopTableView_Inventory_tbProducts.getColumns().addAll(columnProductID, columnProductName);
        shopTableView_Inventory_tbProducts.setItems(gettbProductItems());


    }


    private ObservableList<computerSystem.models.classes.Product> gettbProductItems() {
        ObservableList<computerSystem.models.classes.Product> records = FXCollections.observableArrayList();

        //Update Products class to instanciate with data
        //see the youtube video to add new table values into the data https://www.youtube.com/watch?v=q1LEN2assfU
        records.add(new computerSystem.models.classes.Product());

        return records;
    }

}
