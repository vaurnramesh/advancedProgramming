/*
This is the controller for developer Information

 */


package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class DevopsInfo {

    @FXML
    private Button backButtonDevops;


    public void backButtonDevopsPressed(javafx.event.ActionEvent event) {
        Stage devops = (Stage) backButtonDevops.getScene().getWindow();
        devops.close();
    }
}
