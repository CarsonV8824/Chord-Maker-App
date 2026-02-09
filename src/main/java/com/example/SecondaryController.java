package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import com.example.database.db;

public class SecondaryController {

    @FXML
    private ListView<String> savedChordsListView;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void loadSavedChords() {
        try {
            db database = new db();
            savedChordsListView.getItems().clear();
            for (String chord : database.getChords()) {
                savedChordsListView.getItems().add(chord);
            }
        } catch (Exception e) {
            System.out.println("Error loading saved chords: " + e.getMessage());
        }
    }
}