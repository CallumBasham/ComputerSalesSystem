package com.computerSystem.models.classes;

import java.util.regex.Pattern;

public class Client {
    private String Title = "";
    private String Forename = "";
    private String Surname = "";

    public void setTitle(String _Title) {this.Title = _Title;}
    public void setForename(String _Forename) {this.Forename = _Forename;}
    public void setSurname(String _Surname) {this.Surname= _Surname;}

    public String getTitle() {return this.Title;}
    public String getForename() {return this.Forename;}
    public String getSurname() {return this.Surname;}

    public void signOut() {
        this.Title = "";
        this.Forename = "";
        this.Surname = "";
    }

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