package stocks.view.guiscreens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import stocks.controller.Features;

/**
 * A GUI screen to display the necessary inputs which the user has to be done for Creating
 * Flexible portfolio and its corresponding validation message.
 */

public class CreateFlexiblePortfolioScreen extends JPanel {
  private JTextField newPortfolioName;
  private JTextField firstStock;
  private JTextField date;
  private JTextField quantity;
  private JTextField fee;
  private JButton createPortfolioButton;
  private Boolean isPortfolioNameValid = false;
  private Boolean isStockValid = false;
  private Boolean isDateValid = false;
  private Boolean isQuantityValid = false;
  private Boolean isFeeValid = false;
  private JPanel strategyPanel = new JPanel();
  private JButton noStrategy = new JButton("End Task");
  private JButton yesStrategy = new JButton("Create Dollar cost strategy");

  /**
   * Constructor to create the GUI Frame to enter details to create a portfolio.
   *
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public CreateFlexiblePortfolioScreen(Features features) {

    this.add(new JLabel("Creating a flexible portfolio:"));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSize(3000, 3000);

    newPortfolioName = new JTextField(10);
    this.add(new JLabel("Enter new portfolio name"));
    this.add(newPortfolioName);

    firstStock = new JTextField(10);
    this.add(new JLabel("Enter symbol of stock to be purchased:"));
    this.add(firstStock);

    date = new JTextField(10);
    this.add(new JLabel("Enter the date on which you want to make" +
            "the transaction the stock in the format yyyy-MM-dd."));
    this.add(date);

    quantity = new JTextField(10);
    this.add(new JLabel("Enter a non-Fractional Positive quantity of stock to be bought:"));
    this.add(quantity);

    fee = new JTextField(10);
    this.add(new JLabel("Enter a non-Fractional Positive Commission Fee:"));
    this.add(fee);

    this.add(Box.createRigidArea(new Dimension(0, 5)));
    createPortfolioButton = new JButton("Create Portfolio");
    createPortfolioButton.setEnabled(false);
    this.add(createPortfolioButton);

    createActionListeners(features);

  }

  private void createActionListeners(Features features) {

    newPortfolioName.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputNewPortfolioName(newPortfolioName.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputNewPortfolioName(newPortfolioName.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      } // Not needed for plain-text fields

    });

    firstStock.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isStockValid = features.inputValidStock(firstStock.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isStockValid = features.inputValidStock(firstStock.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      } // No need for plain text fields.

    });

    date.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isDateValid = features.inputDateNormal(date.getText(), firstStock.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isDateValid = features.inputDateNormal(date.getText(), firstStock.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      } //No need for plain text fields.

    });

    quantity.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isQuantityValid = features.inputIntegerNormal(quantity.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isQuantityValid = features.inputIntegerNormal(quantity.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    fee.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isFeeValid = features.inputIntegerNormal(fee.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isFeeValid = features.inputIntegerNormal(fee.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    createPortfolioButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        features.createFlexiblePortfolio(newPortfolioName.getText(),
                firstStock.getText(), date.getText(),
                Double.parseDouble(quantity.getText()), Double.parseDouble(fee.getText()));
        newPortfolioName.setEnabled(false);
        firstStock.setEnabled(false);
        date.setEnabled(false);
        quantity.setEnabled(false);
        fee.setEnabled(false);
        createPortfolioButton.setEnabled(false);
        initStrategyPanel();
      }
    });

    noStrategy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.home();
      }
    });

    yesStrategy.addActionListener(l -> {
      this.removeAll();
      this.add(new JLabel(""));
      this.add(new DollarCostStrategyScreen(features, newPortfolioName.getText(), date.getText()));
      this.updateUI();
    });

  }

  private void initDollarCostStrategy() {
    //ignore
  }

  private void initStrategyPanel() {
    strategyPanel.add(noStrategy);
    strategyPanel.add(yesStrategy);
    this.add(strategyPanel);
    strategyPanel.updateUI();
    this.updateUI();
  }

  void refreshButtonStatus() {
    createPortfolioButton.setEnabled(isPortfolioNameValid && isStockValid
            && isDateValid && isQuantityValid && isFeeValid);
  }

}
