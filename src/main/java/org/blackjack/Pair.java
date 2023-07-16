package org.blackjack;

public class Pair {
    private int player;
    private int dealer;

    public Pair(int player, int dealer) {
        this.player = player;
        this.dealer = dealer;
    }

    @Override
    public String toString() {
        return String.format("[%d] - [%d]", player, dealer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;
        return pair.player == player && pair.dealer == dealer;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(player) * 31 + Integer.hashCode(dealer);
    }
}
