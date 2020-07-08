package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage {

    @FXML
    private Button loginButton;

    @FXML
    private Button quitButton;

    @FXML
    private TextField usernameField;

    @FXML
    void loginButtonPressed() throws IOException {
        String username = usernameField.getText();

        if(username.isEmpty()){
            ErrorPrompt empt = new ErrorPrompt();
            System.out.println("Invalid ID : Please enter the correct username");
            //empt.setErrorMessage("Please enter student ID from S");
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("../view/errorPmpt.fxml"));

            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = new Stage();
            window.setScene(tableViewScene);
            window.show();
        }

        if (username.toUpperCase().charAt(0) == 'S') {
            try{
                //Displaying main Window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainPage.fxml"));
                Parent root = (Parent) loader.load();
                MainPage secController = loader.getController();
                secController.setUsername(username); //Here the ID entered is being sent into the main heading

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                quitButtonPressed(); //This is done so that login button is not present in the background
//                loginButton.setDisable(true);
//                usernameField.setDisable(true);





            } catch (IOException e){
                e.printStackTrace();
            }





        }
        else  {
            System.out.println("Invalid ID : Please enter the correct username");
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("../view/errorPmpt.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = new Stage();
            window.setScene(tableViewScene);
            window.show();
        }


    }

    @FXML
    void quitButtonPressed() {
        Stage login = (Stage) quitButton.getScene().getWindow();
        login.close();
    }


}