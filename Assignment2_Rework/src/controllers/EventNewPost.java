/*
This Controller handles all the new events being added from the FX to Unilink
 */


package controllers;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EventNewPost {
    final static String DATE_FORMAT = "dd/MM/yyyy";
    static String username;
    static String message;
    UniLink uniLinkOb = new UniLink();
    Boolean isDate = false;
    int count = 0;

    @FXML
    private Button eventBackButton;

    @FXML
    private Button imageUploadButton;

    @FXML
    private ImageView eventImage;

    @FXML
    private TextField eventName;

    @FXML
    private TextField eventDescription;

    @FXML
    private TextField venue;

    @FXML
    private TextField date;

    @FXML
    private TextField capacity;

    @FXML
    private Button saveEventButton;

    @FXML
    private TextField imageBrowser;

    @FXML
    private DialogPane dialogBoxEvent;

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public void imageUploadButtonPressed(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );
        File file = fileChooser.showOpenDialog(dialogBoxEvent.getScene().getWindow());

        if (file != null) {
            // ImageUploadButton is the TextField fx:id
            imageBrowser.setText(file.getPath());
            //String imageEvent = imageUploadButton.getText();


        } else {
            System.out.println("error"); // or something else
        }

    }

    public void saveEventButtonPressed(javafx.event.ActionEvent event) throws IOException {
        String venue = this.venue.getText();
        String date = "";
        date = this.date.getText();
        isDate = isDateValid(date);
        String capacity = this.capacity.getText();
        String eventName = this.eventName.getText();
        String eventDescription = this.eventDescription.getText();
        String imageEvent = this.imageBrowser.getText();
        String imageEvent2 = this.imageBrowser.getText();


        if (venue.isEmpty()) {
            try {
                errorEmpty("Venue Details cannot be empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (date.isEmpty() || !isDate) {
            try {
                errorEmpty("Enter the correct Date");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (eventName.isEmpty()) {
            try {
                errorEmpty("Event name details cannot be empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (eventDescription.isEmpty()) {
            try {
                errorEmpty("Event description cannot be empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!checkInt(capacity) || capacity.isEmpty()) {
            try {
                errorEmpty("Capacity cannot be empty or letters");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (Integer.parseInt(capacity) < 0) {
            try {
                errorEmpty("Capacity needs to be positive");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (imageEvent.isEmpty()) {

            //imageEvent = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\eventImage.jpg";
            imageEvent = "src/images/eventImage.jpg";
            uniLinkOb.addEventPost(eventName, eventDescription, username, "Open", venue, date, Integer.parseInt(capacity), 0, imageEvent);
            errorEmpty("Your event post has been added");
        } else {
            String ext = FilenameUtils.getExtension(imageEvent);
            File source = new File(imageEvent);

            //imageEvent = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\"+"event_"+eventName+""+username+"."+ext;
            imageEvent = "src/images/"+"event_"+eventName+""+username+"."+ext;
            File destination = new File(imageEvent);
            try {
                FileUtils.copyFile(source,destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            imageEvent = this.imageBrowser.getText();
            uniLinkOb.addEventPost(eventName, eventDescription, username, "Open", venue, date, Integer.parseInt(capacity), 0, imageEvent);
            errorEmpty("Your event post has been added");
        }
    }

    public void eventBackButtonPressed(javafx.event.ActionEvent event) throws IOException {
        Stage newEvent = (Stage) eventBackButton.getScene().getWindow();
        newEvent.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainPage.fxml"));
        Parent root = (Parent) loader.load();
        MainPage secController = loader.getController();
        secController.setUsername(username);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public String setUsernameEvent(String username) {
        this.username = username;
        return username;

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

    private boolean checkInt(String input) throws IOException {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception ex) {

            return false;
        }
    }
}


