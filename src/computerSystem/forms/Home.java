package computerSystem.forms;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Home {
    Master master = new Master();
    @FXML
    private void handleClick(Event ev) {
        HBox box = (HBox) ev.getSource();
        if (box.getId().equals("homeAccountBtn")) {
            System.out.println("Home Clicked!");
            master.loadPage("accounts/Account.fxml");
        } else if (box.getId().equals("homeShopBtn")) {
            System.out.println("Shop Clicked!");
            master.loadPage("shop/Shop.fxml");
        }
    }
}
