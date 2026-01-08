package org.example.ru.vsu.oop.engine.model.events.staticEvents;

import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.board.CellType;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

public class NoEffectEvent implements StaticEvent {
    @Override
    public void apply(Player player, GameEngine gameEngine) {
        // Ничего не делаем
        gameEngine.addMessage("Ничего не происходит");
    }

    @Override
    public String getDescription() {
        return "Без эффекта";
    }

    @Override
    public SpecialEffect getEffect() {
        return SpecialEffect.NO_EFFECT;
    }

    @Override
    public CellType getType() {
        return CellType.SPECIAL_CELL;
    }
}
