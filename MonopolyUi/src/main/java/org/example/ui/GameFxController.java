package org.example.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
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

    @FXML
    private VBox logBox;

    @FXML
    private ScrollPane logScrollPane;

    private static final double CELL_WIDTH = 60;
    private static final double CELL_HEIGHT = 60;
    private static final double BOARD_SIZE = CELL_HEIGHT * 11; // предполагаем квадратное поле

    private void drawBoard() {
        boardPane.getChildren().clear();

        List<Cell> cells = gameEngine.getBoard().getCells();

        for (Cell cell : cells) {
            StackPane cellPane = new StackPane();

// Основной фон
            Rectangle rect = new Rectangle(CELL_WIDTH, CELL_HEIGHT);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.WHITE);

// Мини-полоска сверху (например для цвета улицы)
            Rectangle colorBar = new Rectangle(CELL_WIDTH, CELL_HEIGHT * 0.2);
            colorBar.setFill(getColorForCell(cell));
            StackPane.setAlignment(colorBar, Pos.TOP_CENTER);
            cellPane.getChildren().addAll(rect, colorBar);

// Подпись
            Label labelName = new Label(cell.getName());
            labelName.setFont(Font.font(10));
            labelName.setWrapText(true);
            labelName.setTextAlignment(TextAlignment.CENTER);
            labelName.setAlignment(Pos.TOP_CENTER);         // текст в Label сверху
            labelName.setMaxWidth(CELL_WIDTH - 4);          // ограничение ширины
            StackPane.setAlignment(labelName, Pos.TOP_CENTER);  // Label размещаем в StackPane сверху
            StackPane.setMargin(labelName, new Insets(CELL_HEIGHT * 0.2, 2, 0, 2)); // отступ сверху

            if (cell instanceof Property prop) {
                Label labelPrice = new Label(Integer.toString(prop.getPrice()));
                labelPrice.setFont(Font.font(10));
                labelPrice.setWrapText(true);
                labelPrice.setTextAlignment(TextAlignment.CENTER);
                labelPrice.setAlignment(Pos.BOTTOM_CENTER);         // текст в Label сверху
                labelPrice.setMaxWidth(CELL_WIDTH - 4);          // ограничение ширины
                StackPane.setAlignment(labelPrice, Pos.BOTTOM_CENTER);  // Label размещаем в StackPane сверху
                StackPane.setMargin(labelPrice, new Insets(CELL_HEIGHT * 0.2, 2, 0, 2));
                cellPane.getChildren().add(labelPrice);
            }

// Добавляем всё в StackPane
            cellPane.getChildren().add(labelName);
            cellPane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);

// Размещаем на доске
            Point2D pos = getCellCoordinates(cell.getPosition());
            cellPane.setLayoutX(pos.getX());
            cellPane.setLayoutY(pos.getY());

            boardPane.getChildren().add(cellPane);

        }

        // 4. Добавляем игроков
        drawPlayers();
    }

    private Point2D getCellCoordinates(int position) {
        double x = 0, y = 0;

        if (position >= 0 && position <= 10) { // Верх
            x = position * CELL_WIDTH;
            y = 0;
        } else if (position >= 11 && position <= 20) { // Правая
            x = BOARD_SIZE - CELL_WIDTH;
            y = (position - 10) * CELL_HEIGHT;
        } else if (position >= 21 && position <= 30) { // Низ
            x = BOARD_SIZE - (position - 20 + 1) * CELL_WIDTH;
            y = BOARD_SIZE - CELL_HEIGHT;
        } else if (position >= 31 && position < 40) { // Левая
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
        } else if (cell instanceof SpecialCell specialCell) {
            if (specialCell.getCellType() == CellType.CHANCE) {
                return Color.LIGHTYELLOW;
            } else if (specialCell.getCellType() == CellType.COMMUNITY) {
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
            circle.setLayoutY(pos.getY() + margin + CELL_HEIGHT * 0.2);

            boardPane.getChildren().add(circle);
            offset++;
        }
    }

    private void animatePlayerMove(Player player, int steps, Runnable onFinished) {
        if (steps <= 0) {
            if (onFinished != null) onFinished.run();
            return;
        }

        Timeline timeline = new Timeline();
        System.out.println(steps);

        for (int i = 1; i <= steps; i++) {
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.5), e -> {
                // Двигаем игрока на 1 клетку
                gameEngine.movePlayer(player, 1);

                // Перерисовываем игроков
                drawBoard();
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(e -> {
            if (onFinished != null) onFinished.run();
        });

        timeline.play();
    }

    public void setGameEngine(GameEngine engine) {
        this.gameEngine = engine;
        this.gameEngine.setListener(this); // теперь безопасно
        drawBoard(); // можно нарисовать поле после установки движка
    }

    private void addLogMessage(String message) {
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setWrapText(true);           // перенос по ширине
            label.setFont(Font.font("Arial", 12));
            logBox.getChildren().add(label);

            // Автопрокрутка вниз
            logScrollPane.layout();            // обновляем layout
            logScrollPane.setVvalue(1.0);      // прокрутка в самый низ
        });
    }


    // ===== КНОПКИ =====

    @FXML
    private void onMoveStepClicked() {
        Player player = gameEngine.getCurrentPlayer();
        int moves = gameEngine.hasMovesSteps() ? gameEngine.getMovesLeft() : 0;

        animatePlayerMove(player, moves, () -> {
            // После завершения анимации
            gameEngine.land();        // вызываем событие на клетке
            gameEngine.endTurn();     // заканчиваем ход
        });
    }

    @FXML
    private void onRollDice() {
        gameEngine.rollDice();
        Player player = gameEngine.getCurrentPlayer();
        int moves = gameEngine.hasMovesSteps() ? gameEngine.getMovesLeft() : 0;

        animatePlayerMove(player, moves, () -> {
            // После завершения анимации
            gameEngine.land();        // вызываем событие на клетке
            gameEngine.endTurn();     // заканчиваем ход
        });

        drawPlayers();
    }

    @FXML
    private void onStep() {
        gameEngine.moveStep();
        drawPlayers();
    }

    @FXML
    private void onEndTurn() {
        gameEngine.endTurn();
        drawPlayers();
    }

    // ===== LISTENER =====

    @Override
    public void onDiceRolled(Player player, int value) {
        diceLabel.setText("Кубики: " + value);
        addLogMessage(player.getName() + " бросил кубики и получил " + value);
    }

    @Override
    public void onPlayerMoved(Player player, int position) {
        Cell cell = gameEngine.getBoard().getCell(position);
        addLogMessage(player.getName() + " шагнул на клетку " + cell.getName());
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
        addLogMessage("Ход закончился. Следующий игрок: " + nextPlayer.getName());
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

