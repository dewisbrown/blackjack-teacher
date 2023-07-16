package org.blackjack;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;
    private Random rng;
    private int numCards;

    public Deck() {
        deck = new ArrayList<>();
        for (int i = 2; i < 11; i++) {
            deck.add(new Card(Suit.C, i, Integer.toString(i)));
            deck.add(new Card(Suit.D, i, Integer.toString(i)));
            deck.add(new Card(Suit.H, i, Integer.toString(i)));
            deck.add(new Card(Suit.S, i, Integer.toString(i)));
        }

        deck.add(new Card(Suit.C, 10, "J"));
        deck.add(new Card(Suit.C, 10, "Q"));
        deck.add(new Card(Suit.C, 10, "K"));
        deck.add(new Card(Suit.C, 11, "A"));

        deck.add(new Card(Suit.D, 10, "J"));
        deck.add(new Card(Suit.D, 10, "Q"));
        deck.add(new Card(Suit.D, 10, "K"));
        deck.add(new Card(Suit.D, 11, "A"));

        deck.add(new Card(Suit.H, 10, "J"));
        deck.add(new Card(Suit.H, 10, "Q"));
        deck.add(new Card(Suit.H, 10, "K"));
        deck.add(new Card(Suit.H, 11, "A"));

        deck.add(new Card(Suit.S, 10, "J"));
        deck.add(new Card(Suit.S, 10, "Q"));
        deck.add(new Card(Suit.S, 10, "K"));
        deck.add(new Card(Suit.S, 11, "A"));

        // init rng
        rng = new Random();

        // init card count
        numCards = 52;
    }

    public Card dealCard() {
        Card card = deck.remove(rng.nextInt(numCards));
        numCards--;
        return card;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
