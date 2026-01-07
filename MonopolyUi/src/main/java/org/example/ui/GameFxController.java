package org.example.ui;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.example.ru.vsu.oop.engine.api.cell.*;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameListener;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

import java.util.List;

import static org.example.ru.vsu.oop.engine.model.enumObject.board.ColorGroup.*;

public class GameFxController implements GameListener {

    public GameEngine gameEngine;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label diceLabel;

    @FXML
    private Pane boardPane;

    private static final double CELL_WIDTH = 60;
    private static final double CELL_HEIGHT = 60;
    private static final double BOARD_SIZE = 600; // предполагаем квадратное поле

    private StackPane createCellVisual(Cell cell) {
        // 1. Основной прямоугольник
        Rectangle rect = new Rectangle(CELL_WIDTH, CELL_HEIGHT);
        rect.setStroke(Color.BLACK);          // граница клетки
        rect.setStrokeWidth(2);
        rect.setFill(Color.WHITE);            // фон клетки

        // 2. Цветная полоса сверху для улиц
        if (cell instanceof Street street) {
            Rectangle colorBar = new Rectangle(CELL_WIDTH, 10);
            colorBar.setFill(getColorForCell(cell));
            colorBar.setStroke(Color.BLACK);

            StackPane cellPane = new StackPane();
            cellPane.getChildren().addAll(rect, colorBar);
            StackPane.setAlignment(colorBar, Pos.TOP_CENTER);

            // Подпись с названием улицы
            Label nameLabel = new Label(cell.getName());
            nameLabel.setFont(Font.font(9));
            nameLabel.setWrapText(true);
            nameLabel.setTextAlignment(TextAlignment.CENTER);
            cellPane.getChildren().add(nameLabel);
            StackPane.setAlignment(nameLabel, Pos.CENTER);

            return cellPane;
        }

        // 3. Для остальных типов клеток
        Label nameLabel = new Label(cell.getName());
        nameLabel.setFont(Font.font(9));
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);

        StackPane cellPane = new StackPane(rect, nameLabel);
        return cellPane;
    }

    private void drawBoard() {
        boardPane.getChildren().clear();

        List<Cell> cells = gameEngine.getBoard().getCells();

        for (Cell cell : cells) {
            // 1. Создаем прямоугольник
            Rectangle rect = new Rectangle(CELL_WIDTH, CELL_HEIGHT);
            rect.setStroke(Color.BLACK);
            rect.setFill(getColorForCell(cell));

            // 2. Получаем координаты клетки
            Point2D pos = getCellCoordinates(cell.getPosition());
            rect.setLayoutX(pos.getX());
            rect.setLayoutY(pos.getY());

            // 3. Подпись клетки
            Label label = new Label(cell.getName());
            label.setFont(Font.font(10));
            label.setLayoutX(pos.getX() + 5);
            label.setLayoutY(pos.getY() + 5);

            boardPane.getChildren().addAll(rect, label);
        }

        // 4. Добавляем игроков
        drawPlayers();
    }

    private Point2D getCellCoordinates(int position) {
        double x = 0, y = 0;

        if (position >= 0 && position <= 9) { // Верх
            x = position * CELL_WIDTH;
            y = 0;
        } else if (position >= 10 && position <= 19) { // Правая
            x = BOARD_SIZE - CELL_WIDTH;
            y = (position - 10) * CELL_HEIGHT;
        } else if (position >= 20 && position <= 29) { // Низ
            x = BOARD_SIZE - (position - 20 + 1) * CELL_WIDTH;
            y = BOARD_SIZE - CELL_HEIGHT;
        } else if (position >= 30 && position <= 39) { // Левая
            x = 0;
            y = BOARD_SIZE - (position - 30 + 1) * CELL_HEIGHT;
        }

        return new Point2D(x, y);
    }

    private Color getColorForCell(Cell cell) {
        if (cell instanceof Street street) {
            return switch (street.getColorGroup()) {
                case BROWN -> Color.SADDLEBROWN;
                case LIGHT_BLUE -> Color.LIGHTBLUE;
                case PINK -> Color.HOTPINK;
                case ORANGE -> Color.ORANGE;
                case RED -> Color.RED;
                case YELLOW -> Color.YELLOW;
                case GREEN -> Color.GREEN;
                case DARK_BLUE -> Color.DARKBLUE;
                default -> Color.GRAY;
            };
        } else if (cell instanceof Railway) {
            return Color.SILVER;
        } else if (cell instanceof Utilities) {
            return Color.LIGHTGOLDENRODYELLOW;
        } else if (cell instanceof SpecialCell specialCell){
            if (specialCell.getCellType() == CellType.CHANCE){
                return Color.LIGHTYELLOW;
            } else if (specialCell.getCellType() == CellType.COMMUNITY){
                return Color.LIGHTCYAN;
            }
        }
        return Color.LIGHTGRAY;
    }

    private void drawPlayers() {
        int offset = 0;
        for (Player player : gameEngine.getPlayers()) {
            Circle circle = new Circle(10);
            Color color = Color.hsb((offset * 60) % 360, 0.8, 0.9);
            circle.setFill(color);

            Point2D pos = getCellCoordinates(player.getPosition());

            double margin = 10;
            circle.setLayoutX(pos.getX() + margin + offset * 15);
            circle.setLayoutY(pos.getY() + margin);

            boardPane.getChildren().add(circle);
            offset++;
        }
    }



    public void setGameEngine(GameEngine engine) {
        this.gameEngine = engine;
        this.gameEngine.setListener(this); // теперь безопасно
        drawBoard(); // можно нарисовать поле после установки движка
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

