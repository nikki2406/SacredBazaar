package sample.Network;

import sample.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    /**
     * Objects for managing communication between server and client,
     * DatabaseManager instance for handling database relating tasks
     */
    private Socket socket;
    private NetManager netManager;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private DatabaseManager databaseManager;

    @Override
    public boolean equals(Object object) {
        ClientHandler ch = (ClientHandler) object;
        return ch.socket.getInetAddress().equals(this.socket);
    }

    /**
     * generates hash code for client
     * @return hash code for client
     */
    @Override
    public int hashCode() {
        String shash = Hash.getSha256(socket.getInetAddress().toString());
        int hash = 0;
        for (int i = 0; i < shash.length(); i++) {
            hash += (int) shash.charAt(i);
        }
        return hash;
    }

    /**
     * Constructor for instantiating data and object I/O streams
     * @param netManager store reference of NetManager to invoke it'socket methods
     * @param s stores reference of socket for making I/O streams
     */
    //todo Optimise hashcode method for set
    ClientHandler(NetManager netManager, Socket s) {
        this.socket = s;
        this.netManager = netManager;
        try {
            dataOutputStream = new DataOutputStream(s.getOutputStream());
            objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            dataInputStream = new DataInputStream(s.getInputStream());
            objectInputStream = new ObjectInputStream(s.getInputStream());
            databaseManager = new DatabaseManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Method that perform action according to client request.
     * For case number refer ActionID class
     */
    private void performAction(){
        /*
         * While client connected, perform action according to client'socket requests.
         * */
        while (true) {
            try {
                switch (Integer.parseInt(dataInputStream.readUTF())) {
                    case 0: {
                        String key = dataInputStream.readUTF();
                        List<Product> productList = databaseManager.searchProduct(key);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 1: {
                        UserInfo userInfo = (UserInfo) objectInputStream.readObject();
                        boolean isUserAdded = databaseManager.createUser(userInfo);
                        dataOutputStream.writeUTF(Boolean.toString(isUserAdded));
                        break;
                    }
                    case 2: {
                        String username = dataInputStream.readUTF();
                        String passwd = dataInputStream.readUTF();
                        UserInfo userInfo = databaseManager.getUserInfo(username, passwd);
                        objectOutputStream.writeObject(userInfo);
                        break;
                    }
                    case 3: {
                        String username = dataInputStream.readUTF();
                        String passwd = dataInputStream.readUTF();
                        boolean isLogin = databaseManager.loginUser(username, passwd);
                        dataOutputStream.writeUTF(Boolean.toString(isLogin));
                        break;
                    }
                    case 4: {
                        MerchantInfo merchantInfo = (MerchantInfo) objectInputStream.readObject();
                        boolean isMerchantAdded = databaseManager.createMerchant(merchantInfo);
                        dataOutputStream.writeUTF(Boolean.toString(isMerchantAdded));
                        break;
                    }
                    case 5: {
                        String username = dataInputStream.readUTF();
                        MerchantInfo merchantInfo = databaseManager.getMerchantInfo(username);
                        objectOutputStream.writeObject(merchantInfo);
                        break;
                    }
                    case 7: {
                        Product product = (Product) objectInputStream.readObject();
                        boolean isProductAdded = databaseManager.addProduct(product);
                        dataOutputStream.writeUTF(Boolean.toString(isProductAdded));
                        break;
                    }
                    case 8: {
                        String username = dataInputStream.readUTF();
                        List<Product> productList = databaseManager.getMerchantProduct(username);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 9: {
                        int productID = Integer.parseInt(dataInputStream.readUTF());
                        boolean isProductRemoved = databaseManager.removeProduct(productID);
                        dataOutputStream.writeUTF(Boolean.toString(isProductRemoved));
                        break;
                    }
                    case 10: {
                        String category = dataInputStream.readUTF();
                        List<Product> productList = databaseManager.getProductOfCategory(category);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 11: {
                        List<String> categories = databaseManager.getCategory();
                        objectOutputStream.writeObject(categories);
                        break;
                    }
                    case 12: {
                        List<List<String>> subCategoriesList = databaseManager.getSubCategory();
                        objectOutputStream.writeObject(subCategoriesList);
                        break;
                    }
                    case 13: {
                        int min = Integer.parseInt(dataInputStream.readUTF());
                        int max = Integer.parseInt(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.getProductBetween(min,max);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 14: {
                        dataOutputStream.writeUTF(databaseManager.getRange());
                        break;
                    }
                    case 15: {
                        int productID = Integer.parseInt(dataInputStream.readUTF());
                        String userName = dataInputStream.readUTF();
                        String deliveryAddress = dataInputStream.readUTF();;
                        double shippingCharges = Double.parseDouble(dataInputStream.readUTF());
                        int quantity = Integer.parseInt(dataInputStream.readUTF());
                        String paymentMethod = dataInputStream.readUTF();
                        boolean purchased = databaseManager.purchaseProduct(productID,userName,deliveryAddress,shippingCharges,quantity,paymentMethod);
                        dataOutputStream.writeUTF(Boolean.toString(purchased));
                        break;
                    }
                    case 16: {
                        String key = dataInputStream.readUTF();
                        boolean isAscending = Boolean.parseBoolean(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.searchProductOrderByPrice(key,isAscending);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 17: {
                        String key = dataInputStream.readUTF();
                        boolean isAscending = Boolean.parseBoolean(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.searchProductOrderByName(key,isAscending);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 18: {
                        String category = dataInputStream.readUTF();
                        boolean isAscending = Boolean.parseBoolean(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.getProductOfCategoryOrderByPrice(category,isAscending);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 19: {
                        String category = dataInputStream.readUTF();
                        boolean isAscending = Boolean.parseBoolean(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.getProductOfCategoryOrderByName(category,isAscending);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 20: {
                        String category = dataInputStream.readUTF();
                        String subCategory = dataInputStream.readUTF();
                        List<Product> productList = databaseManager.getProductOfSubCategory(category,subCategory);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 21: {
                        String category = dataInputStream.readUTF();
                        String subCategory = dataInputStream.readUTF();
                        boolean isAscending = Boolean.parseBoolean(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.getProductOfSubCategoryOrderByPrice(category,subCategory,isAscending);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 22: {
                        String category = dataInputStream.readUTF();
                        String subCategory = dataInputStream.readUTF();
                        boolean isAscending = Boolean.parseBoolean(dataInputStream.readUTF());
                        List<Product> productList = databaseManager.getProductOfSubCategoryOrderByName(category,subCategory,isAscending);
                        objectOutputStream.writeObject(productList);
                        break;
                    }
                    case 23: {
                        String userName = dataInputStream.readUTF();
                        String from = dataInputStream.readUTF();
                        String to = dataInputStream.readUTF();
                        List<Cart> cartList = databaseManager.getUserCart(userName,from,to);
                        objectOutputStream.writeObject(cartList);
                        break;
                    }
                    case 24: {
                        String userName = dataInputStream.readUTF();
                        double from = Double.parseDouble(dataInputStream.readUTF());
                        double to = Double.parseDouble(dataInputStream.readUTF());
                        List<Cart> cartList = databaseManager.getCartOfPriceBetween(userName,from,to);
                        objectOutputStream.writeObject(cartList);
                        break;
                    }
                    case 25: {
                        String userName = dataInputStream.readUTF();
                        List<Cart> cartList = databaseManager.getAllUserCart(userName);
                        objectOutputStream.writeObject(cartList);
                        break;
                    }
                    case 26: {
                        String merchantUserName = dataInputStream.readUTF();
                        List<MerchantHistory> cartList = databaseManager.getAllMerchantHistory(merchantUserName);
                        objectOutputStream.writeObject(cartList);
                        break;
                    }
                    case 27: {
                        String merchantUserName = dataInputStream.readUTF();
                        String userName = dataInputStream.readUTF();
                        List<MerchantHistory> cartList = databaseManager.getMerchantHistoryOfUser(merchantUserName,userName);
                        objectOutputStream.writeObject(cartList);
                        break;
                    }
                    case 28: {
                        String merchantUserName = dataInputStream.readUTF();
                        int productID = Integer.parseInt(dataInputStream.readUTF());
                        List<MerchantHistory> cartList = databaseManager.getMerchantHistoryOfProduct(merchantUserName,productID);
                        objectOutputStream.writeObject(cartList);
                        break;
                    }
                    case 29: {
                        String userName = dataInputStream.readUTF();
                        List<Notification> notifications = databaseManager.getNotification(userName);
                        break;
                    }
                    case 30: {
                        int notificationID = Integer.parseInt(dataInputStream.readUTF());
                        String userName = dataInputStream.readUTF();
                        boolean isRemoved = databaseManager.removeNotification(notificationID,userName);
                        dataOutputStream.writeUTF(Boolean.toString(isRemoved));
                        break;
                    }
                    case 31: {
                        Product product = (Product) objectInputStream.readObject();
                        String userName = dataInputStream.readUTF();
                        boolean isAdded = databaseManager.addtocart(product,userName);
                        dataOutputStream.writeUTF(Boolean.toString(isAdded));
                        break;
                    }
                    case 999: {
                        dataInputStream.close();
                        netManager.clientHandlers.remove(this);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                /*
                 * disconnect from client when client closes application
                 * */
                System.out.println("User disconnected");
                //todo remove user from list //uncomment line below
                netManager.clientHandlers.remove(this);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        performAction();
    }
}
