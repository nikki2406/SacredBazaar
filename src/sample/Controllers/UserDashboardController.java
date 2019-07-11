package sample.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLData;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;


public class UserDashboardController implements Initializable {

    private String searchText;
    private SceneManager sceneManager = new SceneManager();
    private boolean isCategoryPaneVisible;
    private List<Cart> cartResult;
    private boolean isDiliveredVisible;
    private boolean isUndiliveredVisible;

    @FXML
    private Label welcomeMsg;

    @FXML
    private JFXHamburger userDropdownMenu;

    @FXML
    private TextField usernameLabel;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private JFXPasswordField currentPassword;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXPasswordField confirmPassword;

    @FXML
    private JFXButton changePas;

    @FXML
    private JFXTextField contactNum;

    @FXML
    private JFXTextField emailID;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton otherRadioBaton;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXTextField addressL1;

    @FXML
    private JFXTextField addressL2;

    @FXML
    private JFXTextField addressL3;

    @FXML
    private JFXButton editInfo;

    @FXML
    private JFXButton updateInfo;

    @FXML
    private Label userFullName;

    @FXML
    private JFXButton personalInfoTab;

    @FXML
    private JFXButton myCartTab;

    @FXML
    private JFXButton purchaseHistoryTab;

    @FXML
    private ListView cartListView;

    @FXML
    private ListView<?> purchaseHistory;

    @FXML
    private JFXButton categoryButton;

    @FXML
    private ImageView categoryLogo;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private Pane userDropMenu;

    @FXML
    private JFXButton merchantOnlyButton;

    @FXML
    private Pane categoryPane;

    @FXML
    private JFXTreeView<?> categoryTree;

    @FXML
    private ScrollPane personalInfoPane;

    @FXML
    private AnchorPane myCartPane;

    @FXML
    private JFXDatePicker fromDate;

    @FXML
    private JFXDatePicker toDate;

    @FXML
    private JFXTextField minPrice;

    @FXML
    private JFXTextField maxPrice;

    @FXML
    private JFXCheckBox deliveredProducts;

    @FXML
    private JFXCheckBox undeliveredProducts;

    @FXML
    private JFXDatePicker endingDate;

    @FXML
    private JFXDatePicker satrtingDate;

    @FXML
    private Label totalAmountSpent;

    @FXML
    void deliveredOrUndelivered(){//Todo
        if(deliveredProducts.isSelected())
            isDiliveredVisible = true;
        if(undeliveredProducts.isSelected())
            isUndiliveredVisible = true;
        setCart();
    }

    @FXML
    void showCartByDate(){ //Todo
        cartResult = Main.user.getUserCart(Main.user.userInfo.userName,Date.valueOf(fromDate.getValue())+"",Date.valueOf(toDate.getValue())+"");
        for(Cart c: cartResult)
            System.out.println(c.productID);
        setCart();
    }

    @FXML
    void showCartByPrice(){
        cartResult = Main.user.getCartOfPriceBetween(Main.user.userInfo.userName,Double.parseDouble(minPrice.getText()),Double.parseDouble(maxPrice.getText()));
        setCart();
    }

    @FXML
    void categoryButton(ActionEvent event) {
        if(!isCategoryPaneVisible) {
            categoryPane.toFront();
            categoryPane.setDisable(false);
            categoryPane.setOpacity(1);
            isCategoryPaneVisible = true;
        }
        else{
            categoryPane.toBack();
            categoryPane.setDisable(true);
            categoryPane.setOpacity(0);
            isCategoryPaneVisible = false;
        }
    }

