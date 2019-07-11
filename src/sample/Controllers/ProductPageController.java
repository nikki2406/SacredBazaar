package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;
import sample.Product;
import sample.SceneManager;
import sample.TempRestPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {

    private Product p;
    private boolean isCategoryPaneVisible;
    private String searchText;
    private SceneManager sceneManager = new SceneManager();


    @FXML
    private JFXTreeView categotyTreeView;

    @FXML
    private JFXButton categoryButton1;

    @FXML
    private ImageView categoryLogo;

    @FXML
    private TextField searchBar;

    @FXML
    private JFXButton searchButton;

    @FXML
    private Label welcomeMsg;

    @FXML
    private JFXHamburger userDropdownMenu;

    @FXML
    private Pane categoryPane;

    @FXML
    private JFXListView<?> userMenu;

    @FXML
    private JFXButton privious;

    @FXML
    private JFXButton next;

    @FXML
    private JFXButton buyButton;

    @FXML
    private JFXButton addCartButton;

    @FXML
    private Label productName;

    @FXML
    private Label price;

    @FXML
    private Label sellerName;

    @FXML
    private Label productDeiscription;

    @FXML
    private Pane userDropMenu;

    @FXML
    private JFXButton merchantOnlyButton;

    @FXML
    private JFXTreeView searchCategory;

    @FXML
    private ImageView productImage;

    @FXML
    void addToCart(ActionEvent event) {
        if(Main.user.userInfo.userName == null){
            Main.tempRestPoint = new TempRestPoint("Where,what",p,event,"FXML\\productPage");
            try {
                sceneManager.changeScene("FXML\\loginPage","",event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            if (!Main.user.addProductToCart(p, Main.user.userInfo.userName)) {
                Main.tempRestPoint = new TempRestPoint("Errormsg and destination", "Something went wrong. Try again latter.", "homePage");
            }
        }
    }

    @FXML
    void buyNow(ActionEvent event) {
        if(Main.user.userInfo.userName == null){
            Main.tempRestPoint = new TempRestPoint("Where,what",p,event,"FXML\\productPage");
            try {
                sceneManager.changeScene("FXML\\loginPage","",event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.tempRestPoint = new TempRestPoint("Product",p,event);
        try {
            sceneManager.changeScene("FXML\\buyPage","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void closeApp(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void goToInventoryManagement(ActionEvent event) {

    }

    @FXML
    void goToMyCart(ActionEvent event) {
        if(Main.user.userInfo.userName == null){
            Main.tempRestPoint = new TempRestPoint("what.where","Cart",event,"FXML\\UserDashboard");
            try {
                sceneManager.changeScene("FXML\\loginPage","",event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.tempRestPoint = new TempRestPoint("Cart","Cart");
        try {
            sceneManager.changeScene("FXML\\userDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToMyProfile(ActionEvent event) {
        if(Main.user.userInfo.userName == null){
            Main.tempRestPoint = new TempRestPoint("Where,what","personal info",event,"FXML\\userDashboard");
            try {
                sceneManager.changeScene("FXML\\loginPage","",event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.tempRestPoint = new TempRestPoint("personal info","personal info");
        try {
            sceneManager.changeScene("FXML\\userDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToNotification(ActionEvent event) {

    }

    @FXML
    void loadNextImage(ActionEvent event) {

    }

    @FXML
    void loadPreviousImage(ActionEvent event) {

    }

    @FXML
    void menuClick(MouseEvent event) {

    }

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
    void showUserMenu(MouseEvent event) {

    }

    private void setSearchCategory(){ //Todo
    }

    private void setCategoryPane() {
        isCategoryPaneVisible = false;
        int i =0;
        TreeItem root = new TreeItem("Category");
        List<List<String>> listOfList = Main.user.getSubCategory();
        List<String> mL = Main.user.getCategory();
        categotyTreeView.setRoot(root);
        Iterator mIt = mL.iterator();
        while (mIt.hasNext() ) {
            List<String> listOfSubcategory = listOfList.get(i++);
            Iterator it = listOfSubcategory.iterator();
            String category = (String)mIt.next();
            TreeItem<String> rootItem = new TreeItem<String>(category);
            rootItem.setExpanded(true);
            rootItem.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                Main.tempRestPoint = new TempRestPoint("main category",category);
                try {
                    sceneManager.changeScene("FXML\\searchPage","",new ActionEvent());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            while(it.hasNext()){
                TreeItem<String> item = new TreeItem<String>(it.toString());
                String name = (String)it.next();
                item.addEventHandler(MouseEvent.MOUSE_PRESSED,e -> {
                    Main.tempRestPoint = new TempRestPoint("main category and subcategory",category,name);
                    try {
                        sceneManager.changeScene("FXML\\searchPage","",new ActionEvent());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                rootItem.getChildren().add(item);
            }
            root.getChildren().add(rootItem);
        }
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
                userMenu.toBack();
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        p = (Product) Main.tempRestPoint.objects[0];
        Main.tempRestPoint=null;
        productDeiscription.setText(p.productDescription);
        productName.setText(p.productName);
        price.setText(p.actualProductCost+"");
        sellerName.setText(p.merchantName);
        ByteArrayInputStream bis = new ByteArrayInputStream(Product.decodeImage(p.productImages));
        BufferedImage bImage2;
        try {
            bImage2 = ImageIO.read(bis);
         //   productImage.setImage(Image.impl_fromPlatformImage(bImage2));//todo
        } catch (IOException e) {
            e.printStackTrace();
        }

        setWelcomeMsg();
        setHamburgerButton();
        setCategoryPane();
        setUserDropdownMenu();
    }
}
