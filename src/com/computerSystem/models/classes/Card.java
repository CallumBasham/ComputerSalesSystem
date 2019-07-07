package com.computerSystem.models.classes;

import java.util.regex.Pattern;

public class Card {
    private String CardNumber = "";
    private int ExpiryMonth = 0;
    private int ExpiryYear = 0;
    private String CardType = "";

    public void setCardNumber(String _CardNumber) { this.CardNumber = _CardNumber; }
    public void setExpiryMonth(int _ExpiryMonth) { this.ExpiryMonth = _ExpiryMonth; }
    public void setExpiryYear(int _ExpiryYear) { this.ExpiryYear = _ExpiryYear; }
    public void setCardType(String _CardType) { this.CardType = _CardType; }

    public String getCardNumber(){ return this.CardNumber; }
    public Integer getExpiryYear(){ return this.ExpiryYear; }
    public Integer getExpiryMonth(){ return this.ExpiryMonth; }

    public void signOut() {
        setCardNumber("");
        setExpiryMonth(0);
        setExpiryYear(0);
        setCardType("");
    }

    public void resolveCardType(){
        String cardNumber = getCardNumber().replace(" ", "");
        Pattern Visa = Pattern.compile("^4[0-9]{6,}$");
        Pattern MasterCard = Pattern.compile("^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$");
        Pattern AmericanExpress = Pattern.compile("^3[47][0-9]{5,}$");
        Pattern DinersClub = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$");
        Pattern Discover = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{3,}$");
        Pattern JCB = Pattern.compile("^(?:2131|1800|35[0-9]{3})[0-9]{3,}$");

        if(Visa.matcher(cardNumber).matches()) {
            setCardType("Visa");
        } else
        if(MasterCard.matcher(cardNumber).matches()) {
            setCardType("MasterCard");
        } else
        if(AmericanExpress.matcher(cardNumber).matches()) {
            setCardType("American Express");
        } else
        if(DinersClub.matcher(cardNumber).matches()) {
            setCardType("Diners Club");
        } else
        if(Discover.matcher(cardNumber).matches()) {
            setCardType("Discover");
        } else
        if(JCB.matcher(cardNumber).matches()) {
            setCardType("JCB");
        } else {
            setCardType("Other");
        }
    }
}