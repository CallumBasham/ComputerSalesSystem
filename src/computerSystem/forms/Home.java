package computerSystem.forms;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Home {
    @FXML
    private void handleClick(Event ev) {
        HBox box = (HBox) ev.getSource();
        if (box.getId().equals("Sidebar_btnColourMode")) {
            System.out.println("Mode Changed!");
        }
    }
}
