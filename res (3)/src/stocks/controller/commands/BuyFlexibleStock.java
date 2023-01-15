package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Buying of Flexible Stocks, It contains methods which take care of validation
 * and call appropriate model functions.
 */
public class BuyFlexibleStock extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for buying stocks.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public BuyFlexibleStock(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  /**
   * Take input and show view message.
   *
   * @param model represents the model where the command will take place.
   * @throws Exception Throw appropriate errors
   */
  @Override
  public void execute(IModelMain model) throws Exception {
    String portfolioName = inputOldPortfolioName(model, view, sc);
    String ticker = inputStockNameNormal(model, view, sc);
    String date = inputStockDateForwardInTime(model, view, sc, ticker, portfolioName);
    double quantity = inputQuantityNormal(view, sc);
    double fee = inputCommissionFee(view, sc);
    try {
      model.createFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.write("\nBuy Stock Successful.\n");
    } catch (Exception e) {
      view.write("\nStock could not be bought. Try again.\n");
    }
  }

  private String inputStockDateForwardInTime(IModelMain model, StockView view, Scanner sc,
                                             String ticker, String portfolioName) throws Exception {
    while (true) {
      String date = inputStockDateNormal(model, view, sc, ticker);
      if (model.checkTransactionDateValidFlexible(portfolioName, date)) {
        return date;
      } else {
        view.write("Enter the transaction date larger than the " +
                "latest transaction for this portfolio\n");
      }
    }
  }

  private String inputOldPortfolioName(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    view.write("Enter the name of the portfolio for which you want to buy a stock.");
    while (true) {
      try {
        view.write("Here's list of available flexible portfolios:");
        view.write(model.getFlexiblePortfolioList());
      } catch (Exception e) {
        throw new Exception("Unable to fetch list of available portfolios." +
                " Check data-store availability");
      }
      try {
        String portfolioName = sc.next();
        if (model.checkFlexiblePortfolioExists(portfolioName)) {
          return portfolioName;
        } else {
          view.write("This Portfolio does not exist. Try Another from the list.");
        }
      } catch (Exception e) {
        view.write("Invalid portfolio name. Try again");
      }
    }
  }

}
