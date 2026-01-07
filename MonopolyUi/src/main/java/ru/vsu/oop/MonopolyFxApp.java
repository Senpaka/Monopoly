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

    @Override
    public void start(Stage stage) throws Exception {

        GameState gameState = GameFactory.createGameState();
        GameEngine engine = new GameEngineImpl(gameState);
        System.out.println(engine);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/game.fxml")
        );

        Parent root = loader.load();

        GameFxController controller = loader.getController();
        controller.setGameEngine(engine);

        engine.setListener(controller);

        stage.setTitle("Monopoly");
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
