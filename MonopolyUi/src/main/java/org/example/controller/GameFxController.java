package org.example.controller;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import org.example.enumObject.GameState;
import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.cell.Property;
import org.example.ru.vsu.oop.engine.api.cell.Street;
import org.example.ru.vsu.oop.engine.api.event.Event;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameListener;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.impl.cells.eventCell.CardCellImpl;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.events.chance.GoToClosestPropertyCard;
import org.example.ru.vsu.oop.engine.model.events.chance.GoToNCellCard;
import org.example.ru.vsu.oop.engine.model.events.chance.MoveToNCellsCard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.enumObject.GameState.*;
import static org.example.utils.controllerUtils.*;

public class GameFxController implements GameListener {

    public GameEngine gameEngine;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label playerBalanceLabel;

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

    @FXML
    private Button rollDiceButton;

    @FXML
    private Button endTurnButton;

    private GameState currentState = WAITING_FOR_ROLL;

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
        currentState = MOVING;
        updateUIState();

        for (int i = 1; i <= steps; i++) {
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.5), e -> {
                gameEngine.movePlayer(player, 1);
                drawBoard();
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(e -> {
            currentState = END_TURN;
            updateUIState();
            if (onFinished != null) onFinished.run();
        });

        timeline.play();
    }

    private void animatePlayerMoveBackward(Player player, int steps, Runnable onFinished) {
        if (steps <= 0) {
            if (onFinished != null) onFinished.run();
            return;
        }

        Timeline timeline = new Timeline();
        currentState = MOVING;
        updateUIState();

        for (int i = 1; i <= steps; i++) {
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.5), e -> {
                int currentPos = player.getPosition();
                int newPos = (currentPos - 1 + gameEngine.getBoard().getSize()) % gameEngine.getBoard().getSize();
                player.setPosition(newPos);
                drawBoard();
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(e -> {
            currentState = END_TURN;
            updateUIState();
            if (onFinished != null) onFinished.run();
        });

        timeline.play();
    }

    public void setGameEngine(GameEngine engine) {
        this.gameEngine = engine;
        this.gameEngine.setListener(this);
        drawBoard();
    }

    private void showBuyPropertyWindow(Player player, Property property) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BuyPropertyDialog.fxml"));
            BorderPane pane = loader.load();

            BuyPropertyController controller = loader.getController();
            controller.setData(player, property, bought -> {
                if (bought) {
                    int balanceBefore = player.getBalance();
                    property.setOwner(player);
                    player.addProperty(property);
                    int balanceAfter = player.getBalance();
                    log("🏠 " + player.getName() + " купил " + property.getName() + " за " + property.getPrice() + " ₽");
                    log("💰 Баланс: " + balanceBefore + " ₽ → " + balanceAfter + " ₽");
                } else {
                    log("❌ " + player.getName() + " отказался от покупки " + property.getName());
                }
                drawBoard();
                updatePlayerProperties();
                updatePlayerBalance(player);
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

    private void showCardDialog(Player player, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CardDialog.fxml"));
            BorderPane pane = loader.load();

            CardDialogController controller = loader.getController();
            
            String cardType = event.getType() == CellType.CHANCE ? "Шанс" : "Общественная казна";
            controller.setData(event, cardType, confirmedEvent -> {
                applyCardEvent(player, confirmedEvent);
            });

            Stage stage = new Stage();
            stage.setTitle(cardType);
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane, 450, 300);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.initOwner(boardPane.getScene().getWindow());

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyCardEvent(Player player, Event event) {
        if (event instanceof GoToNCellCard goToCard) {
            int currentPos = player.getPosition();
            int targetPos = getTargetPosition(goToCard);
            int steps = calculateSteps(currentPos, targetPos);
            
            log("📍 " + player.getName() + " перемещается на позицию " + targetPos);
            
            if (steps != 0) {
                animatePlayerMove(player, steps, () -> {
                    player.setPosition(targetPos);
                    Cell targetCell = gameEngine.getBoard().getCell(targetPos);
                    log("✅ " + player.getName() + " прибыл на " + targetCell.getName());
                    gameEngine.addMessage(goToCard.getDescription());
                    drawBoard();
                    gameEngine.land();
                });
            } else {
                gameEngine.addMessage(goToCard.getDescription());
                drawBoard();
                gameEngine.land();
            }
        } else if (event instanceof MoveToNCellsCard moveCard) {
            int steps = getMoveSteps(moveCard);
            if (steps != 0) {
                String direction = steps > 0 ? "вперед" : "назад";
                log("📍 " + player.getName() + " перемещается на " + Math.abs(steps) + " клеток " + direction);
                
                if (steps < 0) {
                    int absSteps = Math.abs(steps);
                    animatePlayerMoveBackward(player, absSteps, () -> {
                        Cell currentCell = gameEngine.getBoard().getCell(player.getPosition());
                        log("✅ " + player.getName() + " прибыл на " + currentCell.getName());
                        gameEngine.addMessage(moveCard.getDescription());
                        drawBoard();
                        gameEngine.land();
                    });
                } else {
                    animatePlayerMove(player, steps, () -> {
                        Cell currentCell = gameEngine.getBoard().getCell(player.getPosition());
                        log("✅ " + player.getName() + " прибыл на " + currentCell.getName());
                        gameEngine.addMessage(moveCard.getDescription());
                        drawBoard();
                        gameEngine.land();
                    });
                }
            } else {
                gameEngine.addMessage(moveCard.getDescription());
                drawBoard();
                gameEngine.land();
            }
        } else if (event instanceof GoToClosestPropertyCard closestCard) {
            log("📍 " + player.getName() + " ищет ближайшую собственность");
            closestCard.apply(player, gameEngine);
            Cell currentCell = gameEngine.getBoard().getCell(player.getPosition());
            log("✅ " + player.getName() + " прибыл на " + currentCell.getName());
            gameEngine.addMessage(closestCard.getDescription());
            drawBoard();
            gameEngine.land();
        } else {
            int balanceBefore = player.getBalance();
            event.apply(player, gameEngine);
            int balanceAfter = player.getBalance();
            int balanceChange = balanceAfter - balanceBefore;
            
            if (balanceChange > 0) {
                log("💰 " + player.getName() + " получил " + balanceChange + " ₽");
            } else if (balanceChange < 0) {
                log("💸 " + player.getName() + " заплатил " + Math.abs(balanceChange) + " ₽");
            }
            
            log("📋 " + player.getName() + ": " + event.getDescription());
            drawBoard();
            updatePlayerBalance(player);
        }
    }

    private int getTargetPosition(GoToNCellCard card) {
        try {
            Field positionField = GoToNCellCard.class.getDeclaredField("position");
            positionField.setAccessible(true);
            return (Integer) positionField.get(card);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getMoveSteps(MoveToNCellsCard card) {
        try {
            Field stepsField = MoveToNCellsCard.class.getDeclaredField("steps");
            stepsField.setAccessible(true);
            return (Integer) stepsField.get(card);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int calculateSteps(int currentPos, int targetPos) {
        if (targetPos >= currentPos) {
            return targetPos - currentPos;
        } else {
            return (gameEngine.getBoard().getSize() - currentPos) + targetPos;
        }
    }


    private void showStreetInfo(Property property) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StreetInfoDialog.fxml"));
            Parent pane = loader.load();

            StreetInfoController controller = loader.getController();
            Player currentPlayer = gameEngine.getCurrentPlayer();
            controller.setData(property, currentPlayer, gameEngine, () -> {
                drawBoard();
                updatePlayerProperties();
                updatePlayerBalance(currentPlayer);
            });

            Stage stage = new Stage();
            stage.setTitle(property.getName());
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane, 400, 500);
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

        List<Property> sortedProperties = new ArrayList<>(current.getProperty());
        sortedProperties.sort((p1, p2) -> {
            boolean p1IsStreet = p1 instanceof Street;
            boolean p2IsStreet = p2 instanceof Street;
            
            if (p1IsStreet && !p2IsStreet) return -1;
            if (!p1IsStreet && p2IsStreet) return 1;
            
            if (p1IsStreet && p2IsStreet) {
                Street s1 = (Street) p1;
                Street s2 = (Street) p2;
                int colorCompare = s1.getColorGroup().compareTo(s2.getColorGroup());
                if (colorCompare != 0) return colorCompare;
                return p1.getName().compareTo(p2.getName());
            }
            
            int typeCompare = p1.getCellType().compareTo(p2.getCellType());
            if (typeCompare != 0) return typeCompare;
            return p1.getName().compareTo(p2.getName());
        });

        for (Property property : sortedProperties) {
            Label propertyLabel = new Label(property.getName());
            propertyLabel.setTextFill(getColorForCell(property));
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
        playerBalanceLabel.setText("💰 Баланс: " + player.getBalance() + " $");
        int moves = gameEngine.hasMovesSteps() ? gameEngine.getMovesLeft() : 0;

        animatePlayerMove(player, moves, () -> {
            gameEngine.land();
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
        currentState = WAITING_FOR_ROLL;
        updateUIState();
        drawPlayers();
        updatePlayerProperties();
    }

    @FXML
    public void initialize() {
        setButtonEnabled(rollDiceButton, true);
        setButtonEnabled(endTurnButton, true);
    }

    @Override
    public void onPropertyAvailable(Player player, Property property) {
        Platform.runLater(() -> showBuyPropertyWindow(player, property));
    }

    @Override
    public void onCardDrawn(Player player, Event event) {
        log("🎴 " + player.getName() + " вытянул карту: " + event.getDescription());
        Platform.runLater(() -> showCardDialog(player, event));
    }

    @Override
    public void onRandomEvent(Player player, RandomEvent event) {
        log("🎴 " + player.getName() + " вытянул карту: " + event.getDescription());
        Platform.runLater(() -> showCardDialog(player, event));
        updatePlayerBalance(player);
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
        if (cell.getPosition() == 0) {
            log("🎁 " + player.getName() + " прошел через старт и получил 200 ₽");
            updatePlayerBalance(player);
        }
    }

    @Override
    public void onCellLanded(Player player, Cell cell) {
        if (cell instanceof Property property) {
            if (!property.hasOwner()) {
                log("🏪 Клетка " + property.getName() + " свободна. Цена: " + property.getPrice() + " ₽");
                Platform.runLater(() -> showBuyPropertyWindow(player, property));
                updatePlayerBalance(player);
                return;
            }
            if (property.getOwner() != player) {
                int rent = property.getRentPrice();
                int balanceBefore = player.getBalance();
                log("💸 " + player.getName() + " платит аренду " + rent + " ₽ игроку " + property.getOwner().getName());
                log("💰 Баланс " + player.getName() + ": " + balanceBefore + " ₽ → " + (balanceBefore - rent) + " ₽");
                log("💰 Баланс " + property.getOwner().getName() + ": " + property.getOwner().getBalance() + " ₽ → " + (property.getOwner().getBalance() + rent) + " ₽");
                updatePlayerBalance(player);
            } else {
                log("✅ " + player.getName() + " владеет этой собственностью");
            }
        } else if (cell instanceof CardCellImpl cardCell) {
            String cardType = cardCell.getCellType() == CellType.CHANCE ? "Шанс" : "Общественная казна";
            log("🎴 " + player.getName() + " попал на клетку " + cardType);
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(e -> {
            });
            pause.play();
        } else {
            log("ℹ️ " + cell.getDescription());
        }
        updatePlayerBalance(player);
    }

    @Override
    public void onBalanceChanged(Player player, int newBalance) {
        log("💰 Баланс " + player.getName() + " изменен: " + newBalance + " ₽");
        updatePlayerBalance(player);
    }

    @Override
    public void onGameStarted(List<Player> players) {
        log("🎮 Игра началась!");
        log("👥 Игроки:");
        for (Player player : players) {
            log("   • " + player.getName() + " (стартовый баланс: " + player.getBalance() + " ₽)");
        }
        updateCurrentPlayer();
    }

    @Override
    public void onTurnEnded(Player nextPlayer) {
        log("Ход закончился. Следующий игрок: " + nextPlayer.getName());
        updateCurrentPlayer();
        updatePlayerProperties();
    }

    @Override
    public void onMessage(String message) {
        log("📢 " + message);
    }

    @Override
    public void onGameOver(Player winner) {
        log("🏆 ИГРА ОКОНЧЕНА!");
        log("🎉 Победитель: " + winner.getName() + " с балансом " + winner.getBalance() + " ₽");
        log("📊 Итоговые результаты:");
        for (Player player : gameEngine.getPlayers()) {
            log("   • " + player.getName() + ": " + player.getBalance() + " ₽ (" + player.getProperty().size() + " собственностей)");
        }
    }

    private void updateCurrentPlayer() {
        Player p = gameEngine.getCurrentPlayer();
        currentPlayerLabel.setText("Ходит: " + p.getName());
        updatePlayerBalance(p);
    }
    
    private void updatePlayerBalance(Player player) {
        if (playerBalanceLabel != null && player != null) {
            playerBalanceLabel.setText("💰 Баланс: " + player.getBalance() + " ₽");
        }
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

    private void updateUIState() {
        switch (currentState) {
            case WAITING_FOR_ROLL:
                rollDiceButton.setDisable(false);
                endTurnButton.setDisable(true);
                break;

            case MOVING:
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(true);
                break;

            case BUYING_PROPERTY:

            case PAYING_RENT:
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(false);
                break;

            case GAME_OVER:
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(true);
                break;

            case END_TURN:
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(false);
        }

        updateButtonStyles();
    }

    private void updateButtonStyles() {
        if (rollDiceButton != null) {
            if (rollDiceButton.isDisabled()) {
                rollDiceButton.setStyle(
                        "-fx-background-color: linear-gradient(to right, rgba(245, 194, 231, 0.6), rgba(203, 166, 247, 0.6)); " +
                                "-fx-text-fill: rgba(17, 17, 27, 0.6); " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-font-size: 14px; " +
                                "-fx-opacity: 0.7; " +
                                "-fx-cursor: default;"
                );
            } else {
                rollDiceButton.setStyle(
                        "-fx-background-color: linear-gradient(to right, #f5c2e7, #cba6f7); " +
                                "-fx-text-fill: #11111b; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 12 25; " +
                                "-fx-font-size: 14px; " +
                                "-fx-cursor: hand;"
                );
            }
        }
        if (endTurnButton != null) {
            if (endTurnButton.isDisabled()) {
                endTurnButton.setStyle(
                        "-fx-background-color: rgba(49, 50, 68, 0.6); " +
                                "-fx-text-fill: rgba(205, 214, 244, 0.6); " +
                                "-fx-padding: 12 25; " +
                                "-fx-font-size: 14px; " +
                                "-fx-opacity: 0.7; " +
                                "-fx-cursor: default;"
                );
            } else {
                endTurnButton.setStyle(
                        "-fx-background-color: #313244; " +
                                "-fx-text-fill: #cdd6f4; " +
                                "-fx-padding: 12 25; " +
                                "-fx-font-size: 14px; " +
                                "-fx-cursor: hand;"
                );
            }
        }
    }

    private void setButtonEnabled(Button button ,boolean enabled){
        if (button != null) {
            button.setDisable(!enabled);
            button.setOpacity(enabled ? 1.0 : 0.5);
        }
    }
}