    @FXML
    void changePassword(ActionEvent event) {
        if(Main.user.getUserInfo(Main.user.userInfo.userName,currentPassword.getText()) != null){
            if(newPassword.getText().equals(confirmPassword.getText())){
                //Main.user.updatePassword(newPassword.getText());
            }
        }
        else{
            currentPassword.setStyle("-fx-border-color: red");
            newPassword.setText("");
            confirmPassword.setText("");
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void editInformation(ActionEvent event) {
        firstName.setDisable(false);
        lastName.setDisable(false);
        contactNum.setEditable(true);
        emailID.setEditable(true);
        femaleRadioButton.setDisable(false);
        maleRadioButton.setDisable(false);
        otherRadioBaton.setDisable(false);
        dob.setEditable(true);
        addressL1.setEditable(true);
        addressL2.setEditable(true);
        addressL3.setEditable(true);
    }

    @FXML
    void goToInventoryManagement(ActionEvent event) {

    }

    @FXML
    void changeTotalSpent(){
        Date start = Date.valueOf(satrtingDate.getValue());
        Date end = Date.valueOf(endingDate.getValue());
        double sum=0;
        for(Cart c: cartResult){
            if(c.productPurchaseDate.compareTo(start) >= 0 && c.productPurchaseDate.compareTo(end)<=0)
                sum+=c.purchasePrice;
        }
        totalAmountSpent.setText(sum+"");
    }

    @FXML
    void goToMyCart(ActionEvent event) {
        showMyCartTab(event);
    }

    @FXML
    void goToMyProfile(ActionEvent event) {
        showPersonalInfoTab(event);
    }

    @FXML
    void goToNotification(ActionEvent event) {
        List<Notification> notificationList = Main.user.getNotification(Main.user.userInfo.userName);
    }

    @FXML
    void menuClick(MouseEvent event) {

    }

    @FXML
    void searchProduct(ActionEvent event) {
        searchText = searchBar.getText();
        Main.tempRestPoint = new TempRestPoint("Search Text",searchText);
        try {
            sceneManager.changeScene("FXML\\searchPage","Search Page",event);
        } catch (IOException e) {
            System.out.println("ProductPage: search page load failed.");
        }
    }

    @FXML
    void showMyCartTab(ActionEvent event) {

        personalInfoPane.setOpacity(0);
        myCartPane.setOpacity(1);
        personalInfoPane.setDisable(true);

        myCartPane.setDisable(false);

        cartResult = Main.user.getAllUserCart(Main.user.userInfo.userName);
    }

    @FXML
    void showPersonalInfoTab(ActionEvent event) {
        setPersonalInfoTab();

        personalInfoPane.setOpacity(1);
        myCartPane.setOpacity(0);
        personalInfoPane.setDisable(false);

        myCartPane.setDisable(true);
    }

    @FXML
    void updateInformation(ActionEvent event) {
        //Main.user.updateUserInfo();
    }

    private void setHamburgerButton(){
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(userDropdownMenu);
        burgerTask.setRate(-1);
        userDropdownMenu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            if(userDropMenu.isDisabled()) {
                userDropMenu.toFront();
                userDropMenu.setDisable(false);
                userDropMenu.setOpacity(1);
            }
            else{
                userDropMenu.toBack();
                userDropMenu.setDisable(true);
                userDropMenu.setOpacity(0);
            }
        });
    }

    private void setWelcomeMsg(){
        welcomeMsg.setText("Welcome, "+ Main.user.userInfo.firstName + " " + Main.user.userInfo.lastName);
        userFullName.setText(Main.user.userInfo.firstName+" "+Main.user.userInfo.lastName);
    }

    private void setUserDropdownMenu(){
        if(Main.user.userInfo.isMerchant == 0){
            merchantOnlyButton.setDisable(true);
            merchantOnlyButton.setOpacity(0);
        }
    }

