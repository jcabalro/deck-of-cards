package apian.deckofcards;

import java.util.Optional;

import apian.deckofcards.Card.Suit;

/**
 * The <code>Deck</code> class represents a deck containing
 * all possible distinct {@link Card}s. It provides two
 * operations to {@link #shuffle} and {@link #dealOneCard}.
 */
public class Deck {

   /** Cards of this deck. */
   private Card[] cards = new Card[52];

   /** Next card to be dealt. */
   private int top = 0;

   /**
    * Construct a new deck with all its cards in numerical
    * order.
    */
   public Deck() {
      int index = 0;
      for (Suit suit : Suit.values()) {
         for (int number = 0; number < Card.CARDS_PER_SUIT; number++) {
            cards[index++] = new Card(suit, number);
         }
      }
   }

   /**
    * Shuffles cards of this deck.
    */
   public void shuffle() {
      for (int i = 0; i < cards.length; ++i) {
         int otherPosition = (int) (Math.random() * cards.length);
         Card aux = cards[i];
         cards[i] = cards[otherPosition];
         cards[otherPosition] = aux;
      }
      top = 0;
   }

   /**
    * Deals one card. If there is no cards available, it
    * returns an empty {@link Optional}.
    * 
    * @return Dealt card or nothing.
    */
   public Optional<Card> dealOneCard() {
      return top >= cards.length ? Optional.<Card>empty() : Optional.of(cards[top++]);
   }

   /**
    * @return Total number of cards of this deck.
    */
   public int getLength() {
      return cards.length;
   }

   /**
    * @return Number of cards that have not been yet dealt.
    */
   public int getRestLength() {
      return cards.length - top;
   }

}
