/*
 * Advanced Programming
 *
 * 1.0.1
 *
 * This assignment is solely done by Varun Ramesh (s3793675) and the assignment is subjected to copyright
 */




package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.UniLink;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        UniLink uniLinkOb = new UniLink();

        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        uniLinkOb.startProgram();

    }

}
