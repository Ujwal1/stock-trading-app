package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle the inputs to sell stocks from a flexible portfolio.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class SellFlexibleStock extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input to sell stocks from the portfolio.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public SellFlexibleStock(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {
    String portfolioName = inputOldPortfolioName(model, view, sc);
    String ticker = inputStockNameNormal(model, view, sc);
    String date = inputStockDateForwardInTime(model, view, sc, ticker, portfolioName);
    double quantity = inputSellQuantity(model, view, sc, portfolioName, ticker, date);
    double fee = inputCommissionFee(view, sc);
    try {
      model.sellStockFromFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
    } catch (Exception e) {
      view.write("\nStock could not be sold. Try again.\n");
    }
  }

  private double inputSellQuantity(IModelMain model, StockView view, Scanner sc,
                                   String portfolioName, String ticker, String date)
          throws Exception {
    while (true) {
      double quantity = inputQuantityNormal(view, sc);
      try {
        if (model.checkSellingQuantityandDateFlexible(portfolioName, ticker, date, quantity)) {
          return quantity;
        } else {
          throw new Exception("\nCannot sell the said quantity of " +
                  "the stock from this portfolio.\n");
        }
      } catch (Exception e) {
        throw new Exception("\nCannot sell the said quantity of the stock from this portfolio1.\n");
      }
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
    view.write("Enter the name of the portfolio for which you want to sell a stock.");
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
