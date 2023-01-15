package stocks.view.guiscreens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import stocks.controller.Features;

/**
 * A GUI screen to display the necessary inputs which the user has to be done for Calculating
 * TotalValue and its corresponding validation message.
 */
public class ValueFlexiblePortfolioDateScreen extends JPanel {

  private JTextField oldPortfolioName;
  private JTextField date;
  private JButton valueButton;
  private Boolean isPortfolioNameValid = false;
  private Boolean isDateValid = false;

  /**
   * Constructor for the Class to call the feature obj to display the correct message.
   *
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public ValueFlexiblePortfolioDateScreen(Features features) {
    this.add(new JLabel("Value of a flexible portfolio:"));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSize(3000, 3000);

    oldPortfolioName = new JTextField(10);
    this.add(new JLabel("Enter existing portfolio name"));
    this.add(oldPortfolioName);

    date = new JTextField(10);
    this.add(new JLabel("Enter the date on which you want to find the" +
            " value of portfolio in the format yyyy-MM-dd.\n"));
    this.add(date);

    this.add(Box.createRigidArea(new Dimension(0, 5)));
    valueButton = new JButton("Get Value");
    valueButton.setEnabled(false);
    this.add(valueButton);

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

    date.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isDateValid = features.inputRightDateFormat(date.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isDateValid = features.inputRightDateFormat(date.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    valueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.getValueOfPortfolioDate(oldPortfolioName.getText(), date.getText());
      }
    });
  }

  void refreshButtonStatus() {
    valueButton.setEnabled(isPortfolioNameValid && isDateValid);
  }

}
