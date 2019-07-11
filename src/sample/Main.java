package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    public static User user;
    //public static UserInfo userInfo;
    public static Merchant merchant;
    public static TempRestPoint tempRestPoint;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\loginPage.fxml"));
        primaryStage.setTitle("Bazaar");
        primaryStage.setScene(new Scene(root, 427 , 477));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        //userInfo.newUser("kashyap"," ","Kashyap","Nasit","Male","a","a","a",new Date(29),"9559889113","k");
    }
}
