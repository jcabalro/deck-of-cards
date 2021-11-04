# Deck of Cards

This application implements the **Deck of Cards Problem**. Its a 
Maven project with only four classes (and a few JUnit tests):

- `Card.java` represents a single card, with information about its name, suit and color.
- `Deck.java` represents a deck of cards. It implements the two 
operations required by the problem statement: `shuffle` and `dealOneCard`.
- `DeckComponent.java` is a simple Swing component to show the deck and
dealt cards.
- `Application.java` is the entry point of the program. It shows a
frame with a `DeckComponent` and two buttons for shuffle deck and deal card.

To run the program, assuming maven is installed, you can enter in
a console while you are in the same directory as `pom.xml`:

```
mvn compile exec:java
```


