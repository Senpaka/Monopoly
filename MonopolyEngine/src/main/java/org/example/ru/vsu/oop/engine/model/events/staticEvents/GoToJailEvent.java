package org.example.ru.vsu.oop.engine.model.events.staticEvents;

import org.example.ru.vsu.oop.engine.api.event.StaticEvent;
import org.example.ru.vsu.oop.engine.api.game.GameEngine;
import org.example.ru.vsu.oop.engine.api.player.Player;
import org.example.ru.vsu.oop.engine.model.enumObject.event.SpecialEffect;

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
}
