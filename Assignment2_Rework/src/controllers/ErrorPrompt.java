/*
This controller is for all the alert messages and prompt messages
 */


package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPrompt {
    String message;

    @FXML
    private Button LoginErrorButton;
    @FXML
    private Text errorMessage;

    @FXML
    void closeButtonAction() throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../view/errorPmpt.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) LoginErrorButton.getScene().getWindow();
        window.setScene(tableViewScene);
        window.close();

    }
    public void setErrorMessage(String message){
         errorMessage.setText(message);
    }


}
