package com.example.rezervacnisystem.utills;
import com.example.rezervacnisystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CreateWindow {
    private static Stage stage;
    private static Scene scene;
    //private static Parent root;

    public static void Create(ActionEvent event, String fxmlFIle, int width, int height, String windowName, boolean resizable) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource(fxmlFIle));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        stage.setTitle(windowName);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setResizable(false);
        stage.show();
    }
}
