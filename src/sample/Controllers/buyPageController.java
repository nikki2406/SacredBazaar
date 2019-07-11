package sample.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.LightBase;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;
import sample.Product;
import sample.SceneManager;
import sample.TempRestPoint;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class buyPageController implements Initializable {

    @FXML
    private Label welcomeMsg;

    @FXML
    private JFXHamburger userDropdownMenu;

    @FXML
    private JFXButton categoryButton;

    @FXML
    private ImageView categoryLogo;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private Pane categoryPane;

    @FXML
    private JFXTreeView<?> categoryTree;

    @FXML
    private Pane userDropMenu;

    @FXML
    private JFXButton merchantOnlyButton;

    @FXML
    private JFXTextArea deliveryAddressBox;

    @FXML
    private ToggleGroup payment;

    @FXML
    private ToggleGroup addressType;

    @FXML
    private Label productName;

    @FXML
    private Label finalAddress;

    @FXML
    private Label deliveryCost;

    @FXML
    private Label totalAmount;

    @FXML
    private Label productPrice;

    @FXML
    private Label finalQuantity;

    @FXML
    private TextField quantityTextField;

    @FXML
    private JFXRadioButton COD;

    private boolean isCategoryPaneVisible;
    private SceneManager sceneManager = new SceneManager();
    private Product p;

    @FXML
    void buy(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are sure?");
        alert.setContentText("You will have to pay "+totalAmount.getText()+".");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(!Main.user.purchaseProduct(p.productID,Main.user.userInfo.userName,finalAddress.getText(),99,Integer.parseInt(finalQuantity.getText()),"COD")){
                Main.tempRestPoint = new TempRestPoint("ErrorMsg and destination","Something went wrong. Order was not placed. Please try again after sometime.","homePage");
                try {
                    sceneManager.changeScene("FXML\\errorPrompt","",event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                sceneManager.changeScene("FXML\\homePage","",event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    @FXML
    void categoryButton(ActionEvent event) {

    }

    @FXML
    void closeApp(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void goToInventoryManagement(ActionEvent event) {

    }

    @FXML
    void goToMyCart(ActionEvent event) {
        Main.tempRestPoint = new TempRestPoint("User dashboard option","cart",event);
        try {
            sceneManager.changeScene("FXML\\userDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToMyProfile(ActionEvent event) {

    }

    @FXML
    void goToNotification(ActionEvent event) {

    }

    @FXML
    void menuClick(MouseEvent event) {

    }

    @FXML
    void searchProduct(ActionEvent event) {

    }

    @FXML
    void applyChanges(){
        finalAddress.setText(deliveryAddressBox.getText());
        finalQuantity.setText(Integer.parseInt(quantityTextField.getText())+"");
        setBuyPage();
    }

    private void setHamburgerButton(){
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(userDropdownMenu);
        burgerTask.setRate(-1);
        userDropdownMenu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            if(userDropMenu.isDisabled()) {
                userDropMenu.setDisable(false);
                userDropMenu.setOpacity(1);
            }
            else{
                userDropMenu.setDisable(true);
                userDropMenu.setOpacity(0);
            }
        });
    }

    private void setWelcomeMsg(){
        welcomeMsg.setText("Welcome, "+ Main.user.userInfo.firstName + " " + Main.user.userInfo.lastName);
    }

    private void setUserDropdownMenu(){
        if(Main.user.userInfo.isMerchant == 0){
            merchantOnlyButton.setDisable(true);
            merchantOnlyButton.setOpacity(0);
        }
    }

    private void setCategoryPane() {
        isCategoryPaneVisible = false;
        int i =0;
        TreeItem root = new TreeItem("Category");
        List<List<String>> l = Main.user.getSubCategory();
        List<String> mL = Main.user.getCategory();
        categoryTree.setRoot(root);
        Iterator mIt = mL.iterator();
        while (mIt.hasNext() ) {
            List<String> ls = l.get(i++);
            Iterator it = ls.iterator();
            TreeItem<String> rootItem = new TreeItem<String>(mIt.toString());
            rootItem.setExpanded(true);
            rootItem.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                Main.tempRestPoint = new TempRestPoint("main category",it.toString());
                try {
                    sceneManager.changeScene("FXML\\searchPage","",(ActionEvent) Main.tempRestPoint.objects[1]);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            while(it.hasNext()){
                TreeItem<String> item = new TreeItem<String>(it.toString());
                item.addEventHandler(MouseEvent.MOUSE_PRESSED,e -> {
                    Main.tempRestPoint = new TempRestPoint("main category and subcategory",it.toString());
                    try {
                        sceneManager.changeScene("FXML\\searchPage","",(ActionEvent) Main.tempRestPoint.objects[1]);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                rootItem.getChildren().add(item);
            }
            root.getChildren().add(rootItem);
        }
    }
    private void setHomePageLink(){
        Image image = new Image(getClass().getResourceAsStream("LOGO.ico"));
        categoryLogo.setImage(image);
    }
    private void setBuyPage(){
        productName.setText(p.productName);
        finalAddress.setText(Main.user.userInfo.addressLine1+", "+Main.user.userInfo.addressLine2+", "+Main.user.userInfo.addressLine3);
        productPrice.setText(p.actualProductCost+" (You saved "+ (p.actualProductCost*p.discountPercentage)+")");
        totalAmount.setText((p.actualProductCost*Integer.parseInt(finalQuantity.getText())+99)+"");
        deliveryAddressBox.setText(Main.user.userInfo.addressLine1+", "+Main.user.userInfo.addressLine2+", "+Main.user.userInfo.addressLine3);
        System.out.println("llllllll");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println('0');
        //setCategoryPane();//todo
        System.out.println('1');
        setHamburgerButton();
        System.out.println('2');
        setUserDropdownMenu();
        System.out.println('3');
        setWelcomeMsg();
        System.out.println('4');
        setHomePageLink();
        System.out.println('5');
        p = (Product)Main.tempRestPoint.objects[0];
        System.out.println('6');
        setBuyPage();

    }
}
