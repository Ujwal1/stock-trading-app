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
 * A GUI screen to display the necessary inputs which the user has to be done for Creating
 * Dollar Cost Strategy on a portfolio and its corresponding validation message.
 */
public class DollarCostStrategyScreen extends JPanel {

  private String portfolioName;
  JPanel oneStrategy = new JPanel();
  JPanel stockRatioInputScreen = new JPanel();
  JPanel moreOrQuitScreen = new JPanel();
  JTextField strategyName;

  JTextField totalInvestment;
  JTextField commissionFee;
  JTextField strategyStartDate;
  JTextField strategyEndDate;
  JTextField interval;
  List<List<String>> strategyDetails = new ArrayList<>();
  List<String> detailsInitial = new ArrayList<>();
  List<String> detailsFinal = new ArrayList<>();
  JTextField noDistinctStock;
  private Boolean isStrategyNameValid = false;
  private Boolean isTotalInvestmentValid = false;
  private Boolean isCommissionFeeValid = false;
  private Boolean isNoDistinctStockValid = false;
  private JButton continueButton1 = new JButton("Continue");
  JTextField[] tickerArray;
  JTextField[] percentageArray;
  Features features;
  private Boolean[] isTickerArrayValid;
  private Boolean[] isWeightArrayValid;
  private JButton createStrategyButton = new JButton("Create");
  String portfolioCreationDate;
  private Boolean isStartDateValid = false;
  private Boolean isEndDateValid = false;
  private JButton addMoreStrategyButton = new JButton("Add more strategies");
  private JButton quitAddingStrategyButton = new JButton("Quit Adding Strategies");
  private Boolean isIntervalValid = false;

  /**
   * Constructor to call necessary objects and pass feature class object along with portfolio name
   * and dateof creation.
   *
   * @param features       Object of Feature class to provide access to the controller.
   * @param portfolioName  Name of the portfolio on which dollar cost strategy has to applied.
   * @param dateOfCreation Date on which we need create dollar cost strategy.
   */
  public DollarCostStrategyScreen(Features features, String portfolioName, String dateOfCreation) {
    this.portfolioName = portfolioName;
    this.portfolioCreationDate = dateOfCreation;
    this.features = features;
    initSingleStrategy(features);
    this.add(oneStrategy);
  }

  private void initSingleStrategy(Features features) {
    oneStrategy.add(new JLabel("Basic info form for creating a strategy:"));
    oneStrategy.setLayout(new BoxLayout(oneStrategy, BoxLayout.PAGE_AXIS));

    strategyName = new JTextField(10);
    oneStrategy.add(new JLabel("Enter strategy name"));
    oneStrategy.add(strategyName);

    totalInvestment = new JTextField(10);
    oneStrategy.add(new JLabel("Enter total investment to be done each time."));
    oneStrategy.add(totalInvestment);

    commissionFee = new JTextField(10);
    oneStrategy.add(new JLabel("Enter commission fee in USD for each " +
            "transaction done using this strategy."));
    oneStrategy.add(commissionFee);

    noDistinctStock = new JTextField(10);
    oneStrategy.add(new JLabel("Enter the no of different stocks " +
            "you wish to have in this strategy."));
    oneStrategy.add(noDistinctStock);

    strategyStartDate = new JTextField(10);
    oneStrategy.add(new JLabel("Enter strategy start date."));
    oneStrategy.add(strategyStartDate);

    strategyEndDate = new JTextField(10);
    oneStrategy.add(new JLabel("Enter strategy end date. Leave empty if it is ongoing"));
    oneStrategy.add(strategyEndDate);

    interval = new JTextField(10);
    oneStrategy.add(new JLabel("Enter the interval(in days) of how often you want " +
            "to run this strategy"));
    oneStrategy.add(interval);

    continueButton1.setEnabled(false);
    oneStrategy.add(continueButton1);

    createActionListeners(features);

  }

