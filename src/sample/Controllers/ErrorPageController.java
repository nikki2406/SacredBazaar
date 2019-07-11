package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import sample.Main;
import sample.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPageController implements Initializable {

    SceneManager sceneManager = new SceneManager();
    @FXML
    private Label msg;

    @FXML
    void closeErrorMsg(ActionEvent event) {
        String loc = Main.tempRestPoint.objects[1].toString();
        try {
            sceneManager.changeScene("FXML\\"+loc,",",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String MSG = (String)Main.tempRestPoint.objects[0];
        msg.setText(MSG);

    }
}
