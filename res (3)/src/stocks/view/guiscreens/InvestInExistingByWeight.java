package stocks.view.guiscreens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import stocks.controller.Features;

/**
 * A GUI screen to display the necessary inputs which the user has to be done for Invest in
 * Strategy by weight.
 */
public class InvestInExistingByWeight extends JPanel {

  private final JTextField oldPortfolioName;
  private final JTextField date;
  private final JTextField totalInvestment;
  private final JTextField commissionFee;
  private final JTextField noDistinctStock;
  private Boolean isPortfolioNameValid = false;
  private Boolean isDateValid = false;
  private Boolean isTotalInvestmentValid = false;
  private Boolean isCommissionFeeValid = false;
  private Boolean isNoDistinctStockValid = false;
  private final JButton continueButton1 = new JButton("Continue");
  private final List<List<String>> allInvestmentData = new ArrayList<>();
  List<String> detailsInitial = new ArrayList<>();
  private final JPanel mainPanel = new JPanel();
  JPanel stockRatioInputScreen = new JPanel();
  private Boolean[] isTickerArrayValid;
  private Boolean[] isWeightArrayValid;
  JTextField[] tickerArray;
  JTextField[] percentageArray ;
  Features features;
  private final JButton buyWeightedStocksButton = new JButton("Submit buy request");

