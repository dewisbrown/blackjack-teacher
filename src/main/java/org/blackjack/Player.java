package org.blackjack;
import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final ArrayList<Card> hand;
    private String name;

    public Player() {
        hand = new ArrayList<>();
    }

    public void hit(Card card) {
        hand.add(card);
    }

    public boolean canSplit() {
        return hand.size() == 2 && Objects.equals(hand.get(0).getRank(), hand.get(1).getRank());
    }

    public void clearHand() {
        if (!hand.isEmpty()) {
            hand.clear();
        }
    }

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return value;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
