package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import com.example.data.MarkovChain;

public class PrimaryController {

    @FXML
    private ListView<String> chordListView;

    @FXML
    private TextField chordLengthField;

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
            String generatedChords = MarkovChain.generateChords(length);
            System.out.println("Generating chords...");
            chordListView.getItems().add(generatedChords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
