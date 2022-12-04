package com.example.project2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static  Stage globalstage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startpage fxml.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        globalstage = stage;
        stage.setTitle("Start Page!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void startButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        globalstage.setTitle("Game Screen");
        globalstage.setScene(scene);


    }
    public static void main(String[] args) {
        launch();
    }
}