package computerSystem;

import computerSystem.database.DatabaseSchema;
import computerSystem.forms.Master;

public class Main {

    public static void main(String[] args) {

        DatabaseSchema.Connection.testConnection();

        //Launch the JavaFX Master Form (Init Interface)
        Master.initMaster();

    }
}