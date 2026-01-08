package ru.vsu.oop.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameListener;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;

import java.util.List;
import java.util.stream.Collectors;

import static ru.vsu.oop.utils.controllerUtils.*;

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

    @FXML
    private VBox playerPropertiesBox;


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
            labelName.setAlignment(Pos.TOP_CENTER);
            labelName.setMaxWidth(CELL_WIDTH - 4);
            StackPane.setAlignment(labelName, Pos.TOP_CENTER);
            StackPane.setMargin(labelName, new Insets(CELL_HEIGHT * 0.2, 2, 0, 2));

            if (cell instanceof Property prop) {
                Label labelPrice = new Label(Integer.toString(prop.getPrice()));
                labelPrice.setFont(Font.font(10));
                labelPrice.setWrapText(true);
                labelPrice.setTextAlignment(TextAlignment.CENTER);
                labelPrice.setAlignment(Pos.BOTTOM_CENTER);
                labelPrice.setMaxWidth(CELL_WIDTH - 4);
                StackPane.setAlignment(labelPrice, Pos.BOTTOM_CENTER);
                StackPane.setMargin(labelPrice, new Insets(CELL_HEIGHT * 0.2, 2, 0, 2));
                cellPane.getChildren().add(labelPrice);
            }

            cellPane.getChildren().add(labelName);
            cellPane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);

            Point2D pos = getCellCoordinates(cell.getPosition());
            cellPane.setLayoutX(pos.getX());
            cellPane.setLayoutY(pos.getY());

            boardPane.getChildren().add(cellPane);

        }

        drawPlayers();
    }

    private void drawPlayers() {
        List<Node> playersToRemove = boardPane.getChildren().stream()
                .filter(node -> node instanceof Circle)
                .collect(Collectors.toList());
        boardPane.getChildren().removeAll(playersToRemove);

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
                gameEngine.movePlayer(player, 1);

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
                drawBoard();
                updatePlayerProperties();
            });

            Stage stage = new Stage();
            stage.setTitle("Покупка улицы");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane, 350, 250);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.initOwner(boardPane.getScene().getWindow());

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showStreetInfo(Property property) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StreetInfoDialog.fxml"));
            Parent pane = loader.load();

            StreetInfoController controller = loader.getController();
            controller.setData(property);

            Stage stage = new Stage();
            stage.setTitle(property.getName());
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane, 350, 300);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.initOwner(boardPane.getScene().getWindow());

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updatePlayerProperties() {
        Player current = gameEngine.getCurrentPlayer();
        playerPropertiesBox.getChildren().clear();

        for (Property property : current.getProperty()) {
            Label propertyLabel = new Label(property.getName());
            propertyLabel.setStyle("-fx-text-fill: lightgreen;");
            propertyLabel.setOnMouseClicked(e -> showStreetInfo(property));
            playerPropertiesBox.getChildren().add(propertyLabel);
        }
    }



    @FXML
    private void onMoveStepClicked() {
        Player player = gameEngine.getCurrentPlayer();
        int moves = gameEngine.hasMovesSteps() ? gameEngine.getMovesLeft() : 0;

        animatePlayerMove(player, moves, () -> {
            gameEngine.land();
            gameEngine.endTurn();
        });
    }

    @FXML
    private void onRollDice() {
        gameEngine.rollDice();
        Player player = gameEngine.getCurrentPlayer();
        int moves = gameEngine.hasMovesSteps() ? gameEngine.getMovesLeft() : 0;

        animatePlayerMove(player, moves, () -> {
            gameEngine.land();
            gameEngine.endTurn();
        });
        drawBoard();
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
        updatePlayerProperties();
    }


    @Override
    public void onPropertyAvailable(Player player, Property property) {
        Platform.runLater(() -> showBuyPropertyWindow(player, property));
    }

    @Override
    public void onDiceRolled(Player player, int value) {
        diceLabel.setText("Кубики: " + value);
        log(player.getName() + " бросил кубики и получил " + value);
    }

    @Override
    public void onPlayerMoved(Player player, int position) {
        Cell cell = gameEngine.getBoard().getCell(position);
        log(player.getName() + " шагнул на клетку " + cell.getName());
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
        log("Ход закончился. Следующий игрок: " + nextPlayer.getName());
        updateCurrentPlayer();
        updatePlayerProperties();
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
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setWrapText(true);
            label.setFont(Font.font("Arial", 12));
            logBox.getChildren().add(label);

            logScrollPane.layout();
            logScrollPane.setVvalue(1.0);
        });
    }
}

