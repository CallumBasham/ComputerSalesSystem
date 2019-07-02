module ComputerSalesSystem {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens computerSystem.forms;
    opens computerSystem.forms.accounts;
    opens computerSystem.forms.customControls;
}