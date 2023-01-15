package stocks.controller.commands;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle the inputs to sell stocks from a flexible portfolio using a file.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class SellFlexibleStockFile extends AbstractValidateInputs implements Command {

  private final StockView view;
  private Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input to sell stocks from the portfolio using a file.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public SellFlexibleStockFile(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {

    File f;
    String next = sc.next();
    try {
      // if path is coming from GUI view
      if (!next.equals("sell_flexible_stock_file")) {
        System.out.println("HERE GUI");
        f = new File(next);
      } else {
        f = new File("src/input.txt");
        System.out.println("HERE CLI");
      }

    } catch (Exception e) {
      throw new Exception("File not found");
    }

    sc = new Scanner(f);
    if (!sc.hasNextLine()) {
      throw new IOException("File Empty");
    }

    String portfolioName = inputOldPortfolioNameFile(model, view, sc);
    String ticker = inputStockNameFile(model, view, sc);
    String date = inputStockDateForwardInTimeFile(model, view, sc, ticker, portfolioName);
    double quantity = inputSellQuantityFile(model, view, sc, portfolioName, ticker, date);
    double fee = inputCommissionFeeFile(view, sc);
    try {
      model.sellStockFromFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.write("\nSell successful!\n");
    } catch (Exception e) {
      view.write("\nStock could not be sold. Try again.\n");
    }
  }

  private double inputSellQuantityFile(IModelMain model, StockView view, Scanner sc,
                                       String portfolioName, String ticker, String date)
          throws Exception {
    double quantity = inputQuantityNormal(view, sc);
    try {
      if (model.checkSellingQuantityandDateFlexible(portfolioName, ticker, date, quantity)) {
        return quantity;
      } else {
        throw new Exception("\nCannot sell the said quantity of the stock from this portfolio." +
                "Sell Aborted.\n");
      }
    } catch (Exception e) {
      throw new Exception("\nCannot sell the said quantity of the stock from this portfolio." +
              "Sell Aborted.\n");
    }
  }

  protected double inputCommissionFeeFile(StockView view, Scanner sc) throws Exception {
    double quantity;

    String input = sc.next();
    try {
      quantity = Integer.parseInt(input);
      if (quantity > 0) {
        return quantity;
      } else {
        throw new Exception("\nPlease enter a positive number. Try again\n");
      }
    } catch (NumberFormatException e) {
      throw new Exception("\n Please Enter a Positive Whole Number\n");
    }

  }

  private String inputStockDateForwardInTimeFile(IModelMain model, StockView view, Scanner sc,
                                                 String ticker, String portfolioName)
          throws Exception {
    if (!sc.hasNextLine()) {
      throw new Exception("No date specified.\n");
    }
    String date;
    try {
      date = sc.next();
      LocalDate date1 = LocalDate.parse(date);
    } catch (Exception e) {
      throw new Exception("Invalid date format found.\n");
    }

    if (!model.stockOnDateExists(ticker, date)) {
      throw new Exception("The given stock did not trade on the entered date. " +
              "Sell Aborted.\n");
    }

    if (model.checkTransactionDateValidFlexible(portfolioName, date)) {
      return date;
    } else {
      throw new Exception("Enter the transaction date larger than the " +
              "latest transaction for this portfolio.\n");
    }
  }

  private String inputStockNameFile(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    if (!sc.hasNextLine()) {
      throw new Exception("No stock name specified.\n");
    }
    try {
      String ticker = sc.next();
      //      System.out.println("Ticker:" + ticker);
      if (model.stockExists(ticker)) {
        return ticker;
      } else {
        throw new Exception("Stock name not valid. " +
                "Portfolio Creation aborted," +
                "See documentation\n");
      }
    } catch (Exception e) {
      throw new Exception("Stock name not valid. " +
              "Portfolio Creation aborted," +
              "See documentation\n");
    }
  }

  private String inputOldPortfolioNameFile(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    if (!sc.hasNextLine()) {
      throw new Exception("No old portfolio name specified.\n");
    }
    try {
      String portfolioName = sc.next();
      if (model.checkFlexiblePortfolioExists(portfolioName)) {
        return portfolioName;
      } else {
        throw new Exception("The portfolio name in the file does" +
                " not belong to any flexible portfolio." +
                "Buying stocks aborted.\n");
      }
    } catch (Exception e) {
      throw new Exception("The portfolio name in the file does" +
              " not belong to any flexible portfolio." +
              "Buying stocks aborted.\n");
    }
  }


}
