package computerSystem.forms.shop;

import computerSystem.models.classes.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SearchProduct {

    @FXML private ImageView searchProduct_ImageView_proejctImage;
    @FXML private Button searchProduct_Button_viewProduct;
    @FXML private Label searchProduct_Label_productName;
    @FXML private Label searchProduct_Label_productBasePrice;
    @FXML private Label searchProduct_Label_productCategory;

    public void setup(Product product) {
        searchProduct_ImageView_proejctImage.setImage(product.getProductImage());

        searchProduct_Label_productName.setText(product.getProductName());
        searchProduct_Label_productBasePrice.setText(String.valueOf(product.getProductBasePrice()));
        searchProduct_Label_productCategory.setText(product.getProductCategory());
    }

    //more
}
