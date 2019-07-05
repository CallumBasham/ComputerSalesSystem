module ComputerSalesSystem {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;

    opens computerSystem.forms;
    opens computerSystem.forms.accounts;
    opens computerSystem.forms.shop;
    opens computerSystem.forms.customControls;
    opens computerSystem.models;
    opens computerSystem.models.classes;
}