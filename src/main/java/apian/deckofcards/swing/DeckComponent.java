package apian.deckofcards.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComponent;

import apian.deckofcards.Card;
import apian.deckofcards.Deck;
import lombok.Setter;

/**
 * Swing component to show graphically a deck and all dealt cards. It provides
 * two method to {@link #shuffle} the deck and {@link #deal} a card. This
 * component is responsive, adapting the size of the cards to the size of the
 * available area.
 */
public class DeckComponent extends JComponent {

   private static final long serialVersionUID = 2755973252752123337L;

   /**
    * Number or cards per row in the dealt cards section.
    */
   private static final int DEALT_ROW = 13;
   
   /**
    * Font for cards.
    */
   private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

   /**
    * Deck that is shown in this component.
    */
   private Deck deck = new Deck();

   /**
    * List of dealt cards.
    */
   private List<Card> dealts = new ArrayList<>();
   
   /**
    * Function to send messages to status bar.
    */
   @Setter
   private Consumer<String> notifier = s -> {};

   /**
    * Returns all dealt cards to deck and shuffle deck.
    */
   public void shuffle() {
      deck.shuffle();
      dealts.clear();
      notifier.accept("Deck shuffled");
      repaint();
   }

   /**
    * Deal one card from the deck.
    */
   public void deal() {
      deck.dealOneCard().ifPresentOrElse(
            c -> {
               dealts.add(c);
               notifier.accept("Dealt: " + c.getFullName());
            },
            () -> notifier.accept("There are no more cards"));
      repaint();
   }

   /**
    * Draws the back of a card.
    * 
    * @param x           Horizontal position.
    * @param y           Vetical position.
    * @param cardWidth   Width of the card.
    * @param cardHeight  Height of the card.
    * @param backPadding Width of the white border of the card.
    * @param g2          Graphics context.
    */
   private void drawCardBack(double x, double y, double cardWidth, double cardHeight, double backPadding,
         Graphics2D g2) {
      g2.setPaint(Color.white);
      g2.fillRoundRect((int) x, (int) y, (int) cardWidth, (int) cardHeight, 15, 15);
      g2.setStroke(new BasicStroke(1.0f));
      g2.setPaint(Color.gray);
      g2.drawRoundRect((int) x, (int) y, (int) cardWidth, (int) cardHeight, 15, 15);
      g2.setPaint(Color.blue.darker());
      g2.fillRoundRect((int) (x + backPadding), (int) (y + backPadding), (int) (cardWidth - 2 * backPadding),
            (int) (cardHeight - 2 * backPadding), 10, 10);

   }

   /**
    * Draws a card.
    * 
    * @param card       Card to be drawn.
    * @param x          Horizontal position.
    * @param y          Vertical position.
    * @param cardWidth  Width of the card.
    * @param cardHeight Height of the card.
    * @param g2         Graphics context.
    */
   private void draw(Card card, double x, double y, double cardWidth, double cardHeight, Graphics2D g2) {
      // Save transform because we will rotate to draw numbers upside down.
      AffineTransform oldTransform = g2.getTransform();

      g2.setPaint(Color.white);
      g2.fillRoundRect((int) x, (int) y, (int) cardWidth, (int) cardHeight, 15, 15);
      g2.setStroke(new BasicStroke(1.0f));
      g2.setPaint(Color.gray);
      g2.drawRoundRect((int) x, (int) y, (int) cardWidth, (int) cardHeight, 15, 15);
      g2.setPaint(Color.black);
      g2.drawString(card.getName(), (int) (x + 7), (int) (y + 20));
      g2.setPaint(cardColorToAwtColor(card.getSuit().getColor()));
      g2.drawString(card.getSuit().getSymbol(), (int) (x + 5), (int) (y + 40));

      // Rotate and draw number in the other corner of the card.
      g2.rotate(Math.PI, (int) (x + cardWidth / 2), (int) (y + cardHeight / 2));
      g2.setPaint(Color.black);
      g2.drawString(card.getName(), (int) (x + 7), (int) (y + 20));
      g2.setPaint(cardColorToAwtColor(card.getSuit().getColor()));
      g2.drawString(card.getSuit().getSymbol(), (int) (x + 5), (int) (y + 40));

      // Restore transform.
      g2.setTransform(oldTransform);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      Rectangle bounds = getBounds();

      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setFont(FONT);
      
      // Calculations based on the area occupied by this component.
      int dealtRows = (int) Math.ceil((double) deck.getLength() / DEALT_ROW);
      final double padding = bounds.height * 0.02;
      final double cardHeight = (bounds.height - 3 * padding) / (dealtRows / 2 + 1);
      final double cardWidth = cardHeight * (63.0d / 88.0d);
      final double backPadding = cardHeight * 0.05;

      // First, we draw the deck. All cards are hidden.
      double backStep = (bounds.width - cardWidth - 2 * padding) / (deck.getLength() - 1);
      for (int i = 0; i < deck.getRestLength(); ++i) {
         int x = (int) (bounds.width - cardWidth - padding - i * backStep);
         drawCardBack(x, (int) padding, cardWidth, cardHeight, backPadding, g2);
      }

      // Next, the dealt cards. All cards are visible.
      double dealtStepX = (bounds.width - cardWidth - 2 * padding) / (DEALT_ROW - 1);
      double dealtStepY = (bounds.height - 2 * cardHeight - 3 * padding) / (dealtRows - 1);
      for (int i = 0; i < dealts.size(); ++i) {
         int x = (int) (padding + (i % DEALT_ROW) * dealtStepX);
         int y = (int) (2 * padding + cardHeight + (i / DEALT_ROW) * dealtStepY);
         draw(dealts.get(i), x, y, cardWidth, cardHeight, g2);
      }
   }

   /**
    * Converts a color of a card to a Swing {@link Color}.
    * 
    * @param color Color of a card.
    * @return Swing Color.
    */
   private Color cardColorToAwtColor(Card.Color color) {
      switch (color) {
      case black:
         return Color.black;
      case red:
         return Color.red;
      default:
         return Color.black;
      }
   }

   /**
    * @return Default size for this component.
    */
   @Override
   public Dimension getPreferredSize() {
      return new Dimension(700, 438);
   }

}
