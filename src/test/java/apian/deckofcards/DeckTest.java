package apian.deckofcards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTest {

   static final double MIN_DIFF = 70.0d;

   private Deck deck;

   @BeforeEach
   public void before() {
      deck = new Deck();
   }

   @Test
   public void testDeal() {
      List<Card> dealt = new ArrayList<>();
      Assertions.assertEquals(deck.getLength(), deck.getRestLength(),
            () -> "Invalid initial deck size");
      for (int i = 0; i < deck.getLength(); ++i) {
         Optional<Card> card = deck.dealOneCard();
         Assertions.assertTrue(card.isPresent(), () -> "Empty card before end of deck");
         dealt.add(card.get());
      }
      for (int i = 0; i < 3; ++i) {
         Optional<Card> card = deck.dealOneCard();
         Assertions.assertTrue(card.isEmpty(), () -> "Non empty card after end of deck");
      }
      assertDistinct(dealt);
   }

   @Test
   public void testShuffle() {
      List<Card> initial = dealToList(deck);
      deck.shuffle();
      List<Card> shuffled = dealToList(deck);

      Assertions.assertEquals(initial.size(), shuffled.size(), "Different size after shuffle");

      double differencePercent = 0;
      for (int i = 0; i < initial.size(); ++i) {
         if (!initial.get(i).equals(shuffled.get(i))) {
            differencePercent += 100.0f / deck.getLength();
         }
      }
      Assertions.assertTrue(differencePercent > MIN_DIFF,
            "After shuffle, deck is less than " + MIN_DIFF + "% different");

   }

   private List<Card> dealToList(Deck deck) {
      List<Card> dealt = new ArrayList<>();
      for (int i = 0; i < deck.getLength(); ++i) {
         dealt.add(deck.dealOneCard().get());
      }
      assertDistinct(dealt);
      return dealt;
   }

   private void assertDistinct(Collection<Card> cards) {
      Set<String> distinctNames = cards.stream().map(Card::getFullName).collect(Collectors.toSet());
      Assertions.assertEquals(cards.size(), distinctNames.size(),
            () -> "There are cards duplicated or with the same full name");
   }
}
