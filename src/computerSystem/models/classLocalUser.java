package computerSystem.models;

import java.util.Date;
import java.util.regex.Pattern;

public class classLocalUser {

    public  Account userAccount = new Account();

    public Client userClient = new Client();

    public Address[] userAddresses = new Address[] {};

    public Cards[] userCards = new Cards[] {};

   public classLocalUser() {
   }

    public class Account {
        private String Username = "";
        //Password is only used at runtime in communication with the Database, once authenticated it will not need to be accessed except for changing password
        private String Email = "";
        private String PhoneNumber = "";
        private String Picture = "";
        private Boolean IsAdmin = false;
        private Boolean CanContact = false;
        private Boolean Autnenticated = false;

        public String verifyUsernameFormat(String _Username){
            String errorMsg = "";
            Pattern alphanumericRegex = Pattern.compile("^[a-zA-Z0-9]+$");

            if(_Username.length() < 5) {
                errorMsg += "Username must be more or equal to 5 characters long\n";
            }
            if(_Username.length() > 25) {
                errorMsg += "Username must be less than or equal to 25 characters long\n";
            }
            if(!alphanumericRegex.matcher(_Username).matches()) {
                errorMsg += "Username must be alpha numeric (e.g. no special characters, only letters and numbers)\n";
            }

            return errorMsg;
        }

        public String verifyEmailFormat(String _Email){
            String errorMsg = "";

            if(_Email.length() < 5) {
                errorMsg += "Email must be more or equal to 5 characters long\n";
            }
            if(_Email.length() > 100) {
                errorMsg += "Username must be less than or equal to 100 characters long\n";
            }
            if(!_Email.contains("@")) {
                errorMsg += "Is not valid: Missing @ Symbol\n";
            }
            if(!_Email.contains(".")) {
                errorMsg += "Is not valid: Missing . Symbol\n";
            }

            return errorMsg;
        }

        public String verifyPhoneFormat(String _PhoneNumber){
            String errorMsg = "";
            //Pattern phonenumberRegex = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

            if(_PhoneNumber.length() < 6) {
                errorMsg += "Phone Number must be more or equal to 6 characters long\n";
            }
            if(_PhoneNumber.length() > 50) {
                errorMsg += "Phone Number must be less than or equal to 50 characters long\n";
            }
            /*if(!phonenumberRegex.matcher(_PhoneNumber).matches()) {
                errorMsg += "Phone Number must be in a valid format e.g. 07420 772756\n";
            }*/

            return errorMsg;
        }

        public String verifyPasswordFormat(String _Password) {
            String errorMsg = "";

            if(_Password.length() < 5) {
                errorMsg += "Password must be more or equal to 5 characters long\n";
            }
            if(_Password.length() > 35) {
                errorMsg += "Password must be less than or equal to 35 characters long\n";
            }

            return errorMsg;
        }
    }

    public class Client {
        private String Title = "";
        private String Forename = "";
        private String Surname = "";

        public String verifyForenameFormat(String _Forename){
            String errorMsg = "";
            Pattern alphabeticRegex = Pattern.compile("^[\\p{L} .'-]+$");

            if(_Forename.length() < 2) {
                errorMsg += "Forename must be more or equal to 2 characters long\n";
            }
            if(_Forename.length() > 50) {
                errorMsg += "Forename must be less than or equal to 50 characters long\n";
            }
            if(!alphabeticRegex.matcher(_Forename).matches()) {
                errorMsg += "Forename must be in a valid UK format\n";
            }

            return errorMsg;
        }

        public String verifySurnameFormat(String _Surname){
            String errorMsg = "";
            Pattern alphabeticRegex = Pattern.compile("^[\\p{L} .'-]+$");

            if(_Surname.length() < 2) {
                errorMsg += "Surname must be more or equal to 2 characters long\n";
            }
            if(_Surname.length() > 50) {
                errorMsg += "Surname must be less than or equal to 50 characters long\n";
            }
            if(!alphabeticRegex.matcher(_Surname).matches()) {
                errorMsg += "Surname must be in a valid UK format\n";
            }

            return errorMsg;
        }

    }

    public class Address {
        private String Postcode = "";
        private String Country = "";
        private String TownCityRegion = "String";
        private String HouseName = ""; //For house number/name
        private Boolean BillingAddress = false;
    }

    public class Cards {
        private String CardNumber = "";
        private Date ExpiryDate = new Date();
    }
}



