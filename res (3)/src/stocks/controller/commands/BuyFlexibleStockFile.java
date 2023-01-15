package stocks.controller.commands;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Buying of Flexible Stocks from file.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class BuyFlexibleStockFile extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for buying stocks.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public BuyFlexibleStockFile(StockView view, Scanner sc) {
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
    File f;
    String next = sc.next();
    try {
      // if path is coming from GUI view
      if (!next.equals("add_flexible_stock_file")) {
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
    String date = inputStockDateFile(model, view, sc, ticker);
    double quantity = inputQuantityFile(view, sc);
    Double fee = inputCommissionFeeFile(view, sc);

    try {
      model.createFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.write("\nBuying Stocks Successful!\n");
    } catch (Exception e) {
      view.write("\nStock could not be bought. Try again.\n");
    }
  }

  private double inputQuantityFile(StockView view, Scanner sc) throws Exception {
    double quantity;
    if (!sc.hasNextLine()) {
      throw new Exception("No stock buying quantity specified.\n");
    }
    try {
      String input = sc.next();
      quantity = Integer.parseInt(input);
      if (quantity > 0) {
        return quantity;
      } else {
        throw new Exception("Entered stock quantity was not appropriate." +
                "Creation of portfolio aborted.\n");
      }
    } catch (Exception e) {
      throw new Exception("Entered stock quantity was not appropriate." +
              "Creation of portfolio aborted.\n");
    }

  }

  private String inputStockDateFile(IModelMain model, StockView view, Scanner sc, String ticker)
          throws Exception {
    if (!sc.hasNextLine()) {
      throw new Exception("No stock buying date specified.\n");
    }
    try {
      String date = sc.next();
      LocalDate date1 = LocalDate.parse(date);
      if (model.stockOnDateExists(ticker, date)) {
        return date;
      } else {
        throw new Exception("\nEntered date not right." +
                " Portfolio creation aborted. \n");
      }
    } catch (Exception e) {
      throw new Exception("\nEntered date not right." +
              " Portfolio creation aborted\n");
    }
  }

  private String inputStockNameFile(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    if (!sc.hasNextLine()) {
      throw new Exception("No stock name specified.\n");
    }
    try {
      String ticker = sc.next();
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
}
