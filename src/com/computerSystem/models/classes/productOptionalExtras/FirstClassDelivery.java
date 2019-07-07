package com.computerSystem.models.classes.productOptionalExtras;

import com.computerSystem.models.classes.Product;

public class FirstClassDelivery extends Product {

    public String[] getCompiledOptions() {return new String[] {"Next Business Day: 10.50", "Within 5 Business Days: 3.50"};}

    public FirstClassDelivery(Product product){
        super.setProductID(product.getProductID());
        super.setProductActive(product.getProductActive());
        super.setProductName(product.getProductName());
        super.setProductImageFile(product.getProductImageFile());
        super.setProductImage(product.getProductImage());
        super.setProductDescription(product.getProductDescription());
        super.setProductCategory(product.getProductCategory());
    }

}
