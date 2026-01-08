package ru.vsu.oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameState;
import ru.vsu.oop.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import ru.vsu.oop.ui.GameFactory;
import ru.vsu.oop.controller.GameFxController;
public class MonopolyFxApp extends Application {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        GameState gameState = GameFactory.createGameState();
        GameEngine engine = new GameEngineImpl(gameState);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/game.fxml")
        );

        Parent root = loader.load();

        GameFxController controller = loader.getController();
        controller.setGameEngine(engine);

        stage.setTitle("Monopoly");
        stage.setScene(new Scene(root, 1150, 800));
        stage.setMinWidth(1150);
        stage.setMinHeight(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}