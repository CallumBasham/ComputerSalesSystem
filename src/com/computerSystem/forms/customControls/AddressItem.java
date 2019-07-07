package com.computerSystem.forms.customControls;

import com.computerSystem.Main;
import com.computerSystem.database.DatabaseInteraction;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddressItem implements Initializable {

    private int AddressID = 0;

    @FXML private VBox AddressItemContainer;
    @FXML private TextField addrPostcode;
    @FXML private ChoiceBox addrCountry;
    @FXML private TextField addrTownCityRegion;
    @FXML private TextField addrHouseName;
    @FXML private CheckBox addrBillingAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void implimentDefaultData(com.computerSystem.models.classes.Address addressData, int AddressIDIncrementer) {
        addrCountry.getItems().addAll(DatabaseInteraction.StoredProcedures.Tabular.getAllCountries());
        if(addressData != null) {
            AddressID = addressData.getAddressID();
            addrPostcode.setText(addressData.getPostcode());
            addrCountry.setValue(addressData.getCountry());
            addrTownCityRegion.setText(addressData.getTownCityRegion());
            addrHouseName.setText(addressData.getHouseName());
            addrBillingAddress.setSelected(addressData.getBillingAddress());
            System.out.println("AddressItem.fxml started with existing ID of " + AddressID);
        }else {
            //Empty data
            AddressID = DatabaseInteraction.StoredProcedures.Scalar.getNextMaxAddressID() + AddressIDIncrementer;
            System.out.println("AddressItem.fxml started with new ID of " + AddressID);
        }
    }

    @FXML private void handleDeleteClick(Event ev) {
        System.out.println("AddressItem.fxml removing userAddresses with the ID of " + AddressID);
        Main.localUser.userAddresses.removeIf(addr -> addr.getAddressID() == this.AddressID);
        DatabaseInteraction.StoredProcedures.NonQuery.deleteAddress(this.AddressID);
        ((VBox)AddressItemContainer.getParent()).getChildren().remove(AddressItemContainer);
        AddressItemContainer = null;
    }

    public int getAddressID() {
        return AddressID;
    }
    public String getPostcode() {
        return addrPostcode.getText();
    }
    public String getCountry() {
        return (String)addrCountry.getValue();
    }
    public String getTownCityRegion() {
        return addrTownCityRegion.getText();
    }
    public String getHouseName() {
        return addrHouseName.getText();
    }
    public boolean getBillingAddress() {
        return addrBillingAddress.isSelected();
    }
}
