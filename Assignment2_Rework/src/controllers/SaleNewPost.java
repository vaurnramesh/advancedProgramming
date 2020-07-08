package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.UniLink;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class SaleNewPost {
    static String username;
    UniLink uniLinkOb = new UniLink();

    @FXML
    private Button backSale;

    @FXML
    private ImageView saleImage;

    @FXML
    private Button uploadSaleImage;

    @FXML
    private TextField askingPricee;

    @FXML
    private TextField minimumRaisee;

    @FXML
    private Button saveSale;

    @FXML
    private TextField saleDescriptionn;

    @FXML
    private TextField saleNamee;

    @FXML
    private DialogPane dialogBoxSale;

    @FXML
    private TextField uploadImageBrowser;


    public void errorEmpty(String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/errorPmpt.fxml"));
        Parent escreen = loader.load();
        ErrorPrompt screen = loader.getController();
        screen.setErrorMessage(message);
        Stage stage = new Stage();
        stage.setTitle("Error!");
        stage.setScene(new Scene(escreen));
        stage.setResizable(false);
        stage.show();

    }

    public void backSalePressed(ActionEvent event) throws IOException {
        Stage newSale = (Stage) backSale.getScene().getWindow();
        newSale.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainPage.fxml"));
        Parent root = (Parent) loader.load();
        MainPage secController = loader.getController();
        secController.setUsername(username);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void uploadSaleImagePressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );
        File file = fileChooser.showOpenDialog(dialogBoxSale.getScene().getWindow());

        if (file != null) {
            // ImageUploadButton is the TextField fx:id
            uploadImageBrowser.setText(file.getPath());
            //String imageEvent = imageUploadButton.getText();


        } else {
            System.out.println("error"); // or something else
        }

    }

    public void saveSalePressed(ActionEvent event) throws IOException {
        String saleName = this.saleNamee.getText();
        String saleDescription = this.saleDescriptionn.getText();
        String askingPrice = this.askingPricee.getText();
        String minimumRaise = this.minimumRaisee.getText();
        String saleImage = this.uploadImageBrowser.getText();


        if (saleName.isEmpty()) {
            try {
                errorEmpty("Sale Name cannot be empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (saleDescription.isEmpty()) {
            try {
                errorEmpty("Sale Description cannot be empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else  if (!checkInt(askingPrice)|| askingPrice.isEmpty()) {
            try {
                errorEmpty("Asking Price cannot be empty or negative");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(Double.parseDouble(askingPrice) < 0){
            try{
                errorEmpty("Asking Price cannot be negative");
            } catch(IOException e){
                e.printStackTrace();
            }
        } else if (!checkInt(minimumRaise)||minimumRaise.isEmpty()) {
            try {
                errorEmpty("Minimum Raise cannot be empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(Double.parseDouble(minimumRaise) < 0){
            try{
                errorEmpty("Minimum Raise cannot be negative");
            } catch(IOException e){
                e.printStackTrace();
            }
        } else if (saleImage.isEmpty()) {
            //saleImage = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\saleImage.png";

            saleImage = "src/images/saleImage.png";
            uniLinkOb.addSalePost(saleName, saleDescription, username, "Open", Double.parseDouble(askingPrice), Double.parseDouble(minimumRaise), saleImage);
            errorEmpty("Your sale post has been added");
        } else {
            String ext = FilenameUtils.getExtension(saleImage);
            File source = new File(saleImage);
            //saleImage = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\"+"sale_"+saleName+""+username+"."+ext;
            saleImage = "src/images/"+"sale_"+saleName+""+username+"."+ext;

            File destination = new File(saleImage);
            try {
                FileUtils.copyFile(source,destination);
            } catch (Exception e){
                e.printStackTrace();
            }
            uniLinkOb.addSalePost(saleName, saleDescription, username, "Open", Double.parseDouble(askingPrice), Double.parseDouble(minimumRaise), saleImage);
            errorEmpty("Your sale post has been added");
        }

    }

    public String setUsernameSale(String username) {
        this.username = username;
        return username;

    }
    private boolean checkInt(String input) throws IOException {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception ex) {

            return false;
        }
    }
}
