package com.computerSystem.models.classes.productOptionalExtras;

import com.computerSystem.models.classes.Product;

public class Warranty extends Product {

    public String[] getCompiledOptions() {return new String[] {"3 Month Comprehensive: 10.50", "12 Month Comprehensive: 21:99"};}

    public Warranty(Product product){
        super.setProductID(product.getProductID());
        super.setProductActive(product.getProductActive());
        super.setProductName(product.getProductName());
        super.setProductImageFile(product.getProductImageFile());
        super.setProductImage(product.getProductImage());
        super.setProductDescription(product.getProductDescription());
        super.setProductCategory(product.getProductCategory());
    }
}
