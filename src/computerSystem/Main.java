package computerSystem;

import computerSystem.database.DatabaseSchema;
import computerSystem.forms.Master;
import computerSystem.models.classes.Product;

public class Main {

    public static computerSystem.models.classLocalUser localUser;
    public static computerSystem.models.classLocalShop localShop;
    public static computerSystem.models.classLocalOrders localOrder;

    public static void main(String[] args) {

        //Create new instance of the local user
        localUser = new computerSystem.models.classLocalUser();
        localShop = new computerSystem.models.classLocalShop();
        localOrder = new computerSystem.models.classLocalOrders();

        //Perform Database Setup and Testing
        if(DatabaseSchema.Connection.testConnection()) {
            DatabaseSchema.setupDatabase();
        }

        //Initiate the UI
        Master.initMaster();
    }
}