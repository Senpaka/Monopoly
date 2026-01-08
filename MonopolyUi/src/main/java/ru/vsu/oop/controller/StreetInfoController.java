package ru.vsu.oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Property;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Street;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.ColorGroup;

import java.lang.Runnable;

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
    private Label housesLabel;

    @FXML
    private Label hotelLabel;

    @FXML
    private Label colorGroupStatusLabel;

    @FXML
    private Button buyHouseButton;

    @FXML
    private Button buyHotelButton;

    @FXML
    private Button closeButton;

    private Property property;
    private Player player;
    private GameEngine gameEngine;
    private Runnable onUpdateCallback;

    public void setData(Property property, Player player, GameEngine gameEngine, Runnable onUpdateCallback) {
        this.property = property;
        this.player = player;
        this.gameEngine = gameEngine;
        this.onUpdateCallback = onUpdateCallback;

        nameLabel.setText(property.getName());
        priceLabel.setText(String.valueOf(property.getPrice()));
        rentLabel.setText(String.valueOf(property.getRentPrice()));
        ownerLabel.setText(property.hasOwner() ? property.getOwner().getName() : "Нет");

        if (property instanceof Street street) {
            housesLabel.setText("Дома: " + street.getHouses());
            hotelLabel.setText(street.hasHotel() ? "Отель: Да" : "Отель: Нет");
            
            boolean canBuyHouses = canBuyHouses(street);
            colorGroupStatusLabel.setText(canBuyHouses ? 
                "✅ Вся цветовая группа собрана" : 
                "❌ Не вся цветовая группа собрана");
            
            buyHouseButton.setDisable(!canBuyHouses || street.hasHotel() || street.getHouses() >= 4);
            buyHotelButton.setDisable(!canBuyHouses || street.hasHotel() || street.getHouses() < 4);
            
            if (canBuyHouses) {
                buyHouseButton.setText("Купить дом (" + street.getHousePrice() + " ₽)");
                buyHotelButton.setText("Купить отель (" + street.getHotelPrice() + " ₽)");
            }
        } else {
            housesLabel.setText("Дома: -");
            hotelLabel.setText("Отель: -");
            colorGroupStatusLabel.setText("Не улица");
            buyHouseButton.setDisable(true);
            buyHotelButton.setDisable(true);
        }
    }

    private boolean canBuyHouses(Street street) {
        if (!street.hasOwner() || street.getOwner() != player) {
            return false;
        }

        ColorGroup colorGroup = street.getColorGroup();
        int requiredCount = getRequiredCountForColorGroup(colorGroup);
        
        long ownedCount = player.getProperty().stream()
            .filter(p -> p instanceof Street)
            .map(p -> (Street) p)
            .filter(s -> s.getColorGroup() == colorGroup)
            .count();

        return ownedCount >= requiredCount;
    }

    private int getRequiredCountForColorGroup(ColorGroup colorGroup) {
        return (colorGroup == ColorGroup.BROWN || colorGroup == ColorGroup.DARK_BLUE) ? 2 : 3;
    }

    @FXML
    private void onBuyHouse() {
        if (property instanceof Street street) {
            if (!canBuyHouses(street)) {
                showAlert("Ошибка", "Вы не можете покупать дома на этой улице", 
                    "Вам нужно собрать всю цветовую группу (" + getRequiredCountForColorGroup(street.getColorGroup()) + " улицы)");
                return;
            }

            if (street.getHouses() >= 4) {
                showAlert("Ошибка", "Максимум домов", "На этой улице уже 4 дома. Купите отель.");
                return;
            }

            if (street.hasHotel()) {
                showAlert("Ошибка", "Уже есть отель", "На этой улице уже построен отель.");
                return;
            }

            int housePrice = street.getHousePrice();
            if (player.getBalance() < housePrice) {
                showAlert("Ошибка", "Недостаточно средств", 
                    "Вам нужно " + housePrice + " ₽, у вас " + player.getBalance() + " ₽");
                return;
            }

            if (player.spendMoney(housePrice)) {
                street.addHouse();
                housesLabel.setText("Дома: " + street.getHouses());
                rentLabel.setText(String.valueOf(street.getRentPrice()));
                buyHouseButton.setDisable(street.getHouses() >= 4);
                buyHotelButton.setDisable(street.getHouses() < 4);
                
                if (onUpdateCallback != null) {
                    onUpdateCallback.run();
                }
            }
        }
    }

    @FXML
    private void onBuyHotel() {
        if (property instanceof Street street) {
            if (!canBuyHouses(street)) {
                showAlert("Ошибка", "Вы не можете покупать отели на этой улице", 
                    "Вам нужно собрать всю цветовую группу (" + getRequiredCountForColorGroup(street.getColorGroup()) + " улицы)");
                return;
            }

            if (street.getHouses() < 4) {
                showAlert("Ошибка", "Недостаточно домов", "Нужно построить 4 дома перед покупкой отеля.");
                return;
            }

            if (street.hasHotel()) {
                showAlert("Ошибка", "Уже есть отель", "На этой улице уже построен отель.");
                return;
            }

            int hotelPrice = street.getHotelPrice();
            if (player.getBalance() < hotelPrice) {
                showAlert("Ошибка", "Недостаточно средств", 
                    "Вам нужно " + hotelPrice + " ₽, у вас " + player.getBalance() + " ₽");
                return;
            }

            if (player.spendMoney(hotelPrice)) {
                street.addHotel();
                housesLabel.setText("Дома: 0");
                hotelLabel.setText("Отель: Да");
                rentLabel.setText(String.valueOf(street.getRentPrice()));
                buyHouseButton.setDisable(true);
                buyHotelButton.setDisable(true);
                
                if (onUpdateCallback != null) {
                    onUpdateCallback.run();
                }
            }
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
