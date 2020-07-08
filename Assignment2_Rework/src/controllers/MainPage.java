package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.DAO;
import model.DbConnection;
import model.Post;
import model.UniLink;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPage extends ListView<String> implements Initializable {


    UniLink uniLinkOb = new UniLink();
    int clicked = -1;
    DAO dao = new DAO();
    DbConnection connectionOb = new DbConnection();
    ErrorPrompt errorPrompt = new ErrorPrompt();
    Connection conn;
    String creatorId;
    private String username;
    @FXML
    private Button newEventPost;
    @FXML
    private Button newSalePost;
    @FXML
    private Button newJobPost;
    @FXML
    private MenuItem developerInformation;
    @FXML
    private MenuItem quitUnilink;
    @FXML
    private Button logoutButton;
    @FXML
    private Label idLabel;
    @FXML
    private TableView<PostDetails> postDetailsTable;
    @FXML
    private TableColumn<PostDetails, String> columnStatus;
    @FXML
    private TableColumn<PostDetails, String> columnCreator;
    @FXML
    private TableColumn<PostDetails, String> columnId;

    @FXML
    private TableColumn<PostDetails, String> columnTitle;

    @FXML
    private TableColumn<PostDetails, String> columnDesc;
    @FXML
    private MenuButton typePostButton;
    @FXML
    private MenuItem allPosts;
    @FXML
    private MenuItem eventPost;
    @FXML
    private MenuItem jobPost;
    @FXML
    private MenuItem salePost;
    @FXML
    private MenuButton statusPostButton;
    @FXML
    private MenuItem statusAll;
    @FXML
    private MenuItem statusClose;
    @FXML
    private MenuItem statusOpen;
    @FXML
    private MenuButton creatorPostButton;
    @FXML
    private MenuItem creatorAll;
    @FXML
    private MenuItem creatorMy;
    @FXML
    private ImageView image;
    @FXML
    private Button deleteButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button repliesButton;
    @FXML
    private Label label1;

    @FXML
    private Button filterPostButton;

    @FXML
    private TextField replyTextField;

//    @FXML
//    private Button seeRepliesButton;

//    @FXML
//    private Label labelReplies;

    @FXML
    private Button postDetailButton;

    @FXML
    private MenuItem export;
    @FXML
    private MenuItem importButton;


    private ObservableList<PostDetails> postObs;

    //    ObservableList ObsPostList = FXCollections.observableArrayList();
    private DbConnection dc;

    public void newEventPostPressed(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/eventNew.fxml"));
        Parent root = (Parent) loader.load();
        EventNewPost secController = loader.getController();
        secController.setUsernameEvent(username);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage main = (Stage) postDetailButton.getScene().getWindow();
        main.close();

    }

    public void newSalePostPressed(javafx.event.ActionEvent sale) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/saleNew.fxml"));
        Parent root = (Parent) loader.load();
        SaleNewPost secController = loader.getController();
        secController.setUsernameSale(username);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage main = (Stage) postDetailButton.getScene().getWindow();
        main.close();
    }

    public void newJobPostPressed(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/jobNew.fxml"));
        Parent root = (Parent) loader.load();
        JobNewPost secController = loader.getController();
        secController.setUsernameJob(username);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage main = (Stage) postDetailButton.getScene().getWindow();
        main.close();
    }

    public void allPostsPressed(javafx.event.ActionEvent event) {
        typePostButton.setText("All"); //This makes the type button change to the subdivision selected
    }

    public void eventPostPressed(javafx.event.ActionEvent event) {
        typePostButton.setText("Event");
    }

    public void jobPostPressed(javafx.event.ActionEvent event) {
        typePostButton.setText("Job");
    }

    public void salePostPressed(javafx.event.ActionEvent event) {
        typePostButton.setText("Sale");
    }

    public void statusAllPressed(javafx.event.ActionEvent event) {
        statusPostButton.setText("All"); //This makes the status button change to the subdivision selected

    }

    public void statusClosePressed(javafx.event.ActionEvent event) {
        statusPostButton.setText("Closed");
    }

    public void statusOpenPressed(javafx.event.ActionEvent event) {
        statusPostButton.setText("Open");
    }

    public void creatorAllPressed(javafx.event.ActionEvent event) {
        creatorPostButton.setText("All"); //This makes the creator button change to the subdivision selected
    }

    public void creatorMyPressed(javafx.event.ActionEvent event) {
        creatorPostButton.setText("My Posts");
    }

    public void developerInformationPressed(javafx.event.ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../view/devopsInfo.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = new Stage();
        window.setScene(tableViewScene);
        window.show();
    }

    public void quitUnilinkPressed(ActionEvent event) {
        System.exit(0);
    }

    public void logoutButtonPressed(javafx.event.ActionEvent event) throws IOException {
        Stage logout = (Stage) logoutButton.getScene().getWindow();
        logout.close(); //This makes the main scene disappear

        //This makes it appear the login Page
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = new Stage();
        window.setScene(tableViewScene);
        window.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setUsername(String username) {

        idLabel.setText("Welcome " + username + "!");
        this.username = username;
    }

    public void typePostButtonPressed(javafx.event.ActionEvent event) {

    }

    public void statusPostButtonPressed(javafx.event.ActionEvent event) {
    }

    public void creatorPostButtonPressed(javafx.event.ActionEvent event) {
    }

    public void filterPostButtonPressed(javafx.event.ActionEvent event) throws SQLException {


        String type = typePostButton.getText();
        String status = statusPostButton.getText();
        String username = this.username;
        String creator = creatorPostButton.getText();

        System.out.println("Type of Post is " + typePostButton.getText());
        System.out.println("Status of Post is " + statusPostButton.getText());
        System.out.println("Creator of Post is " + creatorPostButton.getText());
        System.out.println("User name is " + username);


        columnCreator.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        postDetailsTable.setItems(uniLinkOb.addFilter(type, status, creator, username));


    }

    public void clicked(MouseEvent mouseEvent) {
        String id = postDetailsTable.getSelectionModel().getSelectedItem().getId();
        String path = postDetailsTable.getSelectionModel().getSelectedItem().getImage();
        String creatorId = postDetailsTable.getSelectionModel().getSelectedItem().getCreatorId();
        String status = postDetailsTable.getSelectionModel().getSelectedItem().getStatus();
        ArrayList<Post> postArrayList = new ArrayList<Post>();


        File file = new File(path);
        Image img = new Image(file.toURI().toString());
        image.setImage(img);


        if (status.equalsIgnoreCase("Open")) {
            System.out.println(this.username);
            if (id.contains("EVE")) {
                repliesButton.setText("Attend");
                repliesButton.setDisable(false);
                replyTextField.setDisable(true);
                postArrayList.addAll(uniLinkOb.addEventDetails(id));
//                for (int i = 0; i < postArrayList.size(); i++) {
//                    label1.setText(postArrayList.get(i).getPostDetails());
//                }
                if (creatorId.equalsIgnoreCase(this.username)) {
                    System.out.println("creator ID  " + creatorId + "username " + this.username);
                    deleteButton.setDisable(false);
                    closeButton.setDisable(false);
                    replyTextField.setDisable(true);
                    repliesButton.setDisable(true);
                    postDetailButton.setDisable(false);
                    for (int i = 0; i < postArrayList.size(); i++) {
                        label1.setText(postArrayList.get(i).getDescription());
                    }
                } else {
                    deleteButton.setDisable(true);
                    closeButton.setDisable(true);
                    replyTextField.setDisable(true);
                    repliesButton.setDisable(false);
                    postDetailButton.setDisable(true);
                    label1.setText("Only creators can " + "\nview details");
                }

            } else if (id.contains("SAL")) {
                repliesButton.setText("Reply");
                repliesButton.setDisable(false);
                //replyTextField.setDisable(false);
                postArrayList.addAll(uniLinkOb.addSaleDetails(id));
//                for (int i = 0; i < postArrayList.size(); i++) {
//                    label1.setText(postArrayList.get(i).getPostDetails());
//                }
                if (creatorId.equalsIgnoreCase(this.username)) {
                    System.out.println("creator ID  " + creatorId + "username " + this.username);
                    deleteButton.setDisable(false);
                    closeButton.setDisable(false);
                    replyTextField.setDisable(true);
                    repliesButton.setDisable(true);
                    postDetailButton.setDisable(false);
                    for (int i = 0; i < postArrayList.size(); i++) {
                        label1.setText(postArrayList.get(i).getDescription());
                    }
                } else {
                    deleteButton.setDisable(true);
                    closeButton.setDisable(true);
                    replyTextField.setDisable(false);
                    repliesButton.setDisable(false);
                    postDetailButton.setDisable(true);
                    label1.setText("Only creators can " + "\nview details");
                }
            } else if (id.contains("JOB")) {
                repliesButton.setText("Reply");
                repliesButton.setDisable(false);
                replyTextField.setDisable(false);
                postArrayList.addAll(uniLinkOb.addJobDetails(id));
//                for (int i = 0; i < postArrayList.size(); i++) {
//                    label1.setText(postArrayList.get(i).getPostDetails());
//                }

                if (creatorId.equalsIgnoreCase(this.username)) {
                    System.out.println("creator ID  " + creatorId + "username " + this.username);
                    deleteButton.setDisable(false);
                    closeButton.setDisable(false);
                    replyTextField.setDisable(true);
                    repliesButton.setDisable(true);
                    postDetailButton.setDisable(false);
                    for (int i = 0; i < postArrayList.size(); i++) {
                        label1.setText(postArrayList.get(i).getDescription());
                    }
                } else {
                    deleteButton.setDisable(true);
                    closeButton.setDisable(true);
                    replyTextField.setDisable(false);
                    repliesButton.setDisable(false);
                    postDetailButton.setDisable(true);
                    label1.setText("Only creators can " + "\nview details");
                }
            }
        } else if (status.equalsIgnoreCase("Closed")) {
            repliesButton.setDisable(true);
            repliesButton.setDisable(true);
            closeButton.setDisable(true);
            if (id.contains("EVE")) {
                postArrayList.addAll(uniLinkOb.addEventDetails(id));
                if (creatorId.equalsIgnoreCase(this.username)) {
                    deleteButton.setDisable(false);
                    postDetailButton.setDisable(false);
                    for (int i = 0; i < postArrayList.size(); i++) {
                        label1.setText(postArrayList.get(i).getPostDetails());
                    }
                } else {
                    postDetailButton.setDisable(true);
                    label1.setText("Only creators can " + "\nview details");
                }
            } else if (id.contains("SAL")) {
                postArrayList.addAll(uniLinkOb.addSaleDetails(id));
                if (creatorId.equalsIgnoreCase(this.username)) {
                    deleteButton.setDisable(false);
                    postDetailButton.setDisable(false);
                    for (int i = 0; i < postArrayList.size(); i++) {
                        label1.setText(postArrayList.get(i).getPostDetails());
                    }
                } else {
                    postDetailButton.setDisable(true);
                    label1.setText("Only creators can " + "\nview details");
                }
            } else if (id.contains("JOB")) {
                postArrayList.addAll(uniLinkOb.addJobDetails(id));
                if (creatorId.equalsIgnoreCase(this.username)) {
                    deleteButton.setDisable(false);
                    postDetailButton.setDisable(false);
                    for (int i = 0; i < postArrayList.size(); i++) {
                        label1.setText(postArrayList.get(i).getPostDetails());
                    }
                } else {
                    postDetailButton.setDisable(true);
                    label1.setText("Only creators can " + "\nview details");
                }
            }

        }
    }


    public void deleteButtonPressed(ActionEvent event) throws IOException {
        String creatorId = postDetailsTable.getSelectionModel().getSelectedItem().getCreatorId();
        String postId = postDetailsTable.getSelectionModel().getSelectedItem().getId();
        if (creatorId.equalsIgnoreCase(this.username)) {
            uniLinkOb.deletePost(this.username, postId);
            errorEmpty("Your post has been deleted!");
        }

    }

    public void closeButtonPressed(ActionEvent event) throws IOException {
        String creatorId = postDetailsTable.getSelectionModel().getSelectedItem().getCreatorId();
        String postId = postDetailsTable.getSelectionModel().getSelectedItem().getId();
        String status = postDetailsTable.getSelectionModel().getSelectedItem().getStatus();


        if (creatorId.equalsIgnoreCase(this.username) || status.equalsIgnoreCase("Open")) {
            uniLinkOb.closePost(this.username, postId);
        } else if (status.equalsIgnoreCase("Closed")) {
            errorEmpty("Cannot close post this post");
        }

    }

    public void repliesButtonPressed(ActionEvent event) throws IOException {
        String repliesText = replyTextField.getText();
        String postId = postDetailsTable.getSelectionModel().getSelectedItem().getId();
        String status = postDetailsTable.getSelectionModel().getSelectedItem().getStatus();
        double values = 0;

        if (postId.contains("EVE")) {
            errorEmpty("Your event reply has been added!");
            //uniLinkOb.newReply(this.username, postId, values, status);
        } else if (postId.contains("SAL")) {
            if (repliesText.isEmpty()) {
                try {
                    errorEmpty("Amount must not be empty");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else
                try {
                    values = Double.parseDouble(repliesText);
                    errorEmpty("Your Sale reply has been added!");
                    //uniLinkOb.newReply(this.username, postId, values, status);
                } catch (Exception ex) {
                    errorEmpty("Amount must be a number");
                }
        } else if (postId.contains("JOB")) {
            if (repliesText.isEmpty()) {

                try {
                    errorEmpty("Amount must not be empty");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else
                try {
                    values = Double.parseDouble(repliesText);
                    errorEmpty("Your Job reply has been added");
                    //uniLinkOb.newReply(this.username, postId, values, status);
                } catch (Exception ex) {
                    errorEmpty("Amount must be a number");
                }
        }
        uniLinkOb.newReply(this.username, postId, values, status);

    }


    public void errorEmpty(String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/errorPmpt.fxml"));
        Parent escreen = loader.load();
        ErrorPrompt screen = loader.getController();
        screen.setErrorMessage(message);
        Stage stage = new Stage();
        stage.setTitle("Alert!");
        stage.setScene(new Scene(escreen));
        stage.setResizable(false);
        stage.show();

    }

//    @FXML
//    void seeRepliesButtonPressed(ActionEvent event) {
//        String postId = postDetailsTable.getSelectionModel().getSelectedItem().getId();
//        ArrayList<Reply> repliesList = uniLinkOb.seeReplies(postId);
//
//        if (postId.contains("EVE")) {
//            String attendees = "Attendee List: ";
//            Iterator replies = repliesList.iterator();
//            for (int i = 0; i < repliesList.size(); i++) {
//                Reply repliesOb = repliesList.get(i);
//                //System.out.println(repliesOb.getResponderId());
//                attendees += "\n " + repliesOb.getResponderId();
//            }
//            labelReplies.setText(attendees);
//        }
//
//        if (postId.contains("SAL")) {
//            String attendees = "Sale replies ";
//            Iterator replies = repliesList.iterator();
//            for (int i = 0; i < repliesList.size(); i++) {
//                Reply repliesOb = repliesList.get(i);
//                //System.out.println(repliesOb.getResponderId());
//                attendees += "\n " + repliesOb.getResponderId() + ": " + repliesOb.getValue();
//            }
//            labelReplies.setText(attendees);
//        }
//
//        if (postId.contains("JOB")) {
//            String attendees = "Job replies ";
//            Iterator replies = repliesList.iterator();
//            for (int i = 0; i < repliesList.size(); i++) {
//                Reply repliesOb = repliesList.get(i);
//                //System.out.println(repliesOb.getResponderId());
//                attendees += "\n " + repliesOb.getResponderId() + ": " + repliesOb.getValue();
//            }
//            labelReplies.setText(attendees);
//        }
//    }

    public void postDetailButtonPressed(ActionEvent event) throws IOException {
        String postId = postDetailsTable.getSelectionModel().getSelectedItem().getId();
        String path = postDetailsTable.getSelectionModel().getSelectedItem().getImage();
        String status = postDetailsTable.getSelectionModel().getSelectedItem().getStatus();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/postDetailsWindow.fxml"));
        Parent root = (Parent) loader.load();
        PostDetailsWindow secController = loader.getController();
        secController.details(postId, path, status); //Here the ID entered is being sent into the main heading
        secController.setUsername(username);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


        //To make main page disappear
        Stage main = (Stage) postDetailButton.getScene().getWindow();
        main.close();

    }

    public void exportButtonPressed(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            //No Directory selected
        } else {
            String path = selectedDirectory.getAbsolutePath();
            uniLinkOb.exportFile(path);
        }
    }


    public void importButtonPressed(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/importScene.fxml"));
        Parent root = (Parent) loader.load();
        ImportScene secController = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
}