    private void setPersonalInfoTab(){
        firstName.setText(Main.user.userInfo.firstName);
        lastName.setText(Main.user.userInfo.lastName);
        emailID.setText(Main.user.userInfo.email);
        contactNum.setText(Main.user.userInfo.phoneNumber);
        if(Main.user.userInfo.gender.compareToIgnoreCase("Male")==0)
            gender.selectToggle(maleRadioButton);
        else if(Main.user.userInfo.gender.compareToIgnoreCase("Female") == 0)
            gender.selectToggle(femaleRadioButton);
        else
            gender.selectToggle(otherRadioBaton);
        addressL1.setText(Main.user.userInfo.addressLine1);
        addressL2.setText(Main.user.userInfo.addressLine2);
        addressL3.setText(Main.user.userInfo.addressLine3);
        double sum=0;
        for(Cart c: cartResult){
            sum+=c.purchasePrice;
        }
        totalAmountSpent.setText(sum+"");
    }

    private void setCart(){
        boolean isLoopExecuted = false;
        cartListView.getItems().clear();
        System.out.println("001");
        if(cartResult == null){
            System.out.println("null");
            Label msg = new Label("Cart is Empty!");
            cartListView.getItems().add(msg);
        }
        Iterator it = cartResult.iterator();
        while(it.hasNext()){
            isLoopExecuted = true;
            System.out.println("002");
            Cart c = (Cart)it.next();
            if(!isUndiliveredVisible && c.isDelivered == 0)
                continue;
            if(!isDiliveredVisible && c.isDelivered == 1)
                continue;
            JFXListView list = new JFXListView();
            Label productID = new Label("ProductID: "+c.productID+"");
            Label productPurchaseDate = new Label("Product Purchase Date"+c.productPurchaseDate+"");
            //Label estimatedDeliveryDate = new Label(c.estimatedDeliveryDate+"");
            Label actualDeliveryDate = new Label("Delivery Date"+c.actualDeliveryDate+"");
            Label deliveryAddress = new Label("Delivery Address"+c.deliveryAddress+"");
            Label sellingPrice = new Label("Selling Price"+c.sellingPrice+"");
            Label purchasePrice = new Label("Purchase Price"+c.purchasePrice+"");
            Label shippingCharges = new Label("Shipping Charges" + c.shippingCharges+"");
            Label quantity = new Label("Quantity"+c.quantity+"");
            Label retailerUserName = new Label("Retailer Name"+c.retailerUserName+"");
            Label paymentMethod = new Label("Payment Method"+c.paymentMethod+"");
            Label offerName = new Label("Offer Name"+c.offerName+"");
            //Label isDelivered = new Label(c.isDelivered+"");

            list.getItems().addAll(productID,productPurchaseDate,actualDeliveryDate,deliveryAddress,sellingPrice,purchasePrice,shippingCharges,quantity,retailerUserName,paymentMethod,offerName);
            JFXPopup popup = new JFXPopup(list);
            JFXButton showMore = new JFXButton("Show more");
            showMore.setStyle("-fx-background-color: grey");
            showMore.setOnAction(e -> popup.show(showMore, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));
            HBox hBox = new HBox();
            hBox.getChildren().addAll(productID,productPurchaseDate,purchasePrice,showMore);
            cartListView.getItems().add(hBox);
        }
        System.out.println("003");
        if(!isLoopExecuted){
            cartListView.getItems().add(cartResult.get(0));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isDiliveredVisible = true;
        isUndiliveredVisible = true;
        isCategoryPaneVisible = false;

        setUserDropdownMenu();
        setHamburgerButton();
        setWelcomeMsg();
        currentPassword.setDisable(false);
        newPassword.setDisable(false);
        confirmPassword.setDisable(false);

        String s = Main.tempRestPoint.objects[0].toString();
        ActionEvent event = (ActionEvent) Main.tempRestPoint.objects[1];
        Main.tempRestPoint = null;
        cartResult = Main.user.getAllUserCart(Main.user.userInfo.userName);
        if(s.compareToIgnoreCase("Personal info") == 0)
            showPersonalInfoTab(event);
        else if(s.compareToIgnoreCase("cart") == 0)
            showMyCartTab(event);
            //showPurchaseHistoryTab(event);
        Main.tempRestPoint=null;
    }
}
