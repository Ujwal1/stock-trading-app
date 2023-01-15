package stocks.controller.commands;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Buying of Create Flexible Portfolio using a txt file.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class CreateFlexiblePortfolioFile extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for create Flexible Portfolio by file.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public CreateFlexiblePortfolioFile(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {
    File f;
    String next = sc.next();
    try {
      // if path is coming from GUI view
      if (!next.equals("create_flexible_file")) {
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

    String portfolioName = inputNewPortfolioNameFile(model, view, sc);

    String ticker = inputStockNameFile(model, view, sc);
    String date = inputStockDateFile(model, view, sc, ticker);
    double quantity = inputQuantityFile(view, sc);
    double fee = inputCommissionFeeFile(view, sc);
    try {
      model.createFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.write("Portfolio created successfully.\n");
    } catch (Exception e) {
      view.write("Portfolio could not be created. Try again");
    }

  }

  private double inputQuantityFile(StockView view, Scanner sc) throws Exception {
    double quantity;
    if (!sc.hasNextLine()) {
      throw new Exception("No stock buying quantity specified");
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
        throw new Exception("Entered date not right." +
                " Portfolio creation aborted");
      }
    } catch (Exception e) {
      throw new Exception("Entered date not right." +
              " Portfolio creation aborted");
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

  private String inputNewPortfolioNameFile(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    if (!sc.hasNextLine()) {
      throw new Exception("No portfolio name specified.\n");
    }
    try {
      String portfolioName = sc.next();
      if (!model.checkPortfolioExists(portfolioName)) {
        return portfolioName;
      } else {
        throw new Exception("The given portfolio name already exists.");
      }
    } catch (Exception e) {
      throw new Exception("The given portfolio name already exists. " +
              "Could not create the portfolio." +
              "Try again.\n");
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
