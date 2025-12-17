package org.example.ru.vsu.oop.engine.model.events.chance;

import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class GoToNCellChanceCard implements RandomEvent {

    int steps;

    public GoToNCellChanceCard(int steps) {
        this.steps = steps;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {
        player.move(this.steps);

        if (this.steps > 0){
            gameEngine.addMessage("Игрок: " + player.getName() + "перемещается на " + this.steps + "клеток вперед");
        }else{
            gameEngine.addMessage("Игрок: " + player.getName() + "перемещается на " + this.steps + "клеток назад");
        }
    }

    @Override
    public String getDescription() {
        if (this.steps > 0){
            return "Идите на " + this.steps + "клеток вперед";
        }
        return "Идите на " + this.steps + "Клеток назад";
    }
}
