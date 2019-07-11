package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginPageController implements Initializable {

    SceneManager sceneManager = new SceneManager();

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton forgotPasswordButton;

    @FXML
    private JFXButton creatAccount;

    @FXML
    private Label errorLabel;

    @FXML
    void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void goToSignPage(ActionEvent event) {
        try {
            Main.user.userInfo = new UserInfo();
            sceneManager.changeScene("FXML\\signupPage", "Sign UP", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void login(ActionEvent event) {
//        if(Main.user==null){
//            System.out.println("its null");
//        }
        Main.user.userInfo = Main.user.getUserInfo(username.getText(), Hash.getSha256(password.getText()));

        if (Main.user.userInfo == null) {
//            if (false) {
            username.setStyle("-jfx-unfocus-color: red");
            password.setStyle("-jfx-unfocus-color: red");
            errorLabel.setText("User not found!");
        } else {
            if(Main.user.userInfo.isMerchant==1){
                Main.merchant = new Merchant();
                Main.merchant.merchantInfo = Main.merchant.getMerchantInfo(username.getText());
            }
//            Main.user.createUser(userInfo);
            try {
                sceneManager.changeScene("FXML\\homePage", "Home Page", event);
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Login Page: HomePage load fail.");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Enter Server's IP: localhost");
        Scanner scanner = new Scanner(System.in);
        String ip = "localhost";//scanner.next();
        System.out.println("Enter Server's port: 1234");
        int port = 1234;//scanner.nextInt();
        Main.user = new User();
        System.out.println("Connecting to server");
        Main.user.connectToServer(ip, port);
    }

}
