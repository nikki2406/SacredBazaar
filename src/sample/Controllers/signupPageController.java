package sample.Controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Date;
import java.sql.Timestamp;


public class signupPageController implements Runnable, Initializable {

    private SceneManager sceneManager = new SceneManager();

    @FXML
    public JFXTextField firstNameField;
    @FXML
    public JFXTextField lastNameField;
    @FXML
    public JFXDatePicker dobDatePicker;
    @FXML
    public JFXComboBox genderComboBox = new JFXComboBox();

    @FXML
    public JFXTextField phoneNumberField;
    @FXML
    public JFXTextField emailField;
    @FXML
    public JFXTextField addressLine1Field;
    @FXML
    public JFXTextField addressLine2Field;
    @FXML
    public JFXTextField addressLine3Field;

    @FXML
    public JFXTextField usernameField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    public JFXPasswordField confirmPasswordField;

    @FXML
    public JFXButton iAgreeButton;

    @FXML
    public AnchorPane pane1;
    @FXML
    public AnchorPane pane2;
    @FXML
    public AnchorPane pane3;
    @FXML
    public AnchorPane pane4;
    @FXML
    public AnchorPane pane5;

    @FXML
    public JFXButton menuButton1;
    @FXML
    public JFXButton menuButton2;
    @FXML
    public JFXButton menuButton3;
    @FXML
    public JFXButton menuButton4;
    @FXML
    public JFXButton menuButton5;
    @FXML
    public JFXComboBox merchantOption;
    @FXML
    public Label l;
    @FXML
    public JFXTextField accountHolderName;
    @FXML
    public JFXTextField accountNumber;
    @FXML
    public JFXTextField PANNumber;
    @FXML
    public JFXTextField bankName;
    @FXML
    public JFXTextField IFCSCode;

