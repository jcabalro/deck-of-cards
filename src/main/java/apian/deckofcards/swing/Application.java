package apian.deckofcards.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Runs this program showing a simple GUI.
 */
public class Application extends JFrame {

   private static final long serialVersionUID = -7601381434801633371L;

   /**
    * Deck component to show deck and dealt cards.
    */
   private DeckComponent docComp = new DeckComponent();

   /**
    * Button to shuffle the deck.
    */
   private JButton shuffle = button("Shuffle", e -> docComp.shuffle());

   /**
    * Button to deal a card.
    */
   private JButton deal = button("Deal", e -> docComp.deal());
   
   /**
    * Text in the status bar.
    */
   private JLabel status = new JLabel("Deck of Cards");

   /**
    * Construct the GUI.
    */
   public Application() {
      setTitle("Deck of Cards - Juan Caballero Rosado");
      
      // Main panel
      JPanel main = new JPanel();
      main.setBackground(new Color(0x35654d));
      main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
      main.add(docComp);
      add(main, BorderLayout.CENTER);
      
      // Bottom buttons panel
      JPanel buttons = new JPanel();
      buttons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
      buttons.add(shuffle);
      buttons.add(Box.createHorizontalStrut(5));
      buttons.add(deal);
      buttons.setOpaque(false);
      main.add(buttons);
      
      // Status bar.
      JPanel statusBar = new JPanel();
      FlowLayout statusLayout = (FlowLayout) statusBar.getLayout();
      statusLayout.setAlignment(FlowLayout.LEFT);
      statusBar.add(status);
      add(statusBar, BorderLayout.SOUTH);

      docComp.setNotifier(this::showStatus);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
   }
   
   public void showStatus(String text) {
      SwingUtilities.invokeLater(() -> status.setText(text));
   }

   /**
    * Makes a new {@link JButton} based on a lambda action.
    * 
    * @param name   Label to show over the button.
    * @param action Action to do when button is clicked.
    * @return A JButton fully configured.
    */
   private JButton button(String name, Consumer<ActionEvent> action) {
      JButton btn = new JButton(new AbstractAction(name) {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent e) {
            action.accept(e);
         }
      });
      return btn;
   }

   /**
    * Run this application.
    */
   public static void main(String[] args) {
      Application app = new Application();
      app.setVisible(true);
   }

}
