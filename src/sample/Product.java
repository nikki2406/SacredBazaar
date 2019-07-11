package sample;

import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Product implements Serializable {
    public int productID;
    public String merchantName;
    public String productName;
    public String brand;
    public int quantity;
    public double actualProductCost;
    public double yourCost;
    public Date expiryDate;
    public Date manufactureDate;
    public String productCategory;
    public String productSubCategory;
    public int discountPercentage;
    public Timestamp discountPeriod;
    public String discountLabel;
    public String productDescription;
    public String productImages;

    public void newProduct(String merchantName, String productName, String brand, int quantity, double actualProductCost, double yourCost, Date expiryDate, Date manufactureDate, String productCategory, String productSubCategory, int discountPercentage, Timestamp discountPeriod, String discountLabel, String productDescription, String productImages) {
        newProduct(0,merchantName,productName,brand,quantity,actualProductCost,yourCost,expiryDate,manufactureDate,productCategory,productSubCategory,discountPercentage,discountPeriod,discountLabel,productDescription,productImages);
    }

    public void newProduct(int productID, String merchantName, String productName, String brand, int quantity, double actualProductCost, double yourCost, Date expiryDate, Date manufactureDate, String productCategory, String productSubCategory, int discountPercentage, Timestamp discountPeriod, String discountLabel, String productDescription, String productImages) {
        this.productID = productID;
        this.merchantName = merchantName;
        this.productName = productName;
        this.brand = brand;
        this.quantity = quantity;
        this.actualProductCost = actualProductCost;
        this.yourCost = yourCost;
        this.expiryDate = expiryDate;
        this.manufactureDate = manufactureDate;
        this.productCategory = productCategory;
        this.productSubCategory = productSubCategory;
        this.discountPercentage = discountPercentage;
        this.discountPeriod = discountPeriod;
        this.discountLabel = discountLabel;
        this.productDescription = productDescription;
        this.productImages = productImages;
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}

