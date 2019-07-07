package com.computerSystem.models;

import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.*;

import java.util.ArrayList;
import java.util.List;


public class classLocalUser {

    public  Account userAccount = new Account();

    public Client userClient = new Client();

    public List<Address> userAddresses = new ArrayList<Address>();

    public Card userCards = new Card();

    public List<Order> userOrders = new ArrayList<Order>();

   public classLocalUser() {
       System.out.println("Local User Initialised");
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

   public void updateDatabase_UserAccount() {DatabaseInteraction.StoredProcedures.NonQuery.updateUserAccount(userAccount);}

   public void updateDatabase_UserClient() {DatabaseInteraction.StoredProcedures.NonQuery.updateUserClient(userClient, userAccount.getUserID());}

    public void updateDatabase_UserCard() {DatabaseInteraction.StoredProcedures.NonQuery.updateUserCard(userCards, userAccount.getUserID());}

}
