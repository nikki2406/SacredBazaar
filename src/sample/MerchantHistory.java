package sample;

import java.io.Serializable;
import java.sql.Date;

public class MerchantHistory implements Serializable {
    public Product product;
    String recieverName;
    String buyerUserName;
    int quantity;
    String deliveryAddress;

    void addHistory(int productID, String recieverName, String buyerUserName, String productName, String brand, int quantity, String deliveryAddress, double actualProductCost, double boughtCost, Date expiryDate, Date manufactureDate, String productCategory, String productSubCategory, int discountPercentage, String discountLabel, String productDescription, String productImage) {
        this.product = new Product();
        this.product.newProduct(productID, "#",productName,brand,quantity,actualProductCost,boughtCost,expiryDate,manufactureDate,productCategory,productSubCategory,discountPercentage,null,discountLabel,productDescription,productImage);
        this.recieverName = recieverName;
        this.buyerUserName = buyerUserName;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
    }

    public void addHistory(Product product, String recieverName, String buyerUserName, int quantity, String deliveryAddress) {
        this.product = product;
        this.recieverName = recieverName;
        this.buyerUserName = buyerUserName;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
    }

    public String getBuyerUserName(){
        return buyerUserName;
    }

    public int getQuantity(){
        return quantity;
    }

}
