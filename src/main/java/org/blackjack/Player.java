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
        int[] aceIndex = new int[10];
        boolean hasAce = false;
        int j = 0;

        for (int i = 0; i < hand.size(); i++) {
            if (Objects.equals(hand.get(i).getRank(), "A")) {
                hasAce = true;
                aceIndex[j] = i;
                j++;
            }
            value += hand.get(i).getValue();
        }

        // change ace value to 1 if hand value > 21
        while (value > 21 && hasAce) {
            hand.get(aceIndex[--j]).setValue(1);
            value -= 10;
        }

        return value;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
