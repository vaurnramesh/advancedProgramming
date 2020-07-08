package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.UniLink;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class JobNewPost {
    static String username;
    static String message;
    UniLink uniLinkOb = new UniLink();

    @FXML
    private Button backJob;

    @FXML
    private ImageView saleImage;

    @FXML
    private Button uploadJobImage;

    @FXML
    private TextField proposedPricee;

    @FXML
    private Button saveJob;

    @FXML
    private TextField jobdescriptionn;

    @FXML
    private TextField jobNamee;

    @FXML
    private DialogPane dialogBoxJob;

    @FXML
    private TextField browseJobImage;


    public void backJobPressed(ActionEvent event) throws IOException {
        Stage newJob = (Stage) backJob.getScene().getWindow();
        newJob.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainPage.fxml"));
        Parent root = (Parent) loader.load();
        MainPage secController = loader.getController();
        secController.setUsername(username);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void uploadJobImagePressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );
        File file = fileChooser.showOpenDialog(dialogBoxJob.getScene().getWindow());

        if (file != null) {
            // ImageUploadButton is the TextField fx:id
            browseJobImage.setText(file.getPath());
            //String imageEvent = imageUploadButton.getText();


        } else  {
            System.out.println("error"); // or something else
        }

    }

    public void saveJobPressed(ActionEvent event) throws IOException {
        String jobName = this.jobNamee.getText();
        String jobDescription = this.jobdescriptionn.getText();
        String proposedPrice = this.proposedPricee.getText();
        String imageJob = this.browseJobImage.getText();

        if(jobName.isEmpty()){
            try{
                errorEmpty("Job Name cannot be empty");
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if(jobDescription.isEmpty()){
            try{
                errorEmpty("Job Description cannot be empty");
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if(!checkInt(proposedPrice) || proposedPrice.isEmpty()) {
            try{
                errorEmpty("Proposed Price cannot be empty");
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if(Double.parseDouble(proposedPrice) < 0){
            try{
                errorEmpty("Proposed price cannot be negative");
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if(imageJob.isEmpty()){
            //imageJob = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\jobImage.jpg";

            imageJob = "src/images/jobImage.jpg";
            uniLinkOb.addJobPost(jobName,jobDescription,username,"Open",Integer.parseInt(proposedPrice), imageJob);
            errorEmpty("Your job post has been added");
        }
        else{
            //imageJob = this.browseJobImage.getText();
            String ext = FilenameUtils.getExtension(imageJob);
            File source = new File(imageJob);
            //imageJob = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\"+"job_"+jobName+""+username+"."+ext;

            imageJob = "src/images/"+"job_"+jobName+""+username+"."+ext;
            File destination = new File(imageJob);
            try{
                FileUtils.copyFile(source,destination);

            } catch (Exception e){
                e.printStackTrace();
            }
            uniLinkOb.addJobPost(jobName,jobDescription,username,"Open",Integer.parseInt(proposedPrice), imageJob);
            errorEmpty("Your job post has been added");
        }
    }

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

    public String setUsernameJob (String username) {
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