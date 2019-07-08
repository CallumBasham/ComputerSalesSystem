package com.computerSystem.models.classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.util.regex.Pattern;

public class Account {
    private int UserID = 0;
    private String Username = "";
    //Password is only used at runtime in communication with the Database, once authenticated it will not need to be accessed except for changing password
    private boolean AccountType = false; //true = admin | false = user
    private String Email = "";
    private String PhoneNumber = "";
    private boolean CanContact = false;
    private File UserImageFile;
    private Image UserImage;
    private Boolean Authenticated = false; //Used as the secure replacement for password

    public Account() {}

    public Account(int _UserID, String _Username, boolean _AccountType, String _Email, String _PhoneNumber, boolean _CanContact) {
        setUserID(_UserID);
        setUsername(_Username);
        setAccountType(_AccountType);
        setEmail(_Email);
        setPhone(_PhoneNumber);
        setCanContact(_CanContact);
    }

    public void signOut() {
        this.UserID = 0;
        this.Username = "";
        this.AccountType = false;
        this.Email = "";
        this.PhoneNumber = "";
        this.UserImageFile = null;
        this.UserImage = null;
        this.CanContact = false;
        this.Authenticated = false;
    }

    public void setUserID(int _UserID) {
        this.UserID = _UserID;
    }
    public int getUserID() { return this.UserID; }

    public void setUsername(String _Username) {
        this.Username = _Username;
    }
    public String getUsername() { return this.Username; }

    public boolean getAccountType() {return this.AccountType; }

    public void setEmail(String _Email) {
        this.Email = _Email;
    }
    public String getEmail() {return this.Email;}

    public void setPhone(String _PhoneNumber) {
        this.PhoneNumber = _PhoneNumber;
    }
    public String getPhone() {return this.PhoneNumber;}

    public void setUserImageFile(File _ImageFile) {this.UserImageFile = _ImageFile; }
    public File getUserImageFile() {
        return this.UserImageFile;
    }

    public void setUserImage(Image _Image) {this.UserImage = _Image; }
    public Image getUserImage() {
        return this.UserImage;
    }

    public void setCanContact(boolean _CanContact) {this.CanContact = _CanContact; }
    public boolean getCanContact() {
        return this.CanContact;
    }

    public void setAccountType(boolean _Type) {
        this.AccountType = _Type;
    }

    public void setAuthenticated(boolean _Authenticated) {
        this.Authenticated = _Authenticated;
    }
    public boolean getAuthenticated() { return this.Authenticated; }

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