package ru.vsu.oop;

import javafx.fxml.FXMLLoader;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameState;
import ru.vsu.oop.ru.vsu.oop.engine.impl.game.GameEngineImpl;
import ru.vsu.oop.ui.GameFactory;
import ru.vsu.oop.controller.GameFxController;

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