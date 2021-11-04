package apian.deckofcards;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The <code>Card</code> class represents a single card. It 
 * contains information about her name/number, suit and color.
 */
@EqualsAndHashCode
public class Card {

   /**
    * All names of cards of a single suit. Number of names 
    * times number of suits gives the total number of 
    * possible cards in a deck.
    */
   private static final String[] NAMES = { 
         "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
   };

   /**
    * Maximum different cards in a suit.
    */
   public static final int CARDS_PER_SUIT = NAMES.length;

   /**
    * Suit of this card.
    */
   @Getter
   private Suit suit;

   /**
    * Number (zero based) of this card.
    */
   @Getter
   private int number;

   /**
    * Construct a card given its suit and number. Number is
    * zero based, so card Ace is 0, card two is 1, etc.
    * 
    * @param suit   Suit of this card.
    * @param number Number (zero based) of this card.
    */
   public Card(Suit suit, int number) {
      if (number >= NAMES.length || number < 0) {
         throw new IllegalArgumentException("Illegal card number: " + number);
      }
      this.suit = suit;
      this.number = number;
   }

   /**
    * @return Full name of this card. This name includes name of 
    *         the car (eg. J, 3) and suit.
    */
   public String getFullName() {
      return new StringBuilder()
            .append(getName())
            .append(" of ")
            .append(suit.name())
            .toString();
   }

   /**
    * @return Name of this card (eg. J, 3).
    */
   public String getName() {
      return NAMES[number];
   }

   /**
    * Enumeration with the possible colors of suits.
    */
   public enum Color {
      red, black
   }

   /**
    * Enumeration with all possible suits of a card. 
    * Includes information about its color and a 
    * symbol to represents the suit.
    */
   @AllArgsConstructor
   @Getter
   public enum Suit {
      diamonds("♦", Color.red), 
      clubs("♣", Color.black), 
      hearts("♥", Color.red),
      spades("♠", Color.black);

      /** Symbol that represents this suit. */
      final String symbol;

      /** Color of this suit. */
      final Color color;
   }
}
