package computerSystem.models;

import computerSystem.database.DatabaseInteraction;
import computerSystem.models.classes.Account;
import computerSystem.models.classes.Address;
import computerSystem.models.classes.Card;
import computerSystem.models.classes.Card;
import computerSystem.models.classes.Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class classLocalUser {

    public  Account userAccount = new Account();

    public Client userClient = new Client();

    public List<Address> userAddresses = new ArrayList<Address>();

    public Card userCards = new Card();

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
       userCards.signOut();
   }

   public void updateDatabase_userAddresses() {
       for (Address addr:userAddresses) {
            if(DatabaseInteraction.StoredProcedures.Scalar.isAddressDuplicate(addr)){
                DatabaseInteraction.StoredProcedures.NonQuery.updateAddress(addr);
            } else {
                DatabaseInteraction.StoredProcedures.NonQuery.postNewAddress(addr);
            }
       }
   }

}
