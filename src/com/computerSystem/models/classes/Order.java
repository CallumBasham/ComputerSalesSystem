package com.computerSystem.models.classes;

import com.computerSystem.database.DatabaseInteraction;

public class Order  {

    private int OrderID = 0;
    private int UserID = 0;
    private int ProductID = 0;

    private int OrderQuantity = 0;
    private double OrderTotalPrice = 0.0;
    private String OrderExtras = "";

    public int getOrderID() { return this.OrderID; }
    public int getUserID() { return this.UserID; }
    public int getProductID() { return this.ProductID; }
    public int getOrderQuantity() { return this.OrderQuantity; }
    public double getOrderTotalPrice() { return this.OrderTotalPrice; }
    public String getOrderExtras() { return this.OrderExtras; }

    public void setOrderID(int _OrderID) { this.OrderID = _OrderID; }
    public void setUserID(int _UserID) { this.UserID = _UserID; }
    public void setProductID(int _ProductID) { this.ProductID = _ProductID; }
    public void setOrderQuantity(int _OrderQuantity) { this.OrderQuantity = _OrderQuantity; }
    public void setOrderTotalPrice(double _OrderTotalPrice) { this.OrderTotalPrice = _OrderTotalPrice; }
    public void setOrderExtras(String _OrderExtras) { this.OrderExtras = _OrderExtras; }

    public void saveOrder() {
        DatabaseInteraction.StoredProcedures.NonQuery.postNewOrder(this);
    }
}
