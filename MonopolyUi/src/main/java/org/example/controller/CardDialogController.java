package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.ru.vsu.oop.engine.api.event.Event;

import java.util.function.Consumer;

public class CardDialogController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private javafx.scene.control.Button okButton;

    private Event event;
    private Consumer<Event> callback;

    public void setData(Event event, String cardType, Consumer<Event> callback) {
        this.event = event;
        this.callback = callback;

        titleLabel.setText(cardType);
        descriptionLabel.setText(event.getDescription());
    }

    @FXML
    public void onOk() {
        if (callback != null && event != null) {
            callback.accept(event);
        }
        close();
    }

    private void close() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
