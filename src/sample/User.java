package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//todo***** auto fill user information on partial fill of order (save)
public class User implements Runnable {
    //todo make static
    //todo add userID for session
    public UserInfo userInfo;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public Socket s;
    public int serverPort;
    public String serverIP;
    public boolean isConnected;

    /**
     * Connect to server using IP and PORT of server
     * if not run game like T-rex run
     *
     * @param serverIP   IP of server
     * @param serverPort Port of server
     * @return true if connected to server
     */
    public boolean connectToServer(String serverIP, int serverPort) {
        try {
            s = new Socket(serverIP, serverPort);
            System.out.println("Making streams...");
            dataOutputStream = new DataOutputStream(s.getOutputStream());
            objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            dataInputStream = new DataInputStream(s.getInputStream());
            objectInputStream = new ObjectInputStream(s.getInputStream());
            System.out.println("Creating thread...");
            Thread t = new Thread(this);
            t.start();
            isConnected = true;
            System.out.println("Connected to server");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not connect to server.");
            //Thread thread = new Thread(new userinterface.GameWindow()); /*--------------------Yaha bakchoodi ki hai------------------------>>*/
            //thread.start();
            GameWindow.runGame();
        }
        return false;
    }

    /**
     * Search for products having substring 'key'
     *
     * @param keys substring that is to be search in product name or category
     * @return list of products that are found having key in their name or category
     */
    public List<Product> searchProduct(String keys) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.search_ID);
//                out.flush();
                dataOutputStream.writeUTF(keys);
//                out.flush();
                //CHANGED TO LIST IGNORE QUERY RESULT;
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
//                Product nullProduct = new Product();
//                nullProduct.newProduct("#","#","#",0,0,0,null,null,"#",0,null,"#","#","#");
//                productList = new ArrayList<>();
//                productList.add(nullProduct);
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    /**
     * sent information of user to server to make account
     *
     * @param userInfo has user information that is to be inserted
     * @return true if user is created successfully else false
     */
    public boolean createUser(UserInfo userInfo) {
        boolean isUserAdded = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.createUser_ID);
//                dataOutputStream.flush();
                objectOutputStream.writeObject(userInfo);
//                objectOutputStream.flush();
                isUserAdded = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                isUserAdded = false;
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return isUserAdded;
    }

    /**
     * checks for user by sending username and password to server
     *
     * @param username username of user
     * @param passwd   password of user sha256 encrypted
     * @return true if username and password are correct else false
     */
    public boolean loginUser(String username, String passwd) {
        boolean isLogin = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.checkCredentials_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(username);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(passwd);
//                dataOutputStream.flush();
                isLogin = Boolean.parseBoolean(dataInputStream.readUTF());
//                isUserAdded = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                isLogin = false;
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return isLogin;
    }

    /**
     * Retrieve user info having username and password as given in parameters from server
     *
     * @param userName username of user
     * @param passwd   password of user sha256 encrypted
     * @return user information as instance of UserInfo
     */
    public UserInfo getUserInfo(String userName, String passwd) {
        UserInfo userInfo = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getUserInfo_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(passwd);
//                dataOutputStream.flush();
                userInfo = (UserInfo) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return userInfo;
    }

    /**
     * @param category
     * @return
     */
    public List<Product> showProductOfCategory(String category) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.showProductOfCategory_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(category);
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public boolean addProductToCart(Product product, String userName) {
        boolean isAdded = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.addtocart_ID);
//                dataOutputStream.flush();
                objectOutputStream.writeObject(product);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                isAdded = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return isAdded;
    }

    public List<String> getCategory() {
        List<String> categories = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getCategoty_ID);
//                dataOutputStream.flush();
                categories = (ArrayList<String>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return categories;
    }

    public List<List<String>> getSubCategory() {
        List<List<String>> subCategoryList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getSubCategoty_ID);
//                dataOutputStream.flush();
                subCategoryList = (List<List<String>>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return subCategoryList;
    }

    public List<Product> getProductBetween(int min, int max) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getProductBetween_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Integer.toString(min));
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Integer.toString(max));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    /**
     *
     * NOTE COLON SEPERATED VALUE
     * MIN:MAX FORMAT
     */
    public String getRange() {
        String range = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getRange_ID);