  private void createActionListeners(Features features) {
    strategyName.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isStrategyNameValid = features.inputNewStrategyName(strategyName.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isStrategyNameValid = features.inputNewStrategyName(strategyName.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      } // Not needed for plain-text fields

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

    strategyStartDate.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isStartDateValid = features.inputDateGreaterOrEqualStrategyStart(strategyStartDate.
                getText(), portfolioCreationDate);
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isStartDateValid = features.inputDateGreaterOrEqualStrategyStart(strategyStartDate.
                getText(), portfolioCreationDate);
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    strategyEndDate.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isEndDateValid = features.inputDateGreaterOrEqualStrategyEnd(strategyEndDate.
                getText(), strategyStartDate.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isEndDateValid = features.inputDateGreaterOrEqualStrategyEnd(strategyEndDate.
                getText(), strategyStartDate.getText());
        refreshContinueButton1Status();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    interval.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isIntervalValid = features.inputIntegerNormal(interval.getText());
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isIntervalValid = features.inputIntegerNormal(interval.getText());
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });
    continueButton1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        detailsInitial.add(strategyName.getText());
        detailsInitial.add(totalInvestment.getText());
        detailsInitial.add(commissionFee.getText());

        strategyDetails.add(detailsInitial);

        detailsFinal.add(strategyStartDate.getText());

        if (strategyEndDate.getText().equals("")) {
          detailsFinal.add("c");
        } else {
          detailsFinal.add(strategyEndDate.getText());
        }

        detailsFinal.add(interval.getText());

        oneStrategy.removeAll();
        initStockRatioInput(Integer.parseInt(noDistinctStock.getText()));
        oneStrategy.updateUI();
      }
    });

    createStrategyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < Integer.parseInt(noDistinctStock.getText()); i++) {
          ArrayList<String> temp = new ArrayList<>();
          temp.add(tickerArray[i].getText());
          temp.add(percentageArray[i].getText());
          strategyDetails.add(temp);
        }
        strategyDetails.add(detailsFinal);
        features.saveStrategy(strategyDetails);
        ArrayList<String> strategies = new ArrayList<>();
        strategies.add(strategyName.getText());
        features.saveStrategyInPortfolio(portfolioName, strategies);

        oneStrategy.removeAll();

        initMoreOrQuit();
        oneStrategy.add(moreOrQuitScreen);
        oneStrategy.updateUI();
      }
    });

  }

  private void initMoreOrQuit() {
    moreOrQuitScreen.add(new JLabel("Add weights corresponding to stocks:"));
    moreOrQuitScreen.setLayout(new BoxLayout(moreOrQuitScreen, BoxLayout.Y_AXIS));
    moreOrQuitScreen.setSize(3000, 3000);

    moreOrQuitScreen.add(addMoreStrategyButton);
    moreOrQuitScreen.add(quitAddingStrategyButton);

    addMoreStrategyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        oneStrategy.removeAll();
        oneStrategy.add(new JLabel(""));
        oneStrategy.add(new DollarCostStrategyScreen(features, portfolioName,
                portfolioCreationDate));
        oneStrategy.updateUI();
      }
    });

    quitAddingStrategyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.home();
      }
    });

  }

  private void refreshContinueButton1Status() {
    continueButton1.setEnabled(isStrategyNameValid && isTotalInvestmentValid
            && isCommissionFeeValid && isNoDistinctStockValid
            && isStartDateValid && isEndDateValid && isIntervalValid);
  }

  private void initStockRatioInput(int noOfDistinctStocks) {

    stockRatioInputScreen = new JPanel();
    stockRatioInputScreen.setLayout(new BoxLayout(stockRatioInputScreen, BoxLayout.PAGE_AXIS));

    JScrollPane mainScrollPane = new JScrollPane(stockRatioInputScreen);
    mainScrollPane.setPreferredSize(new Dimension(200, 200));

    stockRatioInputScreen.add(new JLabel("Add weights corresponding to stocks:"));

    tickerArray = new JTextField[noOfDistinctStocks];
    percentageArray = new JTextField[noOfDistinctStocks];
    isTickerArrayValid = new Boolean[noOfDistinctStocks];
    isWeightArrayValid = new Boolean[noOfDistinctStocks];
    Arrays.fill(isTickerArrayValid, Boolean.FALSE);
    Arrays.fill(isWeightArrayValid, Boolean.FALSE);

    for (int i = 0; i < noOfDistinctStocks; ++i) {
      tickerArray[i] = new JTextField("");
      percentageArray[i] = new JTextField("");

      stockRatioInputScreen.add(new JLabel("Enter stock name " + i + 1));
      stockRatioInputScreen.add(tickerArray[i]);

      stockRatioInputScreen.add(new JLabel("Enter integral weight for stock " + i + 1));
      stockRatioInputScreen.add(percentageArray[i]);
    }

    createStrategyButton.setEnabled(false);
    stockRatioInputScreen.add(createStrategyButton);

    createActionListenersArrays();

    oneStrategy.add(mainScrollPane);

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
          //ignore
        }

      });

      percentageArray[finalI].getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
          isWeightArrayValid[finalI] = features.inputWeightNormal(percentageArray[finalI].
                  getText());
          refreshContinueButton2Status();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
          isWeightArrayValid[finalI] = features.inputWeightNormal(percentageArray[finalI].
                  getText());
          refreshContinueButton2Status();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
          //ignore
        }
      });
    }
  }

  private void refreshContinueButton2Status() {
    if (isWeightArrayValid() && isTickerArrayValid()) {
      createStrategyButton.setEnabled(isSumHundred());
    } else {
      createStrategyButton.setEnabled(false);
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


}
