package org.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameListener;
import org.example.ru.vsu.oop.engine.api.player.Player;

import java.util.List;

public class GameFxController implements GameListener {

    private GameEngine gameEngine;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label diceLabel;

    @FXML
    private Pane boardPane;

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        updateCurrentPlayer();
    }

    // ===== КНОПКИ =====

    @FXML
    private void onRollDice() {
        gameEngine.rollDice();
    }

    @FXML
    private void onStep() {
        gameEngine.moveStep();
    }

    @FXML
    private void onEndTurn() {
        gameEngine.endTurn();
    }

    // ===== LISTENER =====

    @Override
    public void onDiceRolled(Player player, int value) {
        diceLabel.setText("Кубики: " + value);
    }

    @Override
    public void onPlayerMoved(Player player, int position) {
        // позже: анимация
    }

    @Override
    public void onCellPassed(Player player, Cell cell) {

    }

    @Override
    public void onCellLanded(Player player, Cell cell) {

    }

    @Override
    public void onBalanceChanged(Player player, int newBalance) {

    }

    @Override
    public void onGameStarted(List<Player> players) {

    }

    @Override
    public void onTurnEnded(Player nextPlayer) {
        updateCurrentPlayer();
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onGameOver(Player winner) {

    }

    private void updateCurrentPlayer() {
        Player p = gameEngine.getCurrentPlayer();
        currentPlayerLabel.setText("Ходит: " + p.getName());
    }
}

