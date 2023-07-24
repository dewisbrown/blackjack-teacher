package org.blackjack;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * Tests whether an ace's value changes to 1 if the hand value is over 21.
     */
    public void testAceValueChanging() {
        Player player = new Player();
        Card ace = new Card(Suit.C, 11, "A");
        Card ace2 = new Card(Suit.D, 11, "A");

        player.hit(ace);
        player.hit(ace2);
        assertEquals(12, player.getHandValue());
    }

    public void testAceValueChangesInFullHand() {
        Player player = new Player();

        player.hit(new Card(Suit.C, 11, "A"));
        player.hit(new Card(Suit.D, 4, "4"));
        player.hit(new Card(Suit.D, 5, "5"));
        player.hit(new Card(Suit.D, 6, "6"));
        player.hit(new Card(Suit.D, 11, "A"));
        player.hit(new Card(Suit.D, 3, "3"));

        assertEquals(20, player.getHandValue());
    }

    public void testPlayerCanSplitTwoCards() {
        Player player = new Player();
        Card card1 = new Card(Suit.H, 5, "5");
        Card card2 = new Card(Suit.D, 5, "5");

        player.hit(card1);
        player.hit(card2);
        assertTrue(player.canSplit());
    }

    public void testPlayerCanSplitThreeCards() {
        Player player = new Player();
        Card card1 = new Card(Suit.H, 5, "5");
        Card card2 = new Card(Suit.D, 5, "5");
        Card card3 = new Card(Suit.C, 4, "4");

        player.hit(card1);
        player.hit(card2);
        player.hit(card3);
        assertFalse(player.canSplit());
    }

    public void testPlayerCanSplitTwoDifferentCards() {
        Player player = new Player();
        Card card1 = new Card(Suit.H, 5, "5");
        Card card2 = new Card(Suit.D, 3, "3");

        player.hit(card1);
        player.hit(card2);

        assertFalse(player.canSplit());
    }
}
