package com.computerSystem.forms;

import com.computerSystem.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Home {

    com.computerSystem.forms.Master masterController = new com.computerSystem.forms.Master();

    @FXML private void handleRedirectButtonClick(Event ev){
        HBox sentButton = (HBox) ev.getSource();
        switch (sentButton.getId()){
            case"homeBtnAccessibility":

                break;
            case"homeBtnAccount":
                if(!Main.localUser.userAccount.getAuthenticated()){
                    masterController.loadPage("accounts/Login.fxml");
                } else {
                    masterController.loadPage("accounts/Account.fxml");
                }
                break;
            case"homeBtnShop":
                masterController.loadPage("shop/Shop.fxml");
                break;
        }
    }

}
