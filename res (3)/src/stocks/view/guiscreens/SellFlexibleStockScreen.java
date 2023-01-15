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
 * A GUI screen to display the necessary inputs which the user has to be done for Selling stocks on
 * Flexible Portfolio and its corresponding validation message.
 */
public class SellFlexibleStockScreen extends JPanel {


  private JTextField oldPortfolioName;
  private JTextField stock;
  private JTextField date;
  private JTextField quantity;
  private JTextField fee;
  private JButton sellStocksButton;
  private Boolean isPortfolioNameValid = false;
  private Boolean isStockValid = false;
  private Boolean isDateValid = false;
  private Boolean isQuantityValid = false;
  private Boolean isFeeValid = false;

  /**
   * Constructor for the Class to call the feature obj to display the correct message.
   *
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public SellFlexibleStockScreen(Features features) {

    this.add(new JLabel("Selling Stocks for an existing flexible portfolio:"));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSize(3000, 3000);

    oldPortfolioName = new JTextField(10);
    this.add(new JLabel("Enter existing portfolio name"));
    this.add(oldPortfolioName);

    stock = new JTextField(10);
    this.add(new JLabel("Enter symbol of stock to be sold:"));
    this.add(stock);

    date = new JTextField(10);
    this.add(new JLabel("Enter the date on which you want to make" +
            "the transaction for the stock in the format yyyy-MM-dd."));
    this.add(date);

    quantity = new JTextField(10);
    this.add(new JLabel("Enter a non-Fractional Positive quantity of stock to be sold:"));
    this.add(quantity);

    fee = new JTextField(10);
    this.add(new JLabel("Enter a non-Fractional Positive Commission Fee:"));
    this.add(fee);

    this.add(Box.createRigidArea(new Dimension(0, 5)));
    sellStocksButton = new JButton("Sell Stocks");
    sellStocksButton.setEnabled(false);
    this.add(sellStocksButton);

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

    stock.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isStockValid = features.inputValidStock(stock.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isStockValid = features.inputValidStock(stock.getText());
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
        isDateValid = features.inputDateForwardInTime(date.getText(),
                stock.getText(), oldPortfolioName.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isDateValid = features.inputDateForwardInTime(date.getText(),
                stock.getText(), oldPortfolioName.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    quantity.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isQuantityValid = features.inputSellQuantity(oldPortfolioName.getText(),
                stock.getText(), date.getText(), quantity.getText());
        refreshButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isQuantityValid = features.inputSellQuantity(oldPortfolioName.getText(),
                stock.getText(), date.getText(), quantity.getText());
        refreshButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      } //No need for plain text fields.

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

    sellStocksButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.sellStockFlexible(oldPortfolioName.getText(),
                stock.getText(), date.getText(),
                Double.parseDouble(quantity.getText()), Double.parseDouble(fee.getText()));
      }
    });
  }

  void refreshButtonStatus() {
    sellStocksButton.setEnabled(isPortfolioNameValid && isStockValid
            && isDateValid && isQuantityValid && isFeeValid);
  }

}
