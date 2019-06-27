package computerSystem;

import computerSystem.database.DatabaseSchema;
import computerSystem.forms.Master;

public class Main {

    public static computerSystem.models.classLocalUser localUser;

    public static void main(String[] args) {

        //Create new instance of the local user
        localUser = new computerSystem.models.classLocalUser();

        //Perform Database Setup and Testing
        DatabaseSchema.Connection.testConnection();

        //Initiate the UI
        Master.initMaster();

    }
}