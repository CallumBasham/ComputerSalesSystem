package computerSystem.forms.shop;

import computerSystem.Main;
import computerSystem.models.classes.Product;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Shop implements Initializable {

    @FXML private VBox shopVBox_Shop_searchFilter;
    @FXML private ChoiceBox shopChoiceBox_Shop_searchFilterCategory;
    @FXML private TextField inventorypProductEdit_TextField_productSearch;

    computerSystem.forms.Master masterController = new computerSystem.forms.Master();


    @FXML private void handleSearchTextChanged(Event ev) {
        shopVBox_Shop_searchFilter.getChildren().clear();
        populateProductInterface(getProductResults(inventorypProductEdit_TextField_productSearch.getText(), (String)shopChoiceBox_Shop_searchFilterCategory.getSelectionModel().getSelectedItem()));
    }

    @FXML private void handleSearchCategoryChanged(Event ev) {
        handleSearchTextChanged(ev);
    }

    private Stream<Product> getProductResults(String searchTerm, String searchCategory){
        if(searchCategory.equals("All")){
            return Main.localShop.localProductsList.stream().filter(x -> x.getProductActive() == true && (x.getProductName().toLowerCase().contains(searchTerm.toLowerCase()) || (x.getProductCategory().toLowerCase().equals(searchTerm.toLowerCase()))));
        } else {
            return Main.localShop.localProductsList.stream().filter(x -> x.getProductActive() == true && x.getProductCategory().toLowerCase().equals(searchCategory.toLowerCase()) && (x.getProductName().toLowerCase().contains(searchTerm.toLowerCase()) || (x.getProductCategory().toLowerCase().equals(searchTerm.toLowerCase()))));
        }
    }

    private void populateProductInterface(Stream<Product> relevantProducts) {
        for (Object i:relevantProducts.toArray()) {
            Product prodI = (Product)i;
            try {
                FXMLLoader instantiateSearchItem = masterController.getCustomControl("shop/SearchProduct.fxml");
                HBox pane  = instantiateSearchItem.load();
                SearchProduct controller = instantiateSearchItem.getController();
                shopVBox_Shop_searchFilter.getChildren().add(pane);
                controller.setup(prodI);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Shop Initialized");
        shopChoiceBox_Shop_searchFilterCategory.getSelectionModel().select("All");
    }
}
