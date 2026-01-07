package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.game.GameState;
import org.example.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import org.example.ui.GameFactory;
import org.example.ui.GameFxController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        GameState gameState = GameFactory.createGameState();
        GameEngine engine = new GameEngineImpl(gameState);
        System.out.println(engine);

        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/fxml/game.fxml")
        );

        GameFxController controller = new GameFxController();
        controller.setGameEngine(engine);


        System.out.println(controller.gameEngine);

    }
}