package org.blackjack;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

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
        Stack<Integer> aceIndex = new Stack<>();
        boolean hasAce = false;

        for (int i = 0; i < hand.size(); i++) {
            if (Objects.equals(hand.get(i).getRank(), "A")) {
                hasAce = true;
                aceIndex.push(i);
            }
            value += hand.get(i).getValue();
        }

        // change ace value to 1 if hand value > 21 and recount
        while (value > 21 && hasAce) {
            hand.get(aceIndex.pop()).setValue(1);

            value = 0;
            for (Card card : hand) {
                value += card.getValue();
            }
        }

        return value;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
