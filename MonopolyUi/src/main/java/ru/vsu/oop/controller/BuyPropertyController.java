package ru.vsu.oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Property;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Street;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;


import java.util.function.Consumer;

public class BuyPropertyController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label priceLabel;

    private Player player;
    private Property property;
    private Consumer<Boolean> callback;

    public void setData(Player player, Property property, Consumer<Boolean> callback) {
        this.player = player;
        this.property = property;
        this.callback = callback;

        titleLabel.setText("Купить " + property.getName() + "?");
        priceLabel.setText("Цена: " + property.getPrice());
    }

    // 🔴 ОБЯЗАТЕЛЬНО public
    @FXML
    public void onBuy() {
        boolean success = player.spendMoney(property.getPrice());
        if (success){
            callback.accept(success);
            close();
        }
    }

    // 🔴 ОБЯЗАТЕЛЬНО public
    @FXML
    public void onCancel() {
        callback.accept(false);
        close();
    }

    private void close() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}

