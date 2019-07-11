package sample.Network;

import javafx.fxml.Initializable;
import sample.*;

import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * <h1>DatabaseManager class</h1>
 * <p>DatabaseManager class to handle database related methods.</p>
 *
 * @version 1.0
 * @since 2018-09-02
 */
class DatabaseManager {

    private String USERNAME;
    private String PASSWORD;
    private String CONNECTIONURL = "jdbc:mysql://localhost/avishkar2k18?useSSL=false";
    private Connection dbconnection;
    private PreparedStatement dbstatement;

    /**
     * Default constructor having default password
     */
    DatabaseManager() {
        USERNAME = "root";
        PASSWORD = "root";
    }

    /**
     * Constructor for storing USERNAME and PASSWORD
     *
     * @param USERNAME username for connecting with database at CONNECTIONURL
     * @param PASSWORD password for connecting with database at CONNECTIONURL
     */
    DatabaseManager(String USERNAME, String PASSWORD) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        try {
            dbconnection = DriverManager.getConnection(USERNAME,PASSWORD,CONNECTIONURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create user cart table for it's cart information
     * insert user information in userdata table
     *
     * @param userInfo has user information that is to be inserted
     * @return true if user is created successfully else false
     */
    boolean createUser(UserInfo userInfo) {
        try {
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "INSERT INTO `userdata` (`UserName`,`Password`,`FirstName`,`LastName`,`Gender`,`AddressLine`,`DOB`,`AccountCreatedOn`,`PhoneNumber`,`Email`,`Last5Search`,`IsMerchant`,`IsRoot`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            //todo make query injection free (;,",', drop, insert, ...etc)
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1, userInfo.userName);
            dbstatement.setString(2, userInfo.passwd);
            dbstatement.setString(3, userInfo.firstName);
            dbstatement.setString(4, userInfo.lastName);
            dbstatement.setString(5, userInfo.gender);
            dbstatement.setString(6, userInfo.addressLine1 + ":" + userInfo.addressLine2 + ":" + userInfo.addressLine3);
            dbstatement.setDate(7, userInfo.DOB);
            dbstatement.setTimestamp(8, userInfo.timestamp);
            dbstatement.setString(9, userInfo.phoneNumber);
            dbstatement.setString(10, userInfo.email);
            dbstatement.setString(11, ":");
            dbstatement.setInt(12, userInfo.isMerchant);
            dbstatement.setInt(13, userInfo.isRoot);
            dbstatement.executeUpdate();
            String createQuery = "CREATE TABLE " + userInfo.userName + "_cart ( purchaseID INT NOT NULL AUTO_INCREMENT, ProductPurchaseDate DATE NOT NULL, EstimatedDeliveryDate DATE NOT NULL, ActualDeliveryDate DATE NOT NULL, DeliveryAddress VARCHAR(255) NOT NULL, SellingPrice double NOT NULL, PurchasePrice double NOT NULL, ShippingCharges double NOT NULL, Quantity int NOT NULL, Retailer_UserName VARCHAR(255) NOT NULL, PaymentMethod CHAR(255) NOT NULL, OfferName CHAR(255) NOT NULL, IsDelivered int(3) NOT NULL, PRIMARY Key( PurchaseID ));";
            dbstatement = dbconnection.prepareStatement(createQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.executeUpdate();
            String createNotificationQuery = "CREATE TABLE " + userInfo.userName + "_notification ( NotificationID INT NOT NULL AUTO_INCREMENT, Notification VARCHAR(255) NOT NULL, PRIMARY Key( NotificationID ));";
            dbstatement = dbconnection.prepareStatement(createNotificationQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean editUser(UserInfo userInfo) {
        try {
            String deleteQuery = "select";
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "INSERT INTO `userdata` (`UserName`,`Password`,`FirstName`,`LastName`,`Gender`,`AddressLine`,`DOB`,`AccountCreatedOn`,`PhoneNumber`,`Email`,`Last5Search`,`IsMerchant`,`IsRoot`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            //todo make query injection free (;,",', drop, insert, ...etc)
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1, userInfo.userName);
            dbstatement.setString(2, userInfo.passwd);
            dbstatement.setString(3, userInfo.firstName);
            dbstatement.setString(4, userInfo.lastName);
            dbstatement.setString(5, userInfo.gender);
            dbstatement.setString(6, userInfo.addressLine1 + ":" + userInfo.addressLine2 + ":" + userInfo.addressLine3);
            dbstatement.setDate(7, userInfo.DOB);
            dbstatement.setTimestamp(8, userInfo.timestamp);
            dbstatement.setString(9, userInfo.phoneNumber);
            dbstatement.setString(10, userInfo.email);
            dbstatement.setString(11, ":");
            dbstatement.setInt(12, userInfo.isMerchant);
            dbstatement.setInt(13, userInfo.isRoot);
            dbstatement.executeUpdate();
            String createQuery = "CREATE TABLE " + userInfo.userName + "_cart ( purchaseID INT NOT NULL AUTO_INCREMENT, ProductPurchaseDate DATE NOT NULL, EstimatedDeliveryDate DATE NOT NULL, ActualDeliveryDate DATE NOT NULL, DeliveryAddress VARCHAR(255) NOT NULL, SellingPrice double NOT NULL, PurchasePrice double NOT NULL, ShippingCharges double NOT NULL, Quantity int NOT NULL, Retailer_UserName VARCHAR(255) NOT NULL, PaymentMethod CHAR(255) NOT NULL, OfferName CHAR(255) NOT NULL, IsDelivered int(3) NOT NULL, PRIMARY Key( PurchaseID ));";
            dbstatement = dbconnection.prepareStatement(createQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.executeUpdate();
            String createNotificationQuery = "CREATE TABLE " + userInfo.userName + "_notification ( NotificationID INT NOT NULL AUTO_INCREMENT, Notification VARCHAR(255) NOT NULL, PRIMARY Key( NotificationID ));";
            dbstatement = dbconnection.prepareStatement(createNotificationQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Execute query
     *
     * @param query that is to be executed
     * @return resultSet of query
     * @throws SQLException when there is an error in syntax.
     */
    //remember that it will throw SQLException
    private ResultSet Query(String query) throws SQLException {
        ResultSet resultSet = null;
        dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
        dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = dbstatement.executeQuery();
        return resultSet;
    }

    private Product generateProduct(ResultSet resultSet) throws SQLException {
        if(resultSet == null){
            return null;
        }
        Product product = new Product();
        product.newProduct(resultSet.getInt("ProductID"),
                resultSet.getString("MerchantUserName"),
                resultSet.getString("ProductName"),
                resultSet.getString("Brand"),
                resultSet.getInt("Quantity"),
                resultSet.getDouble("ActualProductCost"),
                resultSet.getDouble("YourCost"),
                resultSet.getDate("ExpiryDate"),
                resultSet.getDate("ManufactureDate"),
                resultSet.getString("ProductCategory"),
                resultSet.getString("ProductSubCategory"),
                resultSet.getInt("DiscountPercentage"),
                resultSet.getTimestamp("DiscountPeriod"),
                resultSet.getString("DiscountLabel"),
                resultSet.getString("ProductDescription"),
                resultSet.getString("ProductImages"));
        return product;
    }

    /**
     * Generates product list from query
     *
     * @param query query that is to be executed for selecting products
     * @return product list of type {@code List<Product>}
     */
    private List<Product> getProducts(String query) {
        ResultSet resultSet;
        List<Product> productList = new ArrayList<Product>();
        try {
            resultSet = Query(query);
            while (resultSet.next()) {
                productList.add(generateProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * Search for products having substring 'key'
     *
     * @param key         substring that is to be search in product name or category
     * @param isAscending is true to get products in ascending order by selling cost
     * @return list of products that are found having key in their name or category
     */
    //todo defination name change from searchProduct to ..
    List<Product> searchProductOrderByPrice(String key, boolean isAscending) {
        //todo use split list to add more products in search than comment resultSet in catch block
        //todo add category search feature
        List<Product> productList = new ArrayList<Product>();
//        String[] split = key.split("\\s+");
        String orderBy = (isAscending) ? "ASC" : "DESC";
        String query = "SELECT * FROM MerchantProducts WHERE ProductName LIKE CONCAT('%', '" + key + "', '%') ORDER BY YourCost " + orderBy + ";";
        productList = getProducts(query);
        return productList;
    }

    List<Product> searchProductOrderByName(String key, boolean isAscending) {
        //todo use split list to add more products in search than comment resultSet in catch block
        //todo add category search feature
        List<Product> productList = new ArrayList<Product>();
//        String[] split = key.split("\\s+");
        String orderBy = (isAscending) ? "ASC" : "DESC";
        String query = "SELECT * FROM MerchantProducts WHERE ProductName LIKE CONCAT('%', '" + key + "', '%') ORDER BY ProductName " + orderBy + ";";
        productList = getProducts(query);
        return productList;
    }

    List<Product> searchProduct(String key) {
        List<Product> productList = new ArrayList<Product>();
        //todo add split
//        String[] split = key.split("\\s+");
        String query = "SELECT * FROM MerchantProducts WHERE ProductName LIKE CONCAT('%', '" + key + "', '%') ;";
        productList = getProducts(query);
        return productList;
    }

    /**
     * Search product of specified category.
     *
     * @param category It is the category of products that is to be returned.
     * @return list of products of specified category.
     */
    List<Product> getProductOfCategory(String category) {
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM MerchantProducts WHERE ProductCategory = '" + category + "';";
        productList = getProducts(query);
        return productList;
    }

    List<Product> getProductOfCategoryOrderByPrice(String category, boolean isAscending) {
        List<Product> productList = new ArrayList<Product>();
        String orderBy = (isAscending) ? "ASC" : "DESC";
        String query = "SELECT * FROM MerchantProducts WHERE ProductCategory = '" + category + "' ORDER BY YourCost " + orderBy + ";";
        productList = getProducts(query);
        return productList;
    }

    List<Product> getProductOfCategoryOrderByName(String category, boolean isAscending) {
        List<Product> productList = new ArrayList<Product>();
        String orderBy = (isAscending) ? "ASC" : "DESC";
        String query = "SELECT * FROM MerchantProducts WHERE ProductCategory = '" + category + "' ORDER BY ProductName " + orderBy + ";";
        productList = getProducts(query);
        return productList;
    }

    List<Product> getProductOfSubCategory(String category, String subCategory) {
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM MerchantProducts WHERE ProductCategory = '" + category + "' and ProductSubCategory = '"+subCategory+"';";
        productList = getProducts(query);
        return productList;
    }

    List<Product> getProductOfSubCategoryOrderByPrice(String category, String subCategory, boolean isAscending) {
        List<Product> productList = new ArrayList<Product>();
        String orderBy = (isAscending) ? "ASC" : "DESC";
        String query = "SELECT * FROM MerchantProducts WHERE ProductCategory = '" + category + "' and ProductSubCategory = '"+subCategory+"' ORDER BY YourCost " + orderBy + ";";
        productList = getProducts(query);
        return productList;
    }

    List<Product> getProductOfSubCategoryOrderByName(String category, String subCategory, boolean isAscending) {
        List<Product> productList = new ArrayList<Product>();
        String orderBy = (isAscending) ? "ASC" : "DESC";
        String query = "SELECT * FROM MerchantProducts WHERE ProductCategory = '" + category + "' and ProductSubCategory = '"+subCategory+"' ORDER BY ProductName " + orderBy + ";";
        productList = getProducts(query);
        return productList;
    }

    /**
     * Generate all categories
     *
     * @return all categories in{@code List<String> } format
     */
    List<String> getCategory() {
        ResultSet resultSet;
        List<String> categories = new ArrayList<String>();
        String query = "SELECT `ProductCategory` from merchantproducts group by `ProductCategory`;";
        try {
            resultSet = Query(query);
            while (resultSet.next()) {
                categories.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Generates list of subcategories
     * i.e. for category 1 list 1 is having sub categories under it
     * for category2 list 2 is having sub categories under it and so on.
     *
     * @return {@code List<List<String>>}
     */
    List<List<String>> getSubCategory() {
        ResultSet resultSet = null;
        List<List<String>> subCategoryList = new ArrayList<>();
        List<String> categories = getCategory();
        Iterator iterator = categories.iterator();
        while (iterator.hasNext()) {
            List<String> tempSubCategories = new ArrayList<String>();
            String category = (String) iterator.next();
            String query = "SELECT * from merchantproducts where `ProductCategory` = '" + category + "' group by `ProductSubCategory`;";
            try {
                resultSet = Query(query);
                int a;
                while (resultSet.next()) {
                    tempSubCategories.add(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            subCategoryList.add(tempSubCategories);
        }
        return subCategoryList;
    }

    /**
     * Search for products between specified range
     *
     * @param min minimum cost of product
     * @param max maximum cost of product
     * @return list of products of format {@code List<Product>}
     */
    List<Product> getProductBetween(int min, int max) {
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * from merchantproducts where `ActualProductCost` >= " + min + " and `ActualProductCost` <= " + max + ";";
        productList = getProducts(query);
        return productList;
    }

    /**
     * add product to user cart
     *
     * @param product product that is to be added
     * @param userName  username of user
     * @return true if product is added else false
     */
    boolean addtocart(Product product, String userName) {
        try {
            String cartQuery = "INSERT INTO `"+userName+"_cart` (ProductPurchaseDate`,`EstimatedDeliveryDate`,`ActualDeliveryDate`,`DeliveryAddress`,`SellingPrice`,`PurchasePrice`,`ShippingCharges`,`Quantity`,`Retailer_UserName`,`PaymentMethod`,`OfferName`,`IsDelivered`) VALUES ("+java.sql.Date.valueOf(LocalDate.of(1991, 06, 22))+", "+java.sql.Date.valueOf(LocalDate.of(1991, 06, 22))+", "+java.sql.Date.valueOf(LocalDate.of(1991, 06, 22))+", "+"::"+", "+product.actualProductCost+", "+product.yourCost+", "+0+", "+0+", "+product.merchantName+", "+"#"+", "+product.discountLabel+", "+0+");";
            //todo make query injection free (;,",', drop, insert, ...etc)
            Query(cartQuery);
//            dbstatement = dbconnection.prepareStatement(cartQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            dbstatement.setDate(1, java.sql.Date.valueOf(LocalDate.of(1991, 06, 22)));
//            dbstatement.setDate(2, java.sql.Date.valueOf(LocalDate.of(1991, 06, 22)));
//            dbstatement.setDate(3, java.sql.Date.valueOf(LocalDate.of(1991, 06, 22)));
//            dbstatement.setString(4, "::");
//            dbstatement.setDouble(5, product.actualProductCost);
//            dbstatement.setDouble(6, product.yourCost);
//            dbstatement.setDouble(7, 0);
//            dbstatement.setInt(8, 0);
//            dbstatement.setString(9, product.merchantName);
//            dbstatement.setString(10, "#");
//            dbstatement.setString(11, product.discountLabel);
//            dbstatement.setInt(12, 0);
//            dbstatement.executeUpdate();
//            dbstatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //todo remove this redundant code by getting min and max values from list that user got at client side

    /**
     * Get minimum and maximum range of price
     *
     * @return colon separated minimum and maximum value of cost
     */
    String getRange() {
        //todo add category as condition
        ResultSet resultSet;
        int min = 0, max = 999999999;
        try {
            String query = "select max(`ActualProductCost`) from merchantproducts;";
            resultSet = Query(query);
            while (resultSet.next()) {
                min = resultSet.getInt(0);
            }
            query = "select min(`ActualProductCost`) from merchantproducts;";
            resultSet = Query(query);
            while (resultSet.next()) {
                max = resultSet.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return min + ":" + max;
    }

    /**
     * Retrieve user info having username and password as given in parameters
     *
     * @param Username username of user
     * @param Passwd   password of user sha256 encrypted
     * @return user information
     */
    UserInfo getUserInfo(String Username, String Passwd) {
        ResultSet resultSet;
        UserInfo userInfo = null;
        String query = "SELECT * FROM UserData WHERE UserName = '" + Username + "' AND Password = '" + Passwd + "';";
        try {
            resultSet = Query(query);
            if (resultSet.next()) {
                userInfo = new UserInfo();
                String[] addressLines = resultSet.getString("AddressLine").split(":");
                //todo make address better
                if (addressLines.length != 3) {
                    addressLines[0] = "#";
                    addressLines[1] = "#";
                    addressLines[2] = "#";
                }
                userInfo.newUser(resultSet.getString("UserName"),
                        "#",
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Gender"),
                        addressLines[0],
                        addressLines[1],
                        addressLines[2],
                        resultSet.getDate("DOB"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getInt("isMerchant"),
                        resultSet.getInt("isRoot"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * checks for user having entry in userdata table for login
     *
     * @param userName username of user
     * @param Passwd   password of user sha256 encrypted
     * @return true if username and password are correct else false for wrong information or information doesn't exist
     */
    boolean loginUser(String userName, String Passwd) {
        //todo make better user credential checks
        String query = "SELECT * FROM userdata WHERE UserName = '" + userName + "' AND Password = '" + Passwd + "';";
        ResultSet resultSet = null;
        try {
            resultSet = Query(query);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * create merchant using merchant information stored in merchantInfo object reference of MerchantInformation
     *
     * @param merchantInfo information of merchant that is to be inserted in merchant data table
     * @return true if merchant is created else false
     */
    boolean createMerchant(MerchantInfo merchantInfo) {
        try {
            //todo create {userdata}.isMerchant = 1
            dbconnection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            String query = "INSERT INTO `MerchantData` (`UserName`, `AccountHolderName`, `AccountNumber`, `PanNumber`, `BankName`, `IFSC`) VALUES (?, ?, ?, ?, ?, ?);";
            //todo make query injection free (;,",', drop, insert, ...etc)
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1, merchantInfo.userName);
            dbstatement.setString(2, merchantInfo.accountHolderName);
            dbstatement.setString(3, merchantInfo.accountNumber);
            dbstatement.setString(4, merchantInfo.panNumber);
            dbstatement.setString(5, merchantInfo.bankName);
            dbstatement.setString(6, merchantInfo.IFSC);
            dbstatement.executeUpdate(query);
            String createQuery = "CREATE TABLE " + merchantInfo.userName + "_Merchant_History( ProductID BIGINT NOT NULL, RecieverName varchar(255) NOT NULL, BuyerUserName VARCHAR(255), ProductName VARCHAR(255) NOT NULL, Brand VARCHAR(255) NOT NULL, Quantity int NOT NULL, DeliveryAddress VARCHAR(255), ActualProductCost DOUBLE NOT NULL, BoughtCost DOUBLE NOT NULL, ExpiryDate DATE, ManufactureDate DATE, ProductCategory VARCHAR(255) NOT NULL, ProductSubCategory VARCHAR(255) NOT NULL, DiscountPercentage TINYINT NOT NULL, DiscountLabel VARCHAR(255) NOT NULL, ProductDescription TEXT NOT NULL, ProductImages LONGBLOB NOT NULL);";
            dbstatement = dbconnection.prepareStatement(createQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieve merchant information
     *
     * @param userName username of merchant
     * @return merchant information in instance of MerchantInfo
     */
    MerchantInfo getMerchantInfo(String userName) {//, String passwd) {
        ResultSet resultSet = null;
        MerchantInfo merchantInfo = null;
        String query = "SELECT * FROM MerchantData WHERE UserName = '" + userName + "';";
        try {
            resultSet = Query(query);
            if (resultSet.next()) {
                merchantInfo = new MerchantInfo();
                merchantInfo.newMerchant(resultSet.getString("UserName"),
                        resultSet.getString("AccountHolderName"),
                        resultSet.getString("AccountNumber"),
                        resultSet.getString("PanNumber"),
                        resultSet.getString("BankName"),
                        resultSet.getString("IFSC"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchantInfo;
    }

    /**
     * add product in MerchantProduct table
     *
     * @param product product that is to be added
     * @return true if product is added successfully else false
     */
    boolean addProduct(Product product) {
        try {
            String query = "INSERT INTO `MerchantProducts` ( `MerchantUserName`, `ProductName`, `Brand`, `Quantity`, `ActualProductCost`, `YourCost`, `ExpiryDate`, `ManufactureDate`, `ProductCategory`, `ProductSubCategory`,`DiscountPercentage`, `DiscountPeriod`, `DiscountLabel`, `ProductDescription`, `ProductImages`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            dbstatement = dbconnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbstatement.setString(1, product.merchantName);
            dbstatement.setString(2, product.productName);
            dbstatement.setString(3, product.brand);
            dbstatement.setInt(4, product.quantity);
            dbstatement.setDouble(5, product.actualProductCost);
            dbstatement.setDouble(6, product.yourCost);
            dbstatement.setDate(7, product.expiryDate);
            dbstatement.setDate(8, product.manufactureDate);
            dbstatement.setString(9, product.productCategory);
            dbstatement.setString(10, product.productSubCategory);
            dbstatement.setInt(11, product.discountPercentage);
            dbstatement.setTimestamp(12, product.discountPeriod);
            dbstatement.setString(13, product.discountLabel);
            dbstatement.setString(14, product.productDescription);
            dbstatement.setString(15, product.productImages);
            dbstatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * retrieve products of merchant
     *
     * @param username username of merchant
     * @return list of products {@code List<Product>}
     */
    List<Product> getMerchantProduct(String username) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * from `MerchantProducts` WHERE `MerchantUserName` = '" + username + "';";
        productList = getProducts(query);
        return productList;
    }

    /**
     * remove product from merchant table
     *
     * @param productID productID of product that is to be removed
     * @return true if product is successfully removed else false
     */
    boolean removeProduct(int productID) {
        String query = "DELETE FROM MerchantProducts WHERE ProductID = " + productID + ";";
        String checkQuery = "SELECT * FROM MerchantProducts WHERE ProductID = " + productID + ";";
        try {
            Query(query);
            return Query(checkQuery).next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean purchaseProduct(int productID, String userName, String deliveryAddress, double shippingCharges, int quantity, String paymentMethod){
        try {
            Product product = null;
            String getProductQuery = "select * from merchantproducts where ProductID = "+productID+" and Quantity >= "+quantity+";";
            ResultSet resultSet = Query(getProductQuery);
            if(resultSet.next()){
                product = generateProduct(resultSet);
            }
            if(product!=null){
                //to generate current date
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new Date();
                //to add 3 days from now for estimated date
                String dt = dateFormat.format(date);  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(dt));
                c.add(Calendar.DATE, 2);  // number of days to add
                dt = sdf.format(c.getTime());

                String query = "insert into "+userName+"_cart"+" (ProductPurchaseDate, EstimatedDeliveryDate, ActualDeliveryDate, DeliveryAddress, SellingPrice, PurchasePrice, ShippingCharges, Quantity, Retailer_UserName, PaymentMethod, OfferName, IsDelivered) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                dbstatement = dbconnection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                dbstatement.setDate(1, java.sql.Date.valueOf(LocalDate.of(2018,9,27)));
                dbstatement.setDate(2,java.sql.Date.valueOf(LocalDate.of(2018,9,29)));
                dbstatement.setDate(3,java.sql.Date.valueOf(LocalDate.of(2018,9,30)));
                dbstatement.setString(4,"'"+deliveryAddress);
                dbstatement.setDouble(5,product.actualProductCost);
                dbstatement.setDouble(6,product.yourCost);
                dbstatement.setDouble(7,shippingCharges);
                dbstatement.setInt(8,quantity);
                dbstatement.setString(9,product.merchantName);
                dbstatement.setString(10,paymentMethod);
                dbstatement.setString(11,product.discountLabel);
                dbstatement.setInt(12,0);
                dbstatement.executeUpdate();
                int q = product.quantity - quantity;
                String updateProduct = "update merchantproducts set Quantity = "+q+" WHERE ProductID = "+productID+";";
                String updateMerchantHistory = "insert into "+product.merchantName+"_merchant_history (ProductID, RecieverName, BuyerUserName, ProductName, Brand, Quantity, DeliveryAddress, ActualProductCost, BoughtCost, ExpiryDate, ManufactureDate, ProductCategory, ProductSubCategory, DiscountPercentage, DiscountLabel, ProductDescription, ProductImages) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                dbstatement = dbconnection.prepareStatement(updateProduct,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                dbstatement.executeUpdate();
                dbstatement = dbconnection.prepareStatement(updateMerchantHistory,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                dbstatement.setInt(1,productID);
                dbstatement.setString(2,product.merchantName);
                dbstatement.setString(3,userName);
                dbstatement.setString(4,product.productName);
                dbstatement.setString(5,product.brand);
                dbstatement.setInt(6,quantity);
                dbstatement.setString(7,deliveryAddress);
                dbstatement.setDouble(8,product.actualProductCost);
                dbstatement.setDouble(9,product.yourCost);
                dbstatement.setDate(10,product.expiryDate);
                dbstatement.setDate(11,product.manufactureDate);
                dbstatement.setString(12,product.productCategory);
                dbstatement.setString(13,product.productSubCategory);
                dbstatement.setInt(14,product.discountPercentage);
                dbstatement.setString(15,product.discountLabel);
                dbstatement.setString(16,product.productDescription);
                dbstatement.setString(17,product.productImages);
                dbstatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Cart generateCart(ResultSet resultSet) throws SQLException {
        if(resultSet == null){
            return null;
        }
        Cart cart = new Cart();
        cart.makeCart(resultSet.getInt("PurchaseID"),
                resultSet.getDate("ProductPurchaseDate"),
                resultSet.getDate("EstimatedDeliveryDate"),
                resultSet.getDate("ActualDeliveryDate"),
                resultSet.getString("DeliveryAddress"),
                resultSet.getDouble("SellingPrice"),
                resultSet.getDouble("PurchasePrice"),
                resultSet.getDouble("ShippingCharges"),
                resultSet.getInt("Quantity"),
                resultSet.getString("Retailer_UserName"),
                resultSet.getString("PaymentMethod"),
                resultSet.getString("OfferName"),
                resultSet.getInt("IsDelivered"));
        return cart;
    }

    private List<Cart> getCart(String query) {
        ResultSet resultSet;
        List<Cart> cartList = new ArrayList<Cart>();
        try {
            resultSet = Query(query);
            while (resultSet.next()) {
                cartList.add(generateCart(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }

    List<Cart> getAllUserCart(String userName) {
        List<Cart> cartList = new ArrayList<>();
        String query = "select * from "+userName+"_cart ;";
        cartList = getCart(query);
        for(Cart c: cartList)
            System.out.println(c.productID);
        return cartList;
    }

    List<Cart> getUserCart(String userName, String from, String to) {
        List<Cart> cartList = new ArrayList<>();
        String query = "select * from `"+userName+"_cart` where `ProductPurchaseDate` between Date('"+from+"') and Date('"+to+"') ;";
        cartList = getCart(query);
        return cartList;
    }

    List<Cart> getCartOfPriceBetween(String userName, double from, double to) {
        List<Cart> cartList = new ArrayList<>();
        String query = "select * from `"+userName+"_cart` where `PurchasePrice` between "+from+" and "+to+" ;";
        cartList = getCart(query);
        return cartList;
    }

    private MerchantHistory generateMerchantHistory(ResultSet resultSet) throws SQLException {
        if(resultSet == null){
            return null;
        }
        MerchantHistory merchantHistory = new MerchantHistory();
        Product product = new Product();
        product.newProduct(resultSet.getInt("ProductID"),
                "#",
                resultSet.getString("ProductName"),
                resultSet.getString("Brand"),
                resultSet.getInt("Quantity"),
                resultSet.getDouble("ActualProductCost"),
                resultSet.getDouble("BoughtCost"),
                resultSet.getDate("ExpiryDate"),
                resultSet.getDate("ManufactureDate"),
                resultSet.getString("ProductCategory"),
                resultSet.getString("ProductSubCategory"),
                resultSet.getInt("DiscountPercentage"),
                null,
                resultSet.getString("DiscountLabel"),
                resultSet.getString("ProductDescription"),
                resultSet.getString("ProductImages"));
        merchantHistory.addHistory(product,
                resultSet.getString("RecieverName"),
                resultSet.getString("BuyerUserName"),
                resultSet.getInt("Quantity"),
                resultSet.getString("DeliveryAddress"));
        return merchantHistory;
    }

    private List<MerchantHistory> getMerchantHistory(String query) {
        ResultSet resultSet;
        List<MerchantHistory> merchantHistoryList = new ArrayList<MerchantHistory>();
        try {
            resultSet = Query(query);
            while (resultSet.next()) {
                merchantHistoryList.add(generateMerchantHistory(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return merchantHistoryList;
    }

    List<MerchantHistory> getAllMerchantHistory(String merchantUserName) {
        List<MerchantHistory> merchantHistories = new ArrayList<MerchantHistory>();
        String query = "select * from `"+merchantUserName+"_merchant_history` ;";
        merchantHistories = getMerchantHistory(query);
        return merchantHistories;
    }

    List<MerchantHistory> getMerchantHistoryOfUser(String merchantUserName, String userName) {
        List<MerchantHistory> merchantHistories = new ArrayList<MerchantHistory>();
        String query = "select * from `"+merchantUserName+"_merchant_history` where `BuyerUserName` = "+userName+";";
        merchantHistories = getMerchantHistory(query);
        return merchantHistories;
    }

    List<MerchantHistory> getMerchantHistoryOfProduct(String merchantUserName, int productID) {
        List<MerchantHistory> merchantHistories = new ArrayList<MerchantHistory>();
        String query = "select * from `"+merchantUserName+"_merchant_history` where `ProductID` = "+productID+";";
        merchantHistories = getMerchantHistory(query);
        return merchantHistories;
    }

    private Notification generateNotification(ResultSet resultSet) throws SQLException {
        if(resultSet == null){
            return null;
        }
        Notification notification = new Notification();
        notification.setNotification(resultSet.getInt("NotificationID"),
                resultSet.getString("Notification"));
        return notification;
    }

    private List<Notification> getNotifications(String query) {
        ResultSet resultSet;
        List<Notification> notifications = new ArrayList<Notification>();
        try {
            resultSet = Query(query);
            while (resultSet.next()) {
                notifications.add(generateNotification(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public List<Notification> getNotification(String userName) {
        List<Notification> notifications = new ArrayList<Notification>();
        String query = "select * from `"+userName+"_notification`;";
        notifications = getNotifications(query);
        return notifications;
    }

    public boolean removeNotification(int notificationID, String userName){
        String query = "DELETE FROM `"+userName+"_notification` WHERE NotificationID = "+notificationID+";";
        try {
            Query(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendDiscount() {
        String query = "select * from userdate;";
        ResultSet resultSet;
        try {
            resultSet = Query(query);
            while(resultSet.next()){
                String userName = resultSet.getString("UserName");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar aCalendar = Calendar.getInstance();
                aCalendar.add(Calendar.MONTH, -1);
                aCalendar.set(Calendar.DATE, 1);
                Date firstDateOfPreviousMonth = aCalendar.getTime();
                aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date lastDateOfPreviousMonth = aCalendar.getTime();
                String from = dateFormat.format(firstDateOfPreviousMonth);
                String to = dateFormat.format(lastDateOfPreviousMonth);
                List<Cart> carts = getUserCart(userName, from, to);
                Iterator iterator = carts.iterator();
                double spent = 0;
                while(iterator.hasNext()) {
                    Cart cart = (Cart) iterator.next();
                    spent += cart.quantity * cart.sellingPrice;
                }
                if(spent>1) {
                    double discount = spent*0.1;
                    String updateMerchantNotification = "insert into `"+userName+"_notification` (Notification) values (?);";
                    String code = getSaltString();
                    dbstatement = dbconnection.prepareStatement(updateMerchantNotification,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    dbstatement.setString(1,"Discount on COD of ₹ "+discount+" with code: "+code);
                    dbstatement.executeUpdate();
                    String validCode = "insert into ValidCodes (code) values (?);";
                    dbstatement = dbconnection.prepareStatement(validCode,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    dbstatement.setString(1,code);
                    dbstatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    boolean editPassword(String userName, String oldPassword, String newPassword) {
        try {
            String query = "Select * from userdate where userName = "+userName+" and password = "+oldPassword+"";
            ResultSet resultSet = Query(query);
            if(resultSet.next()){
                String updateQuery = "update userdata set password = "+newPassword+" where passwprd = "+oldPassword+"; ";
                return true;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}