package com.computerSystem;

import com.computerSystem.database.DatabaseSchema;
import com.computerSystem.forms.Master;

import java.io.File;

public class Main {

    public static com.computerSystem.models.classLocalUser localUser;
    public static com.computerSystem.models.classLocalShop localShop;
    //public static String fileLoaderLocation = System.getProperty("user.dir") + "\\localFileHost";
    public static String fileLoaderLocation = "";
    public static String databaseLocation = "";

    public static void main(String[] args) {

        //Create new instance of the local user
        localUser = new com.computerSystem.models.classLocalUser();
        localShop = new com.computerSystem.models.classLocalShop();
        try {
            fileLoaderLocation = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent() + "\\localFileHost";
            databaseLocation = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (Exception ex) {
            System.exit(0);
        }


        //Perform Database Setup and Testing
        if(DatabaseSchema.Connection.testConnection()) {
            DatabaseSchema.setupDatabase();
        }

        //Initiate the UI
        Master.initMaster();
    }


}