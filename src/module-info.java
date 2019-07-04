module ComputerSalesSystem {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens computerSystem.forms;
    opens computerSystem.forms.accounts;
    opens computerSystem.forms.shop;
    opens computerSystem.forms.customControls;
    opens computerSystem.models;
    opens computerSystem.models.classes;
}