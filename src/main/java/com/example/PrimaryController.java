package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PrimaryController {

    @FXML
    private ListView<String> chordListView;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
        System.out.println("Switching to secondary view");
    }

    @FXML
    private void generateChord() {
        // Placeholder for chord generation logic
        System.out.println("Generating chords...");
        chordListView.getItems().add("C Major");
    }
}
