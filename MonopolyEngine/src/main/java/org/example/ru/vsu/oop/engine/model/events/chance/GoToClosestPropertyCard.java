package org.example.ru.vsu.oop.engine.model.events.chance;

import org.example.ru.vsu.oop.engine.api.cell.Cell;
import org.example.ru.vsu.oop.engine.api.cell.Railway;
import org.example.ru.vsu.oop.engine.api.event.RandomEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;

public class GoToClosestPropertyCard implements RandomEvent {

    private CellType property;
    private String description;

    public GoToClosestPropertyCard(CellType property, String description) {
        this.property = property;
        this.description = description;
    }

    @Override
    public CellType getType() {
        return CellType.CHANCE;
    }

    @Override
    public void apply(Player player, GameEngine gameEngine) {

        int position = player.getPosition();
        Cell cell = gameEngine.getBoard().getCell(position);

        while (cell.getCellType() != this.property){
            position = (player.getPosition() + 1) % gameEngine.getBoard().getSize();
            player.setPosition(position);
            cell.onPass(player, gameEngine);
            cell = gameEngine.getBoard().getCell(position);
        }

        cell.onLand(player, gameEngine);
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
