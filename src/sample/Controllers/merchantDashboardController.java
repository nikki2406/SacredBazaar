package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.*;

import javax.swing.text.EditorKit;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class merchantDashboardController implements Initializable {

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
    private JFXTreeView categoryTree;

    @FXML
    private Pane userDropMenu;

    @FXML
    private JFXButton merchantOnlyButton;

    @FXML
    private JFXTextField productName;

    @FXML
    private JFXTextField brandName;

    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXTextArea productDiscription;

    @FXML
    private JFXTextField actualProductCost;

    @FXML
    private JFXTextField mechantCost;

    @FXML
    private JFXTextField discountPercentage;

    @FXML
    private JFXDatePicker discountFrom;

    @FXML
    private JFXDatePicker discountTo;

    @FXML
    private JFXTextField discountLabel;

    @FXML
    private JFXButton prodctImage;

    @FXML
    private JFXButton prodctImage1;

    @FXML
    private JFXButton prodctImage2;

    @FXML
    private JFXButton prodctImage3;

    @FXML
    private Label image;

    @FXML
    private Label image1;

    @FXML
    private Label image2;

    @FXML
    private Label image3;

    @FXML
    private JFXListView productList;

    @FXML
    private AnchorPane prompt;

    @FXML
    private JFXPasswordField password;

    @FXML
    private AnchorPane listPane;

    @FXML
    private JFXDatePicker manufacturingDate;

    @FXML
    private JFXDatePicker expiryDate;

    @FXML
    private JFXTextField productCategory;

    @FXML
    private JFXTextField productSubcategory;

    @FXML
    private Group productPane;

    private List<Product> productListResult;
    private SceneManager sceneManager = new SceneManager();
    private boolean isCategoryPaneVisible;
    private String searchText;
    private Product p;
    private boolean isNew;
    private File[] images;
    private byte[][] byteImages;
    private String[] stringImages;


    @FXML
    public void categoryButton(ActionEvent event){
        if(!isCategoryPaneVisible) {
            categoryPane.setDisable(false);
            categoryPane.setOpacity(1);
            isCategoryPaneVisible = true;
        }
        else{
            categoryPane.setDisable(true);
            categoryPane.setOpacity(0);
            isCategoryPaneVisible = false;
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void finalSave(ActionEvent event) {
        if(Main.user.loginUser(Main.user.userInfo.userName, Hash.getSha256(password.getText()))) {
            productPane.setDisable(true);
            productPane.setOpacity(0);
            productPane.toBack();
            listPane.setDisable(false);
            listPane.setOpacity(1);
            listPane.toFront();
            p.productName = productName.getText();
            p.merchantName = Main.user.userInfo.userName;
            p.brand = brandName.getText();
            p.quantity = Integer.parseInt(quantity.getText());
            p.actualProductCost = Double.parseDouble(actualProductCost.getText());
            p.yourCost = Double.parseDouble(mechantCost.getText());
            p.expiryDate = Date.valueOf(expiryDate.getValue());
            p.manufactureDate = Date.valueOf(manufacturingDate.getValue());
            p.discountPercentage = Integer.parseInt(discountPercentage.getText());
            p.discountPeriod = Timestamp.valueOf(String.valueOf(discountTo.getValue()));
            p.discountLabel = discountLabel.getText();
            p.productDescription = productDiscription.getText();
            p.productImages = stringImages[0];
            if (isNew) {
                Main.merchant.addProduct(p);
            } else {
                Main.merchant.removeProduct(((Product) Main.tempRestPoint.objects[0]).productID);
                Main.merchant.addProduct(p);
            }
        }
        else{
            password.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void goToInventoryManagement(ActionEvent event) {
        productListResult=Main.merchant.getMerchantProduct(Main.user.userInfo.userName);
        productPane.setDisable(true);
        productPane.setOpacity(0);
        productPane.toBack();
        listPane.setDisable(false);
        listPane.setOpacity(1);
        listPane.toFront();
    }

    @FXML
    void goToMyCart(ActionEvent event) {
        Main.tempRestPoint = new TempRestPoint("cart","cart",event);
        try {
            sceneManager.changeScene("FXML\\userDashboardPage","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToMyProfile(ActionEvent event) {
        Main.tempRestPoint = new TempRestPoint("Personal Info","personal Info",event);
        try {
            sceneManager.changeScene("FXML\\UserDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToNotification(ActionEvent event) {

    }

    @FXML
    void menuClick(MouseEvent event) {

    }

    @FXML
    void saveProduct(ActionEvent event) {
        prompt.setDisable(false);
        prompt.setOpacity(1);
        prompt.toFront();
        listPane.setDisable(true);
        productPane.setDisable(true);
    }

    @FXML
    void addProduct(){
        productPane.setDisable(false);
        productPane.setOpacity(1);
        productPane.toFront();
        listPane.setDisable(true);
        listPane.setOpacity(0);
        listPane.toBack();
        productPane.setDisable(true);
        productPane.setOpacity(0);
        productPane.toBack();
    }

    @FXML
    void browseImage(){
        selectImage(0);
//        try {
//            // Reading a Image file from file system
//            FileInputStream imageInFile = new FileInputStream(images[0]);
//            byteImages[0] = new byte[(int) images[0].length()];
//            imageInFile.read(byteImages[0]);
//
//            // Converting Image byte array into Base64 String
//            stringImages[0] = Product.encodeImage(byteImages[0]);
//
//            System.out.println("Image Successfully Manipulated!");
//        } catch (FileNotFoundException e) {
//            System.out.println("Image not found" + e);
//        } catch (IOException ioe) {
//            System.out.println("Exception while reading the Image " + ioe);
//        }
        stringImages[0] = "mmm";
    }

    @FXML
    void browseImage1(){}

    @FXML
    void browseImage2(){}

    @FXML
    void browseImage3(){}

    @FXML
    void searchProduct(ActionEvent event) {
        searchText = searchBar.getText();
        Main.tempRestPoint = new TempRestPoint("Search text",searchText);
        try {
            sceneManager.changeScene("FXML\\searchPage","Search Page",event);
        } catch (IOException e) {
            System.out.println("ProductPage: search page load failed.");
        }

    }

    @FXML
    void top5Users(ActionEvent event){
        List<MerchantHistory> merchantHistories = Main.merchant.getAllMerchantHistory(Main.merchant.userInfo.userName);
        Iterator iterator = merchantHistories.iterator();
        String[] userNames = new String[1000];
        double[] cost = new double[1000];
        while (iterator.hasNext()){
            MerchantHistory merchantHistory = (MerchantHistory)iterator.next();
            int c=0;
            int add = 0;
            boolean flag = false;
            while(c<=add){
                String uName = userNames[c];
                if(uName.equals(merchantHistory.getBuyerUserName())){
                    flag = true;
                    cost[c]+=merchantHistory.getQuantity()*merchantHistory.product.yourCost;
                }
                c++;
            }
            if(!flag){
                userNames[add] = merchantHistory.getBuyerUserName();
                cost[add++] = merchantHistory.getQuantity()*merchantHistory.product.yourCost;
            }
        }
    }

    private void selectImage(int i){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Choose a image file", "*.jpeg");
        fileChooser.setSelectedExtensionFilter(filter);
        images[i] = fileChooser.showOpenDialog(null);
    }
    private void setHomePageLink(){
        Image image = new Image(getClass().getResourceAsStream("LOGO.ico"));
        categoryLogo.setImage(image);
    }
    private void showResult(){
        if(productListResult == null ){
            Label msg = new Label("No Product Found!");
            productList.getItems().add(msg);
        }
        else {
            Iterator it = productListResult.iterator();
            while (it.hasNext()) {
                Product p = (Product) it.next();
                Label productName = new Label(p.productName);
                Label brand = new Label(p.brand);
                Label quantity = new Label(p.quantity+"");
                Label actualProductCost = new Label(p.actualProductCost +"");
                Label yourCost = new Label(p.yourCost+"");
                Label expiryDate = new Label(p.expiryDate+"");
                Label manufactureDate = new Label(p.manufactureDate+"");
                Label productCategory = new Label(p.productCategory);
                Label productSubCategory = new Label(p.productSubCategory);
                Label discountPercentage = new Label(p.discountPercentage+"");
                Label discountPeriod = new Label(p.discountPeriod+"");
                Label discountLabel = new Label(p.discountLabel);
                Label productDescription = new Label(p.productDescription);
                JFXButton edit = new JFXButton("Edit");
                edit.setStyle("-fx-background-color: grey");
                edit.setOnAction(event -> {
                    System.out.println("Clicked on " + p.productName);
                    Main.tempRestPoint = new TempRestPoint("Product", p);
                    listPane.setDisable(true);
                    listPane.setOpacity(0);
                    productPane.setDisable(false);
                    productCategory.setOpacity(1);
                    setProduct();
                });
                HBox hBox = new HBox();

                hBox.getChildren().addAll(edit,productName,brand,quantity,actualProductCost,yourCost,expiryDate,manufactureDate,productCategory,productSubCategory,discountPercentage,discountPeriod,discountLabel,productDescription);
                productList.getItems().add(hBox);
            }
        }
    }



    private void setProduct() {
        productName.setText(((Product)Main.tempRestPoint.objects[0]).productName);
        brandName.setText(((Product)Main.tempRestPoint.objects[0]).brand);
        quantity.setText(((Product)Main.tempRestPoint.objects[0]).quantity+"");
        actualProductCost.setText(((Product)Main.tempRestPoint.objects[0]).actualProductCost+"");
        productDiscription.setText(((Product)Main.tempRestPoint.objects[0]).productDescription);
        //manufacturingDate.setValue((LocalDate)((Product)Main.tempRestPoint.objects[0]).manufactureDate);
        productCategory.setText(((Product)Main.tempRestPoint.objects[0]).productCategory);
        productSubcategory.setText(((Product)Main.tempRestPoint.objects[0]).productSubCategory);
        actualProductCost.setText(((Product)Main.tempRestPoint.objects[0]).actualProductCost+"");
        mechantCost.setText(((Product)Main.tempRestPoint.objects[0]).yourCost+"");
        discountPercentage.setText(((Product)Main.tempRestPoint.objects[0]).discountPercentage+"");
        discountLabel.setText(((Product)Main.tempRestPoint.objects[0]).discountLabel);
    }
    private void setUserDropdownMenu(){
        if(Main.user.userInfo.isMerchant == 0){
            merchantOnlyButton.setDisable(true);
            merchantOnlyButton.setOpacity(0);
        }
    }

    private void setHamburgerButton(){
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(userDropdownMenu);
        burgerTask.setRate(-1);
        userDropdownMenu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            //upperPane.toFront();
            if(userDropMenu.isDisabled()) {
                userDropMenu.setDisable(false);
                userDropMenu.setOpacity(1);
                userDropMenu.toFront();
            }
            else{
                userDropMenu.setDisable(true);
                userDropMenu.setOpacity(0);
                //mainPane.toFront();
            }
        });
    }

    private void setCategoryPane() {
        isCategoryPaneVisible = false;

        int i = 0;
        TreeItem root = new TreeItem("Category");
        List<List<String>> listOfList = Main.user.getSubCategory();
        List<String> mL = Main.user.getCategory();
        categoryTree.setRoot(root);
        Iterator mIt = mL.iterator();
        while (mIt.hasNext()) {
            System.out.println("-----------------");
            List<String> listOfSubcategory = listOfList.get(i++);
            Iterator it = listOfSubcategory.iterator();
            String category = (String) mIt.next();
            TreeItem<String> rootItem = new TreeItem<String>(category);
            rootItem.setExpanded(true);
            rootItem.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                Main.tempRestPoint = new TempRestPoint("main category", category);
                try {
                    sceneManager.changeScene("FXML\\searchPage", "", new ActionEvent());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            while (it.hasNext()) {
                System.out.println("///////////////////////");
                TreeItem<String> item = new TreeItem<String>(it.toString());
                String name = (String) it.next();
                item.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                    Main.tempRestPoint = new TempRestPoint("main category and subcategory", name);
                    try {
                        sceneManager.changeScene("FXML\\searchPage", "", new ActionEvent());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                rootItem.getChildren().add(item);
            }
            root.getChildren().add(rootItem);
        }
        System.out.println("`````````````````");

    }

    private void setWelcomeMsg(){
        welcomeMsg.setText("Welcome, "+ Main.user.userInfo.firstName + " " + Main.user.userInfo.lastName);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        listPane.setOpacity(1);
//        listPane.setDisable(false);
//        productPane.setOpacity(0);
//        productPane.setDisable(true);
//        prompt.setOpacity(0);
//        prompt.setDisable(true);
        stringImages = new String[4];
        images = new File[4];
        isNew = false;
        isCategoryPaneVisible = false;
        productListResult =  Main.merchant.getMerchantProduct(Main.user.userInfo.userName);

        showResult();
        //setHomePageLink();
        setUserDropdownMenu();
        setHamburgerButton();
        setWelcomeMsg();
        setCategoryPane();
    }
}
