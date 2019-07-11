package sample.Controllers;

//import com.gluonhq.charm.glisten.control.CharmListView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.Product;
import sample.SceneManager;
import sample.TempRestPoint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    private boolean isCategoryPaneVisible;
    private String searchText;
    private List<Product> searchResult;
    private SceneManager sceneManager = new SceneManager();
    private boolean isAscending;
    private int typeOfSearch;                   //0 text 1 Category 2 Category and subcategory
    private String category;
    private String subcategory;

    @FXML
    private ListView searchResultView;

    @FXML
    private JFXButton categoryButton;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXHamburger userDropdownMenu;

    @FXML
    private Pane userDropMenu;

    @FXML
    private JFXButton merchantOnlyButton;

    @FXML
    private Pane categoryPane;

    @FXML
    private JFXButton sortL2H;

    @FXML
    private JFXButton sortName;

    @FXML
    private JFXButton sortH2L;

    @FXML
    private Pane categoryPane1;

    @FXML
    private Label welcomeMsg;

    @FXML
    private JFXTextField minValue;

    @FXML
    private JFXTextField maxValue;

    @FXML
    private JFXButton priceRangeButton;

    @FXML
    private JFXTreeView searchCategoryTree;

    @FXML
    void showProductBetween(){
        int minV = Integer.parseInt(minValue.getText());
        int maxV = Integer.parseInt(maxValue.getText());
        List<Product> temp = searchResult;
        searchResult= new ArrayList<Product>();
        Iterator it = temp.iterator();
        while (it.hasNext()){
            Product p = (Product)it.next();
            if(p.actualProductCost>=minV && p.actualProductCost<=maxV)
                searchResult.add(p);
        }
        showResult();
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
        try {
            sceneManager.changeScene("FXML\\userDashboard","",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToMyProfile(ActionEvent event) {
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
    void sortBrandWise(ActionEvent event) {
        isAscending = isAscending ==true?false:true;
        searchResult = null;
        if(typeOfSearch == 1) {
            searchResult = Main.user.getProductOfCategoryOrderByName(category, isAscending);
        }
        else if(typeOfSearch == 0){
            searchResult = Main.user.searchProductOrderByName(searchText, isAscending);
        }
        else{
            searchResult = Main.user.getProductOfSubCategoryOrderByName(category,subcategory,isAscending);
        }
        showResult();
    }

    @FXML
    void sortHighToLow(ActionEvent event) {
        searchResult=null;
        if(typeOfSearch == 0) {
            searchResult = Main.user.searchProductOrderByPrice(searchText, true);
        }
        else if(typeOfSearch == 1){
            searchResult = Main.user.getProductOfCategoryOrderByPrice(category,true);
        }
        else if(typeOfSearch == 2){
            searchResult = Main.user.getProductOfSubCategoryOrderByPrice(category,subcategory,true);
        }
        showResult();

    }

    @FXML
    void sortLowToHigh(ActionEvent event) {
        searchResult=null;
        if(typeOfSearch == 0) {
            searchResult = Main.user.searchProductOrderByPrice(searchText, false);
        }
        else if(typeOfSearch == 1){
            searchResult = Main.user.getProductOfCategoryOrderByPrice(category,false);
        }
        else if(typeOfSearch == 2){
            searchResult = Main.user.getProductOfSubCategoryOrderByPrice(category,subcategory,false);
        }
        showResult();
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
    void setSearchCategory(){               //Todo finish here
        int i =0;
        TreeItem root = new TreeItem("Category");
        List<List<String>> listOfList = Main.user.getSubCategory();
        List<String> mL = Main.user.getCategory();
        searchCategoryTree.setRoot(root);
        Iterator mIt = mL.iterator();
        while (mIt.hasNext() ) {
            System.out.println("-----------------");
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
                System.out.println("///////////////////////");
                TreeItem<String> item = new TreeItem<String>(it.toString());
                String name = (String)it.next();
                item.addEventHandler(MouseEvent.MOUSE_PRESSED,e -> {
                    Main.tempRestPoint = new TempRestPoint("main category and subcategory",name);
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
        System.out.println("`````````````````");
    }

    @FXML
    void searchProduct(ActionEvent event) {
        searchResult=null;
        searchText = searchBar.getText();
        searchResult = Main.user.searchProduct(searchText);
        showResult();
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
    }

    private void setUserDropdownMenu(){
        if(Main.user.userInfo.isMerchant == 0){
            merchantOnlyButton.setDisable(true);
            merchantOnlyButton.setOpacity(0);
        }
    }

    private void showResult(){
        searchResultView.getItems().clear();
        if(searchResult == null ){
            Label msg = new Label("No Result Found!");
            searchResultView.getItems().add(msg);
        }
        else {
            Iterator it = searchResult.iterator();
            while (it.hasNext()) {
                Product p = (Product) it.next();
                Label name = new Label(p.productName);
                Label price = new Label(p.actualProductCost + "");
                JFXButton buy = new JFXButton(p.productName);
                buy.setStyle("-fx-background-color: grey");
                buy.setText("Buy Now");
                buy.setOnAction(event -> {
                    System.out.println("Clicked on " + p.productName);
                    Main.tempRestPoint = new TempRestPoint("Product", p);
                    try {
                        sceneManager.changeScene("FXML\\productPage", p.productName, event);
                    } catch (IOException e) {
                        System.out.println("SearchPage: Product Page load failed!");
                    }
                });
                HBox hBox = new HBox();
                VBox vBox = new VBox();
                Label description = new Label(p.productDescription);
                //ImageView image = new ImageView(p.productImages);
                Pane spacing = new Pane();
                Pane hSpacing = new Pane();
                Pane hSpacing2 = new Pane();
                hSpacing.setMinSize(100, 50);
                hSpacing2.setMinSize(100, 50);
                spacing.setMinSize(50, 50);
                vBox.getChildren().addAll(name, spacing, price);
                //hBox.getChildren().addAll(image,vBox,buy,description);
                hBox.getChildren().addAll(vBox, hSpacing, description, hSpacing2, buy);
                searchResultView.getItems().add(hBox);
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isAscending = true;
        isCategoryPaneVisible = false;
        searchResult=null;
        if(Main.tempRestPoint != null){

            if(Main.tempRestPoint.itIs.equalsIgnoreCase("search text")){
                typeOfSearch = 0;
                searchText = (String)Main.tempRestPoint.objects[0];
                searchResult = Main.user.searchProduct(searchText);
                searchBar.setText(searchText);
            }
            else if(Main.tempRestPoint.itIs.equalsIgnoreCase("main category")){
                typeOfSearch = 1;
                searchText = (String)Main.tempRestPoint.objects[0];
                category = searchText;
                searchResult = Main.user.getProductOfCategoryOrderByName(Main.tempRestPoint.objects[0].toString(),true);
                searchBar.setText("Category: "+ Main.tempRestPoint.objects[0].toString());
            }
            else if(Main.tempRestPoint.itIs.equalsIgnoreCase("main category and subcategory")){
                typeOfSearch = 2;
                searchText = Main.tempRestPoint.objects[0] + "||" + Main.tempRestPoint.objects[1];
                category =  (String)Main.tempRestPoint.objects[0];
                subcategory = (String)Main.tempRestPoint.objects[1];
                searchResult = Main.user.getProductOfSubCategory(Main.tempRestPoint.objects[0].toString(),Main.tempRestPoint.objects[1].toString());
                searchBar.setText("Category: "+ Main.tempRestPoint.objects[0].toString() + "  ||  Subcategory: " + Main.tempRestPoint.objects[1].toString());
            }
            showResult();
        }
        Image image = new Image(getClass().getResourceAsStream("LOGO.ico"));
        categoryButton.setGraphic(new ImageView(image));

        setUserDropdownMenu();
        setHamburgerButton();
        setWelcomeMsg();
    }

}
