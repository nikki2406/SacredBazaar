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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    private boolean isCategoryPaneVisible = false;
    private SceneManager sceneManager = new SceneManager();
    private String searchText;
    private ActionEvent event = new ActionEvent();

    @FXML
    private ImageView siteLogo;

    @FXML
    private TextField searchBar;

    @FXML
    private JFXButton searchButton;

    @FXML
    private Pane categoryPane;

    @FXML
    private Pane userDropMenu;


    @FXML
    private Label welcomeMsg;

    @FXML
    private JFXHamburger userDropdownMenu;

    @FXML
    private JFXButton merchantOnlyButton;

    @FXML
    private ImageView categoryLogo;

    @FXML
    private JFXTreeView categoryTreeView;

    @FXML
    private JFXListView featuredProducts;

    @FXML
    private Pane upperPane;

    @FXML
    private ScrollPane mainPane;

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
    public void searchProduct(ActionEvent event){
        searchText = searchBar.getText();
        System.out.println(searchText);
        Main.tempRestPoint = new TempRestPoint("Search text",searchText);
        try {
            sceneManager.changeScene("FXML\\searchPage","Search Page", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void menuClick(){

    }
    @FXML
    public void goToSignupPage(ActionEvent event) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signupPage.fxml"));
//        Parent root1 = (Parent) fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(new Scene(root1));
//        stage.setTitle("Sign up");
//        stage.show();
//        //mainPane.setDisable(true);
        sceneManager.changeScene("signupPage", "blah",event);
    }

    @FXML
    public void goToLoginPage(ActionEvent event){
        try {
            sceneManager.changeScene("FXML\\loginPage","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void goToInventoryManagement(ActionEvent event) {
        try {
            sceneManager.changeScene("FXML\\merchantDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Main.tempRestPoint = new TempRestPoint("User dashboard option","personal info",event);
        try {
            sceneManager.changeScene("FXML\\userDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToNotification(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();
    }

    @FXML
    void goToHome(ActionEvent event){
        try {
            sceneManager.changeScene("FXML\\homePage","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHomePageLink(){
        Image image = new Image(getClass().getResourceAsStream("LOGO.ico"));
        categoryLogo.setImage(image);
        try {
            sceneManager.changeScene("FXML\\homePage","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHamburgerButton(){
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(userDropdownMenu);
        burgerTask.setRate(-1);
        userDropdownMenu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            upperPane.toFront();
            if(userDropMenu.isDisabled()) {
                userDropMenu.setDisable(false);
                userDropMenu.setOpacity(1);
                userDropMenu.toFront();
            }
            else{
                userDropMenu.setDisable(true);
                userDropMenu.setOpacity(0);
                mainPane.toFront();
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

            int i = 0;
            TreeItem root = new TreeItem("Category");
            List<List<String>> listOfList = Main.user.getSubCategory();
            List<String> mL = Main.user.getCategory();
            categoryTreeView.setRoot(root);
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
                        sceneManager.changeScene("FXML\\searchPage", "", event);
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
                            sceneManager.changeScene("FXML\\searchPage", "", event);
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

    private void setFeaturedProducts(){
        List<String> c = Main.user.getCategory();
        System.out.println("!!!!!!!!");
        Iterator cIt = c.iterator();
        while(cIt.hasNext()){
            System.out.println("@@@@@@@");
            String category = (String)cIt.next();
            List<Product> p = Main.user.showProductOfCategory(category);
            Iterator it = p.iterator();
            Label cName = new Label(category+" :");
            cName.setStyle("-fx-font-size: 15pt ; -fx-font-weight: bold;");
            featuredProducts.getItems().add(cName);
            while(it.hasNext()){
                System.out.println("#######");
                Product product = (Product) it.next();
                Label name = new Label(product.productName);
                Label price = new Label(product.actualProductCost + "");
                JFXButton buy = new JFXButton(product.productName);
                buy.setStyle("-fx-background-color: grey");
                buy.setText("Buy Now");
                buy.setOnAction(event -> {
                    System.out.println("Clicked on "+product.productName);
                    Main.tempRestPoint = new TempRestPoint("Product",product);
                    try {
                        sceneManager.changeScene("FXML\\productPage",product.productName,event);
                    } catch (IOException e) {
                        System.out.println("SearchPage: Product Page load failed!");
                    }
                });
                HBox hBox = new HBox();
                VBox vBox = new VBox();
                Label description = new Label(product.productDescription);
                //ImageView image = new ImageView(p.productImages);
                Pane spacing = new Pane();
                Pane hSpacing = new Pane();
                Pane hSpacing2 = new Pane();
                hSpacing.setMinSize(100,50);
                hSpacing2.setMinSize(100,50);
                spacing.setMinSize(50,50);
                vBox.getChildren().addAll(name,spacing,price);
                //hBox.getChildren().addAll(image,vBox,buy,description);
                hBox.getChildren().addAll(vBox,hSpacing,description,hSpacing2,buy);
                featuredProducts.getItems().add(hBox);
            }
            /*Get products of respective category in form of list.
            Set Iterator on the.
            Create a label with category name with help of Cit.
            create product list as it is in search page.
            add them to list.
            add some space.
             */
        }
        System.out.println("%%%%");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image logo = new Image(getClass().getResourceAsStream("logo.jpeg"));
        siteLogo.setImage(logo);
        setHamburgerButton();
        setWelcomeMsg();
//        setHomePageLink();
        setUserDropdownMenu();
        setCategoryPane();
        setFeaturedProducts();
    }

}
