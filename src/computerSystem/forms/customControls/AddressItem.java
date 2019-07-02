package computerSystem.forms.customControls;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddressItem implements Initializable {

    public static int PrimaryKey = 0;

    @FXML private VBox AddressItemContainer;
    @FXML private TextField addrPostcode;
    @FXML private ChoiceBox addrCountry;
    @FXML private TextField addrTownCityRegion;
    @FXML private TextField addrHouseName;
    @FXML private CheckBox addrBillingAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void implimentDefaultData(computerSystem.models.classes.Address addressData) {
        if(addressData != null) {
            PrimaryKey = addressData.getAddressID();
            addrPostcode.setText(addressData.getPostcode());
            //add choice box here
            addrTownCityRegion.setText(addressData.getTownCityRegion());
            addrHouseName.setText(addressData.getHouseName());
            addrBillingAddress.setSelected(addressData.getBillingAddress());
        }else {
            //Empty data
            PrimaryKey = 420;
        }


    }

    @FXML private void handleDeleteClick(Event ev) {
        ((VBox)AddressItemContainer.getParent()).getChildren().remove(AddressItemContainer);
    }

    public int getID() {
        return PrimaryKey;
    }
}