  /**
   * Constructor to call the corresponding feature.
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public InvestInExistingByWeight(Features features) {
    this.features = features;
    mainPanel.add(new JLabel("Buying Stocks for an existing flexible portfolio:"));
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setSize(3000, 3000);

    oldPortfolioName = new JTextField(10);
    mainPanel.add(new JLabel("Enter existing portfolio name"));
    mainPanel.add(oldPortfolioName);

    date = new JTextField(10);
    mainPanel.add(new JLabel("Enter the date on which you want to make" +
            "the transaction for the stock in the format yyyy-MM-dd."));
    mainPanel.add(date);

    totalInvestment = new JTextField(10);
    mainPanel.add(new JLabel("Enter total investment to be done each time."));
    mainPanel.add(totalInvestment);

    commissionFee = new JTextField(10);
    mainPanel.add(new JLabel("Enter commission fee in USD for each " +
            "transaction done using this strategy."));
    mainPanel.add(commissionFee);

    noDistinctStock = new JTextField(10);
    mainPanel.add(new JLabel("Enter the no of different stocks " +
            "you wish to have in this strategy."));
    mainPanel.add(noDistinctStock);

    continueButton1.setEnabled(false);
    mainPanel.add(continueButton1);

    createActionListeners(features);
    this.add(mainPanel);
  }

  private void createActionListeners(Features features) {

    oldPortfolioName.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputOldPortfolioName(oldPortfolioName.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputOldPortfolioName(oldPortfolioName.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        // ignore
      } // Not needed for plain-text fields

    });

    date.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isDateValid = features.inputDateForwardInTime(date.getText(),
                "IBM", oldPortfolioName.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isDateValid = features.inputDateForwardInTime(date.getText(),
                "IBM", oldPortfolioName.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      } //No need for plain text fields.

    });

    totalInvestment.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isTotalInvestmentValid = features.inputAmountNormal(totalInvestment.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isTotalInvestmentValid = features.inputAmountNormal(totalInvestment.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    commissionFee.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isCommissionFeeValid = features.inputIntegerNormal(commissionFee.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isCommissionFeeValid = features.inputIntegerNormal(commissionFee.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    noDistinctStock.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isNoDistinctStockValid = features.inputIntegerNormal(noDistinctStock.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isNoDistinctStockValid = features.inputIntegerNormal(noDistinctStock.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    continueButton1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        detailsInitial.add(date.getText());
        detailsInitial.add(totalInvestment.getText());
        detailsInitial.add(commissionFee.getText());
        allInvestmentData.add(detailsInitial);

        mainPanel.removeAll();
        initStockRatioInput(Integer.parseInt(noDistinctStock.getText()));
        mainPanel.updateUI();
      }
    });

    buyWeightedStocksButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < Integer.parseInt(noDistinctStock.getText()); i++) {
          ArrayList<String> temp = new ArrayList<>();
          temp.add(tickerArray[i].getText());
          temp.add(percentageArray[i].getText());
          allInvestmentData.add(temp);
        }
        features.investByWeight(oldPortfolioName.getText(), allInvestmentData);
        mainPanel.removeAll();
        features.home();
      }
    });

  }

  private void initStockRatioInput(int noOfDistinctStocks) {

    stockRatioInputScreen = new JPanel();
    stockRatioInputScreen.setLayout(new BoxLayout(stockRatioInputScreen, BoxLayout.PAGE_AXIS));

    JScrollPane mainScrollPane = new JScrollPane(stockRatioInputScreen);
    mainScrollPane.setPreferredSize(new Dimension(200,200));

    stockRatioInputScreen.add(new JLabel("Add weights corresponding to stocks:"));
    //    stockRatioInputScreen.setSize(3000, 3000);
    //    mainScrollPane = new JScrollPane(stockRatioInputScreen);
    //    mainScrollPane.setPreferredSize(new Dimension(200,100));

    tickerArray = new JTextField[noOfDistinctStocks];
    percentageArray = new JTextField[noOfDistinctStocks];
    isTickerArrayValid = new Boolean[noOfDistinctStocks];
    isWeightArrayValid = new Boolean[noOfDistinctStocks];
    Arrays.fill(isTickerArrayValid, Boolean.FALSE);
    Arrays.fill(isWeightArrayValid, Boolean.FALSE);

    for (int i = 0; i < noOfDistinctStocks; ++i) {
      tickerArray[i] = new JTextField("");
      percentageArray[i] = new JTextField("");
      int index = i + 1;
      stockRatioInputScreen.add(new JLabel("Enter stock name " + index));
      stockRatioInputScreen.add(tickerArray[i]);

      stockRatioInputScreen.add(new JLabel("Enter integral weight for stock " + index));
      stockRatioInputScreen.add(percentageArray[i]);
    }

    buyWeightedStocksButton.setEnabled(false);
    stockRatioInputScreen.add(buyWeightedStocksButton);

    createActionListenersArrays();

    mainPanel.add(mainScrollPane);

  }

  void createActionListenersArrays() {
    for (int i = 0; i < Integer.parseInt(noDistinctStock.getText()); i++) {
      int finalI = i;
      tickerArray[finalI].getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
          isTickerArrayValid[finalI] = features.inputValidStock(tickerArray[finalI].getText());
          refreshContinueButton2Status();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
          isTickerArrayValid[finalI] = features.inputValidStock(tickerArray[finalI].getText());
          refreshContinueButton2Status();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
          // ignore
        }

      });

      percentageArray[finalI].getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
          isWeightArrayValid[finalI] =
                  features.inputWeightNormal(percentageArray[finalI].getText());
          refreshContinueButton2Status();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
          isWeightArrayValid[finalI] =
                  features.inputWeightNormal(percentageArray[finalI].getText());
          refreshContinueButton2Status();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
          // ignore
        }
      });
    }
  }

  private void refreshContinueButton2Status() {
    if (isWeightArrayValid() && isTickerArrayValid()) {
      buyWeightedStocksButton.setEnabled(isSumHundred());
    }
    else {
      buyWeightedStocksButton.setEnabled(false);
    }
  }

  private boolean isTickerArrayValid() {
    return Arrays.stream(isTickerArrayValid).allMatch(val -> val);
  }

  private boolean isWeightArrayValid() {
    return Arrays.stream(isWeightArrayValid).allMatch(val -> val);
  }

  private boolean isSumHundred() {
    ArrayList<Integer> weights = new ArrayList<>();
    for (int i = 0; i < Integer.parseInt(noDistinctStock.getText()); ++i) {
      weights.add(Integer.parseInt(percentageArray[i].getText()));
    }
    return features.isSumHundred(weights);
  }

  private void refreshContinueButton1Status() {
    continueButton1.setEnabled(isPortfolioNameValid && isTotalInvestmentValid
            && isCommissionFeeValid && isNoDistinctStockValid
            && isDateValid);
  }

}
