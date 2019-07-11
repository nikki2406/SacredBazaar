package sample;

import java.io.Serializable;

public class MerchantInfo extends UserInfo implements Serializable {

    public String userName;
    public String accountHolderName;
    public String accountNumber;
    public String panNumber;
    public String bankName;
    public String IFSC;

    /**
     * create merchant using information given in parameters
     * @param userName username of merchant
     * @param accountHolderName account holder name of it's bank account
     * @param accountNumber account number of merchant
     * @param panNumber pan number of merchant
     * @param bankName bank name of merchant
     * @param IFSC IFSC code of bank having merchant's bank account
     */
    public void newMerchant(String userName, String accountHolderName, String accountNumber, String panNumber, String bankName, String IFSC) {
        this.userName = userName;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.panNumber = panNumber;
        this.bankName = bankName;
        this.IFSC = IFSC;
    }
}