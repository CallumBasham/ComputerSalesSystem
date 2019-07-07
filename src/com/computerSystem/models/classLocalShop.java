package com.computerSystem.models;

import com.computerSystem.database.DatabaseInteraction;
import com.computerSystem.models.classes.Product;

import java.util.ArrayList;
import java.util.List;

public class classLocalShop {

        public List<Product> localProductsList = new ArrayList<Product>();

        public void populateTestDataSet() {
            localProductsList.add(new Product(0, true, "Razer Huntsman", "Mouse", 420.69, "A"));
            localProductsList.add(new Product(1, true, "Razer Blackwidow", "Keyboard", 435.69, "A"));
            localProductsList.add(new Product(2, true, "Razer Blackwidow Ultimate", "Keyboard", 500, "B"));
            localProductsList.add(new Product(3, true, "Razer Blackwidow Chroma", "Keyboard", 460.69, "C"));
            localProductsList.add(new Product(4, true, "Razer Kracken", "Headphone", 250, "C"));
        }

        public void importShopData() {
            DatabaseInteraction.StoredProcedures.Tabular.getShopDetails();
        }
}
