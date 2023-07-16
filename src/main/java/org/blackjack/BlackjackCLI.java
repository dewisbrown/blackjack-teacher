package org.blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BlackjackCLI {
    private ArrayList<Card> mainDeck;
    private Random rng;
    private int cardCount;
    private boolean playing;

    private Player dealer;
    private Player player;

    private int winCount;
    private int drawCount;
    private int lossCount;

    public BlackjackCLI() {
        dealer = new Player();
        player = new Player();
        winCount = 0;
        drawCount = 0;
        lossCount = 0;

        mainDeck = new ArrayList<>();
        rng = new Random();
        cardCount = 52 * 6;
        initDeck();

        play();
    }

    public void play() {
        playing = true;
        while (playing) {
            dealCards();
            printHands();

            if (player.getHandValue() == 21) {
                System.out.println("---- Blackjack! ----");
                continue;
            }

            Action action = getAction();
            while (action != Action.STAY) {
                action = getAction();
            }

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

            playing = continueGame();
        }
        printStats();
    }

    private void dealCards() {
        dealer.clearHand();
        player.clearHand();

        dealer.hit(deal());
        for (int i = 0; i < 2; i++) {
            player.hit(deal());
        }
    }

    private void dealDealer() {
        while (dealer.getHandValue() < 17) {
            dealer.hit(deal());
            printHands();
            System.out.println();
        }
    }

    private void printHands() {
        System.out.print("Dealer: ");
        for (Card card : dealer.getHand()) {
            System.out.print(card);
        }
        System.out.println(" - " + dealer.getHandValue());

        System.out.print("Player: ");
        for (Card card : player.getHand()) {
            System.out.print(card);
        }
        System.out.println(" - " + player.getHandValue());
    }

    private Card deal() {
        if (cardCount < 52) {
            mainDeck.clear();
            initDeck();
        }
        return mainDeck.get(rng.nextInt(cardCount--));
    }

    private Action getAction() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("HIT [1], STAND [2], DOUBLE [3], SPLIT [4]");
        char c = scnr.nextLine().charAt(0);
        switch (c) {
            case '1': {
                player.hit(deal());
                printHands();
                if (player.getHandValue() >= 21) {
                    return Action.STAY;
                }
                return Action.HIT;
            }
            case '3': {
                player.hit(deal());
                printHands();
                return Action.STAY;
            }
            case '4': {
                if (player.canSplit()) {
                    System.out.println("You chose SPLIT");
                } else {
                    System.out.println("You cannot SPLIT");
                }
                return Action.SPLIT;
            }
            default: {
                return Action.STAY;
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
        System.out.printf("%nWins: %d, Losses: %d, Draws: %d", winCount, lossCount, drawCount);
    }

    private int getCardCount() {
        return cardCount;
    }

    private void initDeck() {
        for (int i = 0; i < 6; i++) {
            mainDeck.addAll(new Deck().getDeck());
        }
    }
}