    public void setFrameFalse(){
        menuButton1.setBackground(new Background(new BackgroundFill(Color.web("#21618c"), CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton2.setBackground(new Background(new BackgroundFill(Color.web("#21618c"), CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton3.setBackground(new Background(new BackgroundFill(Color.web("#21618c"), CornerRadii.EMPTY, Insets.EMPTY)));
//        menuButton4.setBackground(new Background(new BackgroundFill(Color.web("#21618c"), CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton1.setTextFill(Color.WHITE);
        menuButton2.setTextFill(Color.WHITE);
        menuButton3.setTextFill(Color.WHITE);
//        menuButton4.setTextFill(Color.WHITE);
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(false);
        pane5.setVisible(false);
    }

    public void initGenderComboBox(){
        genderComboBox.setMaxHeight(100);
        genderComboBox.getItems().add("Male");
        genderComboBox.getItems().add("Female");
        genderComboBox.getItems().add(" ̄ ̄\\_(ツ)_/ ̄ ̄");
    }

    @FXML
    public void menuButton1OnClickAction(){
        setFrameFalse();
        menuButton1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton1.setTextFill(Color.web("#21618c"));
        pane1.setVisible(true);
        System.out.println("1");
    }
    @FXML
    public void menuButton2OnClickAction(){
        setFrameFalse();
        menuButton2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton2.setTextFill(Color.web("#21618c"));
        pane2.setVisible(true);
        System.out.println("2");
    }
    @FXML
    public void menuButton3OnClickAction(){
        setFrameFalse();
        menuButton3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton3.setTextFill(Color.web("#21618c"));
        pane3.setVisible(true);
        System.out.println("3");
    }
    @FXML
    public void menuButton4OnClickAction(){
        setFrameFalse();
        menuButton4.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton4.setTextFill(Color.web("#21618c"));
        pane4.setVisible(true);
        System.out.println("4");
    }
    @FXML
    public void menuButton5OnClickAction(){
        setFrameFalse();
        menuButton5.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuButton5.setTextFill(Color.web("#21618c"));
        pane5.setVisible(true);
        System.out.println("5");
    }

    public void iAgreeAction(ActionEvent event){
        boolean valid = true;
        if(firstNameField.getText().isEmpty()||dobDatePicker.getValue() == null||genderComboBox.getValue() == null || lastNameField.getText().isEmpty()){
            menuButton1OnClickAction();
            if(firstNameField.getText().isEmpty()) firstNameField.setStyle("-jfx-unfocus-color: red");
            System.out.println(dobDatePicker.getValue());
            if(dobDatePicker.getValue() == null) dobDatePicker.setStyle("-fx-border-color: red");
            if(genderComboBox.getValue() == null) genderComboBox.setStyle("-jfx-unfocus-color: red");
            if(lastNameField.getText().isEmpty()) lastNameField.setStyle("-jfx-unfocus-color: red");
            return;
        }
        if(phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()){
            menuButton2OnClickAction();
            if(phoneNumberField.getText().isEmpty()) phoneNumberField.setStyle("-jfx-unfocus-color: red");
            if(emailField.getText().isEmpty()) emailField.setStyle("-jfx-unfocus-color: red");
            return;
        }
        if(addressLine1Field.getText().isEmpty() || addressLine2Field.getText().isEmpty() || addressLine3Field.getText().isEmpty()){
            menuButton2OnClickAction();
            if(addressLine1Field.getText().isEmpty()) addressLine1Field.setStyle("-jfx-unfocus-color: red");
            if(addressLine2Field.getText().isEmpty()) addressLine2Field.setStyle("-jfx-unfocus-color: red");
            if(addressLine3Field.getText().isEmpty()) addressLine3Field.setStyle("-jfx-unfocus-color: red");
            return;
        }
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() || !(passwordField.getText().equals(confirmPasswordField.getText()))){
            menuButton3OnClickAction();
            if(usernameField.getText().isEmpty()) usernameField.setStyle("-jfx-unfocus-color: red");
            if(passwordField.getText().isEmpty()) passwordField.setStyle("-jfx-unfocus-color: red");
            if(confirmPasswordField.getText().isEmpty()) confirmPasswordField.setStyle("-jfx-unfocus-color: red");
            if(!(passwordField.getText().equals(confirmPasswordField.getText()))){
                passwordField.setText("");
                confirmPasswordField.setText("");
                passwordField.setStyle("-jfx-unfocus-color: red");
                confirmPasswordField.setStyle("-jfx-unfocus-color: red");
            }
            return;
        }
        if((merchantOption.getSelectionModel().getSelectedItem().toString().compareToIgnoreCase("Yes")==0) || accountHolderName.getText().isEmpty() || accountNumber.getText().isEmpty() || PANNumber.getText().isEmpty() || bankName.getText().isEmpty() || IFCSCode.getText().isEmpty()){
            menuButton4OnClickAction();
            if(accountHolderName.getText().isEmpty()){ accountHolderName.setStyle("-jfx-unfocus-color: red");}
            if(accountNumber.getText().isEmpty()) accountNumber.setStyle("-jfx-unfocus-color: red");
            if(PANNumber.getText().isEmpty()) PANNumber.setStyle("-jfx-unfocus-color: red");
            if(bankName.getText().isEmpty()) bankName.setStyle("-jfx-unfocus-color: red");
            if(IFCSCode.getText().isEmpty()) IFCSCode.setStyle("-jfx-unfocus-color: red");
            return;
        }
        /*
        String output = genderComboBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(output);

        String date = dobDatePicker.getValue().toString();
        System.out.println(date);
        */
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        UserInfo userInfo = new UserInfo();

        userInfo.newUser(usernameField.getText(),
                Hash.getSha256(passwordField.getText()),
                firstNameField.getText(),
                (lastNameField.getText().equals(""))?"":lastNameField.getText(),
                genderComboBox.getSelectionModel().getSelectedItem().toString(),
                addressLine1Field.getText(),
                addressLine2Field.getText(),
                addressLine3Field.getText(),
                Date.valueOf(dobDatePicker.getValue()),
                phoneNumberField.getText(),
                emailField.getText());
        Main.user.createUser(userInfo);
        //todo maybe its no in if condition
        if(merchantOption.getSelectionModel().getSelectedItem().toString().compareToIgnoreCase("yes") == 0){
            Main.merchant.merchantInfo.userName = usernameField.getText();
            Main.merchant.merchantInfo.accountHolderName = accountHolderName.getText();
            Main.merchant.merchantInfo.accountNumber = accountNumber.getText();
            Main.merchant.merchantInfo.bankName = bankName.getText();
            Main.merchant.merchantInfo.IFSC = IFCSCode.getText();
            Main.merchant.merchantInfo.panNumber = PANNumber.getText();
        }
        try {
            sceneManager.changeScene("FXML\\loginPage","Home Page",event);
        } catch (IOException e) {
            System.out.println("Sign up Page: User created but loginPage load failed!");
        }
    }

    @FXML
    public void close(ActionEvent event){
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    @Override
    public void run() {

    }
    private void initMerchantOption(){
        merchantOption.getItems().clear();
        merchantOption.getItems().addAll("Yse","No");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initGenderComboBox();
        initMerchantOption();
        setFrameFalse();
        menuButton1.setTextFill(Color.web("#21618c"));
        menuButton1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane1.setVisible(true);
    }
}
