package computerSystem.models.classes;

public class Address {
    private int AddressID = 0;
    private String Postcode = "";
    private String Country = "";
    private String TownCityRegion = "String";
    private String HouseName = ""; //For house number/name
    private Boolean BillingAddress = false;

    public int getAddressID() {return this.AddressID;}
    public String getPostcode() {return this.Postcode;}
    public String getCountry() {return this.Country;}
    public String getTownCityRegion() {return this.TownCityRegion;}
    public String getHouseName() {return this.HouseName;}
    public boolean getBillingAddress() {return this.BillingAddress;}

    public void setPostcode(String _Postcode) {this.Postcode = _Postcode;}
    public void setCountry(String _Country) {this.Country = _Country; }
    public void setTownCityRegion(String _TownCityRegion) {this.TownCityRegion = _TownCityRegion;}
    public void setHouseName(String _HouseName) {this.HouseName = _HouseName;}
    public void setBillingAddress(boolean _BillingAddress) {this.BillingAddress = _BillingAddress;}
}