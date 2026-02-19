package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import com.example.data.MarkovChain;

import com.example.database.db;

public class PrimaryController {

    @FXML
    private ListView<String> chordListView;

    @FXML
    private TextField chordLengthField;

    @FXML
    private ChoiceBox<String> chordTypeChoiceBox;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
        System.out.println("Switching to secondary view");
    }

    @FXML
    private void generateChord() {
        try {
            int length;
            try {
                length = Integer.parseInt(chordLengthField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for chord length");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter a valid integer for chord length.");
                alert.showAndWait();
                return;
            }
        
            if (length < 4 || length > 16) {
                System.out.println("Chord length must be between 4 and 16");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Chord length must be between 4 and 16.");
                alert.showAndWait();
                return;
            }
            String generatedChords;
            switch (chordTypeChoiceBox.getValue()) {
                case "Popular":
                    generatedChords = MarkovChain.generatePopularChords(length);
                    break;
                case "Jazz":
                    generatedChords = MarkovChain.generateJazzChords(length);
                    break;
                default:
                    System.out.println("Invalid chord type selected");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please select a valid chord type.");
                    alert.showAndWait();
                    return;
            }
            
            generatedChords = generatedChords.stripLeading();
            generatedChords = generatedChords.replaceAll(" ", "-");
            System.out.println("Generating chords...");
            chordListView.getItems().add(generatedChords);
            try {
                db database = new db();
                database.insertChord(generatedChords);
                database.close();
            } catch (Exception e) {
                System.out.println("Error inserting chord into database: " + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
