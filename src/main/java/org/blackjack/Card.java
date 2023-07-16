package org.blackjack;
public class Card {
    private Suit suit;
    private String rank;
    private int value;
    
    public Card(Suit suit, int value, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getRank() {
        return rank;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + rank + getSuitSymbol() + "]";
    }

    private char getSuitSymbol() {
        switch(suit) {
            case C: return '\u2663';
            case D: return '\u2666';
            case H: return '\u2665';
            case S: return '\u2660';
            default: return 'X';
        }
    }
}
