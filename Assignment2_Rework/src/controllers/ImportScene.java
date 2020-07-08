package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.UniLink;

import java.io.File;



public class ImportScene {
    UniLink unilinkOb = new UniLink();

    @FXML
    private Button importButton;

    @FXML
    private TextField browseDb;

    @FXML
    private DialogPane dialogPane;


    public void importButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("DataBase Files", "*.db")
        );
        File file = fileChooser.showOpenDialog(dialogPane.getScene().getWindow());

        if (file != null) {
            // ImageUploadButton is the TextField fx:id
            browseDb.setText(file.getPath());
            String path = browseDb.getText();
            unilinkOb.importFile(path);

        } else {
            System.out.println("error"); // or something else
        }

    }

}