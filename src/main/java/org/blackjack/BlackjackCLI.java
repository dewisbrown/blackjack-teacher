package org.blackjack;

import java.util.*;

public class BlackjackCLI {
    private ArrayList<Card> mainDeck;
    private HashMap<Pair, Action> pairActionMap;
    private Random rng;
    private int cardCount;
    private boolean playing;

    private Player dealer;
    private Player player;

    private int correctCount;
    private int incorrectCount;


    public BlackjackCLI() {
        dealer = new Player();
        player = new Player();
        correctCount = 0;
        incorrectCount = 0;

        mainDeck = new ArrayList<>();
        pairActionMap = new HashMap<>();
        rng = new Random();
        cardCount = 52 * 6;
        initDeck();
        initMap();
        play();
    }

    public void play() {
        while (true) {
            dealCards();
            printHands();

            if (dealer.getHandValue() == 21) {
                System.out.println("---- Dealer Blackjack! ----");
                continue;
            }

            if (player.getHandValue() == 21) {
                System.out.println("---- Blackjack! ----");
                continue;
            }

            Action action = getAction();

            if (action == Action.EXIT) {
                printStats();
                break;
            }

            // Check if action is correct action
            if (isCorrectAction(action)) {
                System.out.println("Correct action!\n");
                correctCount++;
            } else {
                Action correctAction = getCorrectAction();
                System.out.println("Incorrect action!");
                System.out.printf("Correct action: %s%n%n", correctAction.toString());
                incorrectCount++;
            }

            /*
            if (player.getHandValue() < 22) {
                dealDealer();
            }

            if (player.getHandValue() == 21) {
                System.out.println("---- Win ----");
                winCount++;
            } else if (player.getHandValue() > 21) {
                System.out.println("---- Loss ----");
                lossCount++;
            } else if (dealer.getHandValue() > 21) {
                System.out.println("---- Win ----");
                winCount++;
            } else if (dealer.getHandValue() > player.getHandValue()) {
                System.out.println("---- Loss ----");
                lossCount++;
            } else if (dealer.getHandValue() < player.getHandValue()) {
                System.out.println("---- Win ----");
                winCount++;
            } else {
                System.out.println("---- Push ----");
                drawCount++;
            }
             */
        }
    }

    private boolean isCorrectAction(Action action) {
        Pair key = new Pair(player.getHandValue(), dealer.getHandValue());
        return pairActionMap.get(key) == action || pairActionMap.get(key) == Action.NONE;
    }

    private Action getCorrectAction() {
        Pair key = new Pair(player.getHandValue(), dealer.getHandValue());
        return pairActionMap.get(key);
    }

    private void dealCards() {
        dealer.clearHand();
        player.clearHand();

        dealer.hit(deal());
        for (int i = 0; i < 2; i++) {
            player.hit(deal());
        }
    }

    private void printHands() {
        System.out.print("Dealer: ");
        for (Card card : dealer.getHand()) {
            System.out.print(card);
        }
        System.out.println();

        System.out.print("Player: ");
        for (Card card : player.getHand()) {
            System.out.print(card);
        }
        System.out.println();
    }

    private Card deal() {
        if (cardCount < 52) {
            mainDeck.clear();
            initDeck();
        }
        return mainDeck.get(rng.nextInt(cardCount--));
    }

    private Action getAction() {
        System.out.println("HIT [1], STAND [2], DOUBLE [3], SPLIT [4], EXIT [5]");
        char c = getCharInput();

        switch (c) {
            case '1' -> {
                return Action.HIT;
            }
            case '2' -> {
                return Action.STAY;
            }
            case '3' -> {
                return Action.DOUBLE;
            }
            case '4' -> {
                if (!player.canSplit()) {
                    System.out.println("You cannot SPLIT");
                }
                return Action.SPLIT;
            }
            default -> {
                return Action.EXIT;
            }
        }
    }

    private boolean continueGame() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Play another hand? Y or N?");
        while (true) {
            char option = scnr.next().toUpperCase().charAt(0);
            if (option == 'Y') {
                return true;
            } else if (option == 'N') {
                return false;
            } else {
                System.out.println("Input Y or N");
            }
        }
    }

    private void printStats() {
        System.out.printf("%nCorrect: %d, Wrong: %d%n", correctCount, incorrectCount);
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

    private char getCharInput() {
        String validInputs = "12345";
        Scanner scnr = new Scanner(System.in);

        while (true) {
            String line = scnr.nextLine();
            if (line.length() == 0) {
                System.err.println("Enter a valid option: 1, 2, 3, 4");
                continue;
            }
            if (validInputs.indexOf(line.charAt(0)) > -1) {
                return line.charAt(0);
            } else {
                System.err.println("Enter a valid option: 1, 2, 3, 4");
            }
        }
    }
}
