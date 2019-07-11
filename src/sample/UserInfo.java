package sample;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

public class UserInfo implements Serializable {
    public String passwd;
    public String userName;
    public String firstName;
    public String lastName;
    public String gender;
    public String addressLine1;
    public String addressLine2;
    public String addressLine3;
    public Date DOB;
    public Timestamp timestamp;
    public String phoneNumber;
    public String email;
    public int isMerchant;
    public int isRoot;

    public void newUser(String userName, String passwd, String firstName, String lastName, String gender, String addressLine1, String addressLine2, String addressLine3, Date DOB, String phoneNumber, String email) {
        this.userName = userName;
        this.passwd = passwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.DOB = DOB;
        this.phoneNumber = phoneNumber;
        this.email = email;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.isMerchant = this.isRoot = 0;
    }

    public void newUser(String userName, String passwd, String firstName, String lastName, String gender, String addressLine1, String addressLine2, String addressLine3, Date DOB, String phoneNumber, String email, int isMerchant, int isRoot){
        newUser(userName, passwd, firstName, lastName, gender, addressLine1, addressLine2, addressLine3, DOB, phoneNumber, email);
        this.isMerchant = isMerchant;
        this.isRoot = isRoot;
    }

}