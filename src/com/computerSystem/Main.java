package com.computerSystem;

import com.computerSystem.database.DatabaseSchema;
import com.computerSystem.forms.Master;

public class Main {

    public static com.computerSystem.models.classLocalUser localUser;
    public static com.computerSystem.models.classLocalShop localShop;
    public static String fileLoaderLocation = System.getProperty("user.dir") + "\\localFileHost";

    public static void main(String[] args) {

        //Create new instance of the local user
        localUser = new com.computerSystem.models.classLocalUser();
        localShop = new com.computerSystem.models.classLocalShop();

        //Perform Database Setup and Testing
        if(DatabaseSchema.Connection.testConnection()) {
            DatabaseSchema.setupDatabase();
        }

        //Initiate the UI
        Master.initMaster();
    }
}