package com.computerSystem.models.classes.productOptionalExtras;

import com.computerSystem.models.classes.Product;

public class GiftWrapping extends Product {

    public String[] getCompiledOptions() {return new String[] {"Standard Gift Wrapping: 1.50"};}

    public GiftWrapping(Product product){
        super.setProductID(product.getProductID());
        super.setProductActive(product.getProductActive());
        super.setProductName(product.getProductName());
        super.setProductImageFile(product.getProductImageFile());
        super.setProductImage(product.getProductImage());
        super.setProductDescription(product.getProductDescription());
        super.setProductCategory(product.getProductCategory());
    }

}
