package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class SceneManager {
    //tackle exception during call
    public void changeScene(String sceneName, String title, ActionEvent event) throws IOException {

        Parent home_parent = null;
        try {
            home_parent = FXMLLoader.load(getClass().getResource(sceneName+".fxml"));
            Scene Home = new Scene(home_parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.setTitle(title);
            //window.setResizable(false);
//            window.initStyle(StageStyle.UNDECORATED);
//            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setX(30);
            window.setY(10);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    void changeScene(String sceneName) throws IOException {
//        changeScene(sceneName,sceneName);
//    }
}