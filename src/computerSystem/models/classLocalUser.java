package computerSystem.models;

import computerSystem.database.DatabaseInteraction;
import computerSystem.models.classes.Account;
import computerSystem.models.classes.Address;
import computerSystem.models.classes.Cards;
import computerSystem.models.classes.Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class classLocalUser {

    public  Account userAccount = new Account();

    public Client userClient = new Client();

    public List<Address> userAddresses = new ArrayList<Address>();

    public List<Cards> userCards = new ArrayList<Cards>();

   public classLocalUser() {
       System.out.println("Local User Initilized");
   }

   public void populateDetails(){
       if(userAccount.getAuthenticated()){
           DatabaseInteraction.StoredProcedures.Tabular.getUserDetails();
       }
   }

   public void signOut() {
       userAccount.signOut();
       userClient.signOut();
       userAddresses =  new ArrayList<Address>();
       userCards = new ArrayList<Cards>();
   }

}
