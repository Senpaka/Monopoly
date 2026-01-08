package ru.vsu.oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Property;

public class StreetInfoController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label rentLabel;

    @FXML
    private Label ownerLabel;

    @FXML
    private Button closeButton;

    private Stage stage;

    public void setData(Property property) {
        nameLabel.setText(property.getName());
        priceLabel.setText(String.valueOf(property.getPrice()));
        rentLabel.setText(String.valueOf(property.getRentPrice()));
        ownerLabel.setText(property.hasOwner() ? property.getOwner().getName() : "Нет");
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
