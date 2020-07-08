package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PostDetailsWindow {
    final static String DATE_FORMAT = "dd/MM/yyyy";
    static String username;
    UniLink uniLinkOb = new UniLink();
    Boolean isDate = false;
    private String postId;
    private String path;
    @FXML
    private Button backButton;

    @FXML
    private Label title;

    @FXML
    private ImageView imageView;

    @FXML
    private Button uploadImage;

    @FXML
    private TableView<Reply> tableView;

    @FXML
    private TableColumn<Reply, String> userIdCol;

    @FXML
    private TableColumn<Reply, Double> valuesCol;

    @FXML
    private TextField uploadImageText;
    @FXML
    private Button getRepliesButton;

    @FXML
    private Label details;

    @FXML
    private TextField details1;

    @FXML
    private TextField details2;

    @FXML
    private TextField details3;

    @FXML
    private TextField details4;

    @FXML
    private TextField details5;

    @FXML
    private TextField details6;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private Button saveButton;

    @FXML
    private AnchorPane dialogBox;

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

    public void details(String postId, String path, String status) {
        ArrayList<Post> postArrayList = new ArrayList<Post>();
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        ArrayList<Sale> saleArrayList = new ArrayList<>();
        ArrayList<Job> jobArrayList = new ArrayList<>();


        ObservableList<Reply> replies = uniLinkOb.seeReplies2(postId);
        ObservableList<Reply> items = tableView.getItems();


        //Setting the title
        title.setText(postId);

        //Image of the post has been set
        File file = new File(path);
        Image img = new Image(file.toURI().toString());
        imageView.setImage(img);

        //Static values which are pushed from mainPage
        this.postId = postId;
        this.path = path;

        //Disable save button if there are already replies present

        if (status.equalsIgnoreCase("Closed")) {
            saveButton.setDisable(true);
        } else if (status.equalsIgnoreCase("Open")) {
            saveButton.setDisable(false);
            if (replies.isEmpty()) {
                saveButton.setDisable(false);
            } else {
                saveButton.setDisable(true);
            }
        }

        //Displaying editable posts
        String info = "Post Details";
        if (postId.contains("EVE")) {
            eventArrayList.addAll(uniLinkOb.addEventDetails(postId));
            postArrayList.addAll(uniLinkOb.addEventDetails(postId));
            for (int i = 0; i < postArrayList.size(); i++) {
                label1.setText("Event Name");
                details1.setText(postArrayList.get(i).getTitle());
                label2.setText("Event Description");
                details2.setText(postArrayList.get(i).getDescription());
            }
            for (int i = 0; i < eventArrayList.size(); i++) {
                label3.setText("Venue");
                details3.setText(eventArrayList.get(i).getVenue());
                label4.setText("Date");
                details4.setText(eventArrayList.get(i).getDate());
                label5.setText("Capacity");
                details5.setText(String.valueOf(eventArrayList.get(i).getCapacity()));
                label6.setText("Attendee Count");
                details6.setText(String.valueOf(eventArrayList.get(i).getAttendeeCount()));
                details6.setDisable(true);
            }
        } else if (postId.contains("SAL")) {
            saleArrayList.addAll(uniLinkOb.addSaleDetails(postId));
            postArrayList.addAll(uniLinkOb.addSaleDetails(postId));
            for (int i = 0; i < postArrayList.size(); i++) {
                label1.setText("Sale Name");
                details1.setText(postArrayList.get(i).getTitle());
                label2.setText("Sale Description");
                details2.setText(postArrayList.get(i).getDescription());
            }
            for (int i = 0; i < saleArrayList.size(); i++) {
                label3.setText("Asking Price");
                details3.setText(String.valueOf(saleArrayList.get(i).getAskingPrice()));
                label4.setText("Minimum Raise");
                details4.setText(String.valueOf(saleArrayList.get(i).getMinimumRaise()));
                label5.setText("Highest Offer");
                details5.setText(String.valueOf(saleArrayList.get(i).getHighestOffer()));
                details5.setDisable(true);
                label6.setDisable(true);
                label6.setDisable(true);
//                    info += "\n" + postArrayList.get(i).getPostDetails();
//                    details.setText(info);
            }
        } else if (postId.contains("JOB")) {
            jobArrayList.addAll(uniLinkOb.addJobDetails(postId));
            postArrayList.addAll(uniLinkOb.addJobDetails(postId));
            for (int i = 0; i < postArrayList.size(); i++) {
                label1.setText("Job Name");
                details1.setText(postArrayList.get(i).getTitle());
                label2.setText("Job Description");
                details2.setText(postArrayList.get(i).getDescription());
            }
            for (int i = 0; i < jobArrayList.size(); i++) {
                label3.setText("Proposed Price");
                details3.setText(String.valueOf(jobArrayList.get(i).getProposedPrice()));
                label4.setText("Lowest Offer");
                details4.setText(String.valueOf(jobArrayList.get(i).getLowestOffer()));
                details4.setDisable(true);
                label5.setDisable(true);
                details5.setDisable(true);
                label6.setDisable(true);
                details6.setDisable(true);
//                    info += "\n" + postArrayList.get(i).getPostDetails();
//                    details.setText(info);
            }
        }

    }

    public void backButtonPressed(ActionEvent event) throws IOException {
        Stage postDetails = (Stage) backButton.getScene().getWindow();
        postDetails.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainPage.fxml"));
        Parent root = (Parent) loader.load();
        MainPage secController = loader.getController();
        secController.setUsername(username);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void uploadImagePressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );
        File file = fileChooser.showOpenDialog(dialogBox.getScene().getWindow());

        if (file != null) {
            // ImageUploadButton is the TextField fx:id
            uploadImageText.setText(file.getPath());
            //String imageEvent = imageUploadButton.getText();


        } else {
            System.out.println("error"); // or something else
        }
    }

    public void getRepliesButtonPressed(ActionEvent event) {

        userIdCol.setCellValueFactory(new PropertyValueFactory<>("responderId"));
        valuesCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableView.setItems(uniLinkOb.seeReplies2(postId));

    }

    public void saveButtonPressed(ActionEvent event) throws IOException {


        if (postId.contains("EVE")) {
            String venue = this.details3.getText();
            String date = this.details4.getText();
            isDate = isDateValid(date);
            String capacity = this.details5.getText();
            String eventName = this.details1.getText();
            String eventDescription = this.details2.getText();
            String imageEvent = this.uploadImageText.getText();


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
                uniLinkOb.updateEventDetails(this.postId, eventName, eventDescription, username, "Open", venue, date, Integer.parseInt(capacity), 0, imageEvent);
            } else {
                String ext = FilenameUtils.getExtension(imageEvent);
                File source = new File(imageEvent);
                //imageEvent = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\" + "event_" + eventName + "" + username + "." + ext;

                imageEvent = "src/images/" + "event_" + eventName + "" + username + "." + ext;
                File destination = new File(imageEvent);
                try {
                    FileUtils.copyFile(source, destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                uniLinkOb.updateEventDetails(this.postId, eventName, eventDescription, username, "Open", venue, date, Integer.parseInt(capacity), 0, imageEvent);
            }

        } else if (postId.contains("SAL")) {
            String saleName = this.details1.getText();
            String saleDescription = this.details2.getText();
            String askingPrice = this.details3.getText();
            String minimumRaise = this.details4.getText();
            String saleImage = this.uploadImageText.getText();

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
            } else if (!checkInt(askingPrice) || askingPrice.isEmpty()) {
                try {
                    errorEmpty("Asking Price cannot be empty or negative");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Double.parseDouble(askingPrice) < 0) {
                try {
                    errorEmpty("Asking Price cannot be negative");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (!checkInt(minimumRaise) || minimumRaise.isEmpty()) {
                try {
                    errorEmpty("Minimum Raise cannot be empty");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Double.parseDouble(minimumRaise) < 0) {
                try {
                    errorEmpty("Minimum Raise cannot be negative");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (saleImage.isEmpty()) {
                //saleImage = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\saleImage.png";
                saleImage = "src/images/saleImage.png";
                uniLinkOb.updateSaleDetails(this.postId, saleName, saleDescription, username, "Open", Double.parseDouble(askingPrice), Double.parseDouble(minimumRaise), saleImage);
            } else {
                String ext = FilenameUtils.getExtension(saleImage);
                File source = new File(saleImage);
                //saleImage = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\" + "sale_" + saleName + "" + username + "." + ext;
                saleImage = "src/images/" + "sale_" + saleName + "" + username + "." + ext;
                File destination = new File(saleImage);
                try {
                    FileUtils.copyFile(source, destination);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                uniLinkOb.updateSaleDetails(this.postId, saleName, saleDescription, username, "Open", Double.parseDouble(askingPrice), Double.parseDouble(minimumRaise), saleImage);
            }
        } else if (postId.contains("JOB")) {
            String jobName = this.details1.getText();
            String jobDescription = this.details2.getText();
            String proposedPrice = this.details3.getText();
            String imageJob = this.uploadImageText.getText();

            if (jobName.isEmpty()) {
                try {
                    errorEmpty("Job Name cannot be empty");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (jobDescription.isEmpty()) {
                try {
                    errorEmpty("Job Description cannot be empty");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (!checkInt(proposedPrice) || proposedPrice.isEmpty()) {
                try {
                    errorEmpty("Proposed Price cannot be empty");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Double.parseDouble(proposedPrice) < 0) {
                try {
                    errorEmpty("Proposed price cannot be negative");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (imageJob.isEmpty()) {
                //imageJob = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\jobImage.jpg";
                imageJob = "src/images/jobImage.jpg";
                uniLinkOb.updateJobDetails(this.postId, jobName, jobDescription, username, "Open", Integer.parseInt(proposedPrice), imageJob);
            } else {
                //imageJob = this.browseJobImage.getText();
                String ext = FilenameUtils.getExtension(imageJob);
                File source = new File(imageJob);
                //imageJob = "D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\images\\" + "job_" + jobName + "" + username + "." + ext;
                imageJob = "src/images/" + "job_" + jobName + "" + username + "." + ext;
                File destination = new File(imageJob);
                try {
                    FileUtils.copyFile(source, destination);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                uniLinkOb.updateJobDetails(this.postId, jobName, jobDescription, username, "Open", Integer.parseInt(proposedPrice), imageJob);
            }
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

    private boolean checkInt(String input) throws IOException {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception ex) {

            return false;
        }
    }

    public String setUsername(String username) {
        this.username = username;
        return username;

    }
}

