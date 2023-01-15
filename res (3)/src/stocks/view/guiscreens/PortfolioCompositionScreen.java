package stocks.view.guiscreens;

import java.awt.Dimension;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Box;
import stocks.controller.Features;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * A GUI screen to display the necessary inputs which the user has to be done for checking
 * composition of a portfolio and its corresponding validation message.
 */
public class PortfolioCompositionScreen extends JPanel {

  private JTextField oldPortfolioName;
  private Boolean isPortfolioNameValid = false;
  private JButton compositionButton;
  private JPanel compositionPanel = new JPanel();

  /**
   * Constructor to call the corresponding feature.
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public PortfolioCompositionScreen(Features features) {
    this.add(new JLabel("Cost Basis of a flexible portfolio:"));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSize(3000, 3000);

    oldPortfolioName = new JTextField(10);
    this.add(new JLabel("Enter existing portfolio name"));
    this.add(oldPortfolioName);

    this.add(Box.createRigidArea(new Dimension(0, 5)));
    compositionButton = new JButton("Get Composition");
    compositionButton.setEnabled(false);
    this.add(compositionButton);

    createActionListeners(features);
  }

  private void createActionListeners(Features features) {

    oldPortfolioName.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputOldPortfolioName(oldPortfolioName.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputOldPortfolioName(oldPortfolioName.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    compositionButton.addActionListener(l -> {
      String composition = features.getComposition(this.oldPortfolioName.getText());

      compositionPanel.removeAll();
      compositionPanel.setLayout(new BoxLayout(compositionPanel, BoxLayout.Y_AXIS));
      compositionPanel.setSize(3000, 3000);
      compositionPanel.add(new JLabel("\n"));
      String[] compositionData = composition.split("\n");
      for (int i = 0; i < compositionData.length; i++) {
        compositionPanel.add(new JLabel(compositionData[i]));
      }
      add(compositionPanel);
      this.updateUI();
    });
  }

  private void refreshButtonStatus() {
    compositionButton.setEnabled(isPortfolioNameValid);
  }
}
