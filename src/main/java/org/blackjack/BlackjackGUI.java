package org.blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BlackjackGUI {
    private ArrayList<Card> mainDeck;
    private HashMap<Pair, Action> pairActionMap;
    private Random rng;
    private int cardCount;
    private boolean playing;

    private Player dealer;
    private Player player;

    private int winCount;
    private int drawCount;
    private int lossCount;

    public BlackjackGUI() {
        dealer = new Player();
        player = new Player();
        winCount = 0;
        drawCount = 0;
        lossCount = 0;

        mainDeck = new ArrayList<>();
        pairActionMap = new HashMap<>();
        rng = new Random();
        cardCount = 52 * 6;
        initDeck();
        initMap();
    }

    public void dealCards() {
        dealer.clearHand();
        player.clearHand();

        dealer.hit(getCard());
        for (int i = 0; i < 2; i++) {
            player.hit(getCard());
        }
    }

    public void hitDealer() {
        while (dealer.getHandValue() < 17) {
            dealer.hit(getCard());
        }
        checkWin();
    }

    public Card getCard() {
        if (cardCount < 52) {
            mainDeck.clear();
            initDeck();
        }
        return mainDeck.get(rng.nextInt(cardCount--));
    }

    public void hitPlayer() {
        player.hit(getCard());
    }

    public boolean canHit() {
        return player.getHandValue() <= 21;
    }

    public boolean split() {
        return player.canSplit();
    }

    public String getStats() {
        return String.format("%nWins: %d, Losses: %d, Draws: %d", winCount, lossCount, drawCount);
    }

    public String getDealerHand() {
        StringBuilder sb = new StringBuilder();
        for (Card card : dealer.getHand()) {
            sb.append(card);
        }
        return sb.toString();
    }

    public String getPlayerHand() {
        StringBuilder sb = new StringBuilder();
        for (Card card : player.getHand()) {
            sb.append(card);
        }
        return sb.toString();
    }

    public boolean checkBust() {
        if (player.getHandValue() > 21) {
            lossCount++;
            return true;
        }

        return false;
    }

    public boolean isCorrectAction(Action action) {
        Pair key = new Pair(player.getHandValue(), dealer.getHandValue());
        return pairActionMap.get(key) == action || pairActionMap.get(key) == Action.NONE;
    }

    public Action getCorrectAction() {
        Pair key = new Pair(player.getHandValue(), dealer.getHandValue());
        return pairActionMap.get(key);
    }

    private void checkWin() {
        if (player.getHandValue() > 21) {
            lossCount++;
        } else if (dealer.getHandValue() > 21) {
            winCount++;
        } else if (dealer.getHandValue() > player.getHandValue()) {
            lossCount++;
        } else if (dealer.getHandValue() < player.getHandValue()) {
            winCount++;
        } else {
            drawCount++;
        }
    }

    private int getCardCount() {
        return cardCount;
    }

    private void initDeck() {
        for (int i = 0; i < 6; i++) {
            mainDeck.addAll(new Deck().getDeck());
        }
    }

    private void initMap() {
        for (int i = 18; i < 22; i++) {
            for (int j = 4; j < 22; j++) {
                pairActionMap.put(new Pair(i, j), Action.STAY);
            }
        }

        for (int i = 4; i < 8; i++) {
            for (int j = 2; j < 12; j++) {
                pairActionMap.put(new Pair(i, j), Action.HIT);
            }
        }

        for (int i = 4; i < 22; i++) {
            for (int j = 2; j < 8; j++) {
                pairActionMap.put(new Pair(i, j), Action.NONE);
            }
        }

        for (int i = 2; i < 12; i++) {
            pairActionMap.put(new Pair(8, i), Action.HIT);
            pairActionMap.put(new Pair(11, i), Action.DOUBLE);
            pairActionMap.put(new Pair(17, i), Action.STAY);
        }

        for (int i = 13; i < 17; i++) {
            for (int j = 2; j < 7; j++) {
                pairActionMap.put(new Pair(i, j), Action.STAY);
            }
        }

        for (int i = 12; i < 17; i++) {
            for (int j = 7; j < 12; j++) {
                pairActionMap.put(new Pair(i, j), Action.HIT);
            }
        }

        // mapping actions for 9
        pairActionMap.put(new Pair(9, 2), Action.HIT);

        for (int i = 3; i < 7; i++) {
            pairActionMap.put(new Pair(9, i), Action.STAY);
        }

        for (int i = 7; i < 12; i++) {
            pairActionMap.put(new Pair(9, i), Action.HIT);
        }

        // mapping actions for 10
        for (int i = 2; i < 10; i++) {
            pairActionMap.put(new Pair(10, i), Action.DOUBLE);
        }

        pairActionMap.put(new Pair(10, 10), Action.HIT);
        pairActionMap.put(new Pair(10, 11), Action.HIT);

        // mapping actions for 12
        pairActionMap.put(new Pair(12, 2), Action.HIT);
        pairActionMap.put(new Pair(12, 3), Action.HIT);

        for (int i = 4; i < 7; i++) {
            pairActionMap.put(new Pair(12, i), Action.STAY);
        }
    }
}
