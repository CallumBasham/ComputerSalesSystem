package com.computerSystem.models.classes;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Product  {

    private int ProductID = 0;
    private boolean ProductActive = false;
    private String ProductName = "";
    private File ProductImageFile;
    private Image ProductImage;
    private String ProductDescription = "";
    private double ProductBasePrice = 0.00;
    private String ProductCategory = "";

    public Product() {
        try {this.setProductImage(new Image(new FileInputStream("src/com.computerSystem/forms/content/Icon_Shop.png")));} catch(IOException ex) {System.out.println(ex.getMessage());}
    }

    public Product(int _ProductID, boolean _ProductActive, String _ProductName, String _ProductDescription, double _ProductBasePrice, String _ProductCategory) {
        setProductID(_ProductID);
        try {this.setProductImage(new Image(new FileInputStream("src/com.computerSystem/forms/content/Icon_Shop.png")));} catch(IOException ex) {System.out.println(ex.getMessage());}
        setProductActive(_ProductActive);
        setProductName(_ProductName);
        setProductDescription(_ProductDescription);
        setProductBasePrice(_ProductBasePrice);
        setProductCategory(_ProductCategory);
    }

    public int getProductID() { return this.ProductID; }
    public boolean getProductActive() { return this.ProductActive; }
    public String getProductName() { return this.ProductName; }
    public Image getProductImage() { return this.ProductImage; }
    public File getProductImageFile() { return this.ProductImageFile; }
    public String getProductDescription() { return this.ProductDescription; }
    public double getProductBasePrice() { return this.ProductBasePrice; }
    public String getProductCategory() { return this.ProductCategory; }

    public void setProductID(int _ProductID) { this.ProductID = _ProductID; }
    public void setProductActive(boolean _ProductActive) { this.ProductActive = _ProductActive; }
    public void setProductName(String _ProductName) { this.ProductName = _ProductName; }
    public void setProductImage(Image _ProductImage) { this.ProductImage = _ProductImage; }
    public void setProductImageFile(File _ProductImageFile) { this.ProductImageFile = _ProductImageFile; }
    public void setProductDescription(String _ProductDescription) { this.ProductDescription = _ProductDescription; }
    public void setProductBasePrice(double _ProductBasePrice) { this.ProductBasePrice = _ProductBasePrice; }
    public void setProductCategory(String _ProductCategory) { this.ProductCategory = _ProductCategory; }

}
