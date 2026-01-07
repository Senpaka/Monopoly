package ru.vsu.oop.ru.vsu.oop.engine.model.events.staticEvents;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.StaticEvent;
import ru.vsu.oop.ru.vsu.oop.engine.api.game.GameEngine;
import ru.vsu.oop.ru.vsu.oop.engine.api.player.Player;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.board.CellType;
import ru.vsu.oop.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class GoToJailEvent implements StaticEvent {
    @Override
    public void apply(Player player, GameEngine gameEngine) {
        gameEngine.sendPlayerTo(player, 10);
        gameEngine.addMessage(player.getName() + " отправляется в тюрьму!");
    }

    @Override
    public String getDescription() {
        return "Отправляйся в тюрьму";
    }

    @Override
    public SpecialEffect getEffect() {
        return SpecialEffect.GO_TO_JAIL;
    }

    @Override
    public CellType getType() {
        return CellType.SPECIAL_CELL;
    }
}