//                dataOutputStream.flush();
                range = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return range;
    }

    public boolean purchaseProduct(int productID, String userName, String deliveryAddress, double shippingCharges, int quantity, String paymentMethod){
        boolean purchased = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.purchaseProduct_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Integer.toString(productID));
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(deliveryAddress);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Double.toString(shippingCharges));
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Integer.toString(quantity));
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(paymentMethod);
//                dataOutputStream.flush();
                purchased = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return purchased;
    }

    public List<Product> searchProductOrderByPrice(String key, boolean isAscending) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.searchProductOrderByPrice_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(key);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Boolean.toString(isAscending));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Product> searchProductOrderByName(String key, boolean isAscending) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.searchProductOrderByName_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(key);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Boolean.toString(isAscending));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Product> getProductOfCategoryOrderByPrice(String category, boolean isAscending) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getProductOfCategoryOrderByPrice_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(category);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Boolean.toString(isAscending));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Product> getProductOfCategoryOrderByName(String category, boolean isAscending) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getProductOfCategoryOrderByName_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(category);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Boolean.toString(isAscending));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Product> getProductOfSubCategory(String category, String subCategory) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getProductOfSubCategory_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(category);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(subCategory);
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Product> getProductOfSubCategoryOrderByPrice(String category, String subCategory, boolean isAscending) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getProductOfSubCategoryOrderByPrice_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(category);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(subCategory);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Boolean.toString(isAscending));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Product> getProductOfSubCategoryOrderByName(String category, String subCategory, boolean isAscending) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getProductOfSubCategoryOrderByName_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(category);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(subCategory);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Boolean.toString(isAscending));
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    public List<Cart> getUserCart(String userName, String fromDate, String toDate) {
        List<Cart> cartList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getUserCart_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(fromDate);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(toDate);
//                dataOutputStream.flush();
                cartList = (ArrayList<Cart>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return cartList;
    }

    public List<Cart> getCartOfPriceBetween(String userName, double fromPrice, double toPrice) {
        List<Cart> cartList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getCartOfPriceBetween_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Double.toString(fromPrice));
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Double.toString(toPrice));
//                dataOutputStream.flush();
                cartList = (ArrayList<Cart>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return cartList;
    }

    public List<Cart> getAllUserCart(String userName) {
        List<Cart> cartList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getAllUserCart_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                cartList = (ArrayList<Cart>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return cartList;
    }

    public List<MerchantHistory> getAllMerchantHistory(String merchantUserName) {
        List<MerchantHistory> merchantHistoryList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getAllMerchantHistory_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(merchantUserName);
//                dataOutputStream.flush();
                merchantHistoryList = (ArrayList<MerchantHistory>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return merchantHistoryList;
    }

    public List<MerchantHistory> getMerchantHistoryOfUser(String merchantUserName, String userName) {
        List<MerchantHistory> merchantHistoryList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getMerchantHistoryOfUser_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(merchantUserName);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                merchantHistoryList = (ArrayList<MerchantHistory>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return merchantHistoryList;
    }

    public List<MerchantHistory> getMerchantHistoryOfProduct(String merchantUserName, int productID) {
        List<MerchantHistory> merchantHistoryList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getMerchantHistoryOfProduct_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(merchantUserName);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Integer.toString(productID));
//                dataOutputStream.flush();
                merchantHistoryList = (ArrayList<MerchantHistory>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return merchantHistoryList;
    }

    public List<Notification> getNotification(String userName) {
        List<Notification> notifications = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getNotification_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                notifications = (ArrayList<Notification>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return notifications;
    }

    public boolean removeNotification(int notificationID, String userName){
        boolean isRemoved = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.removeNotification_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(Integer.toString(notificationID));
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                isRemoved = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (connectToServer(serverIP, serverPort)) {
                //can't connect to server;
            }
        }
        return isRemoved;
    }

    @Override
    public void run() {

    }
}
