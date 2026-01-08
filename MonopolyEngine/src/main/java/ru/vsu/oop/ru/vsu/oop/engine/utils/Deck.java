package ru.vsu.oop.ru.vsu.oop.engine.utils;

import ru.vsu.oop.ru.vsu.oop.engine.api.event.RandomEvent;

import java.util.*;

public class Deck {

    private Deque<RandomEvent> deck;

    public Deck(Deque<RandomEvent> deck) {
        this.deck = deck;
    }

    public void shuffle() {
        List<RandomEvent> deckList = new ArrayList<>(deck);
        Collections.shuffle(deckList);
        deck = new ArrayDeque<>(deckList);
    }

    public Deque<RandomEvent> getDeck() {
        return deck;
    }

    public RandomEvent drawCard(){
        RandomEvent event = deck.pollFirst();
        deck.addLast(event);
        return event;
    }
}
