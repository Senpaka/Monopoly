package ru.vsu.oop.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Cell;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Property;
import ru.vsu.oop.ru.vsu.oop.engine.api.cell.Street;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameListener;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;

import java.util.List;

import static ru.vsu.oop.utils.controllerUtils.getColorForCell;

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

            Rectangle rect = new Rectangle(CELL_WIDTH, CELL_HEIGHT);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.WHITE);

            Rectangle colorBar = new Rectangle(CELL_WIDTH, CELL_HEIGHT * 0.2);
            colorBar.setFill(getColorForCell(cell));
            StackPane.setAlignment(colorBar, Pos.TOP_CENTER);
            cellPane.getChildren().addAll(rect, colorBar);

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

    private void showBuyPropertyWindow(Player player, Property property) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BuyPropertyDialog.fxml"));
            BorderPane pane = loader.load();

            BuyPropertyController controller = loader.getController();
            controller.setData(player, property, bought -> {
                if (bought) {
                    property.setOwner(player);
                    player.addProperty(property);
                    log(player.getName() + " купил " + property.getName());
                } else {
                    log(player.getName() + " отказался от покупки " + property.getName());
                }
                drawPlayers();
            });

            Stage stage = new Stage();
            stage.setTitle("Покупка улицы");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onPropertyAvailable(Player player, Property property) {
        Platform.runLater(() -> showBuyPropertyWindow(player, property));
    }

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
        if (cell instanceof Property property) {

            if (!property.hasOwner()) {
                Platform.runLater(() ->
                        showBuyPropertyWindow(player, property)
                );
                return;
            }

            if (property.getOwner() != player) {
                log(player.getName() + " платит аренду " + property.getOwner().getName());
            }
        }
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

    private void log(String message) {
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        logBox.getChildren().add(label);

        // Прокрутка вниз
        logScrollPane.layout();
        logScrollPane.setVvalue(1.0);
    }
}

