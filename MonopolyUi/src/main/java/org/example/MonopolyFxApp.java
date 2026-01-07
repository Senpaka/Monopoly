package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameState;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ui.GameFactory;
import org.example.ui.GameFxController;

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
