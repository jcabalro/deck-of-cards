package apian.deckofcards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import apian.deckofcards.Card.Suit;

public class CardTest {

   @Test
   public void testSuitsNotNull() {
      for (Suit suit : Suit.values()) {
         Assertions.assertNotNull(suit.getColor());
         Assertions.assertNotNull(suit.getSymbol());
      }
   }

   @Test
   public void testFields() {
      Card card1 = new Card(Suit.diamonds, 3);
      Assertions.assertEquals(Suit.diamonds, card1.getSuit());
      Assertions.assertEquals(3, card1.getNumber());
      Assertions.assertEquals("4", card1.getName());
      Assertions.assertEquals("4 of diamonds", card1.getFullName());

      Card card2 = new Card(Suit.hearts, 0);
      Assertions.assertEquals(Suit.hearts, card2.getSuit());
      Assertions.assertEquals(0, card2.getNumber());
      Assertions.assertEquals("A", card2.getName());
      Assertions.assertEquals("A of hearts", card2.getFullName());

      Assertions.assertNotEquals(card1, card2, "Different cards must not be equals");

      Card card3 = new Card(card1.getSuit(), card1.getNumber());
      Assertions.assertEquals(card1, card3, "Equals cards must be equals");
      Assertions.assertEquals(card1.hashCode(), card3.hashCode(),
            "Equals cards must have same hash");

   }

   @Test
   public void testValidation() {
      try {
         new Card(Suit.clubs, Card.CARDS_PER_SUIT);
      } catch (IllegalArgumentException ex) {
         // OK
      }
      try {
         new Card(Suit.clubs, -1);
      } catch (IllegalArgumentException ex) {
         // OK
      }
   }
}
