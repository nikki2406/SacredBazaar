package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Merchant extends User {

    public MerchantInfo merchantInfo;
    /**
     * create merchant having it's information in instance of MerchantInfo
     * @param merchantInfo instance having information of merchant required
     * @return true if merchant is created else false
     */
    public boolean createMerchant(MerchantInfo merchantInfo) {
        boolean isMerchantAdded = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.createMerchant_ID);
//                dataOutputStream.flush();
                objectOutputStream.writeObject(merchantInfo);
//                objectOutputStream.flush();
                isMerchantAdded = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                isMerchantAdded = false;
                e.printStackTrace();
            }
        } else {
            if(connectToServer(serverIP,serverPort)) {
                //can't connect to server;
            }
        }
        return isMerchantAdded;
    }

    public boolean removeMerchant(String userName, String passwd){
        //todo delete merchant
        return true;
    }

    /**
     * retrieve merchant information from server
     * @param userName userName of merchant
     * @return instance of MerchantInfo having merchant information
     */
    public MerchantInfo getMerchantInfo(String userName) {
        MerchantInfo merchantInfo = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getMerchantInfo_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(userName);
//                dataOutputStream.flush();
                merchantInfo = (MerchantInfo) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if(connectToServer(serverIP,serverPort)) {
                //can't connect to server;
            }
        }
        return merchantInfo;
    }

/*
    //todo remove this redundant function using UserInfo tuple IsMerchant.
    public boolean isMerchant(String username, String passwd) {
        boolean ismerchant = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.isMerchant_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(username);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(passwd);
//                dataOutputStream.flush();
                ismerchant = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                ismerchant = false;
                e.printStackTrace();
            }
        } else {
            if(connectToServer(serverIP,serverPort)) {
                //can't connect to server;
            }
        }
        return ismerchant;
    }
*/

    /**
     * add product by sending it's information
     * @param product instance of product storing information of product.
     * @return true if product is added else false
     */
    public boolean addProduct(Product product) {
        boolean isAdded = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.addProduct_ID);
//                out.flush();
                objectOutputStream.writeObject(product);
//                out.flush();
                isAdded = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(connectToServer(serverIP,serverPort)) {
                //can't connect to server;
            }
        }
        return isAdded;
    }

    /**
     * retrieve merchant products for it's dashboard
     * @param username username if merchant
     * @return list of products in form {@code List<Product>}
     */
    public List<Product> getMerchantProduct(String username) {
        List<Product> productList = null;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.getMerchantsProduct_ID);
//                dataOutputStream.flush();
                dataOutputStream.writeUTF(username);
//                dataOutputStream.flush();
                productList = (ArrayList<Product>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if(connectToServer(serverIP,serverPort)) {
                //can't connect to server;
            }
        }
        return productList;
    }

    /**
     * remove product by it's product ID
     * @param productID pid of product that is to be removed
     * @return true if product is removed else false
     */
    public boolean removeProduct(int productID) {
        boolean isRemoved = false;
        if (isConnected) {
            try {
                dataOutputStream.writeUTF(ActionID.removeProduct_ID);
//                out.flush();
                dataOutputStream.writeUTF(String.valueOf(productID));
//                out.flush();
                isRemoved = Boolean.parseBoolean(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(connectToServer(serverIP,serverPort)) {
                //can't connect to server;
            }
        }
        return isRemoved;
    }

}
