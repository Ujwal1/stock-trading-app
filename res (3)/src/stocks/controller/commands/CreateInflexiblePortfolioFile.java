package stocks.controller.commands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Create Flexible Portfolio using a file.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class CreateInflexiblePortfolioFile extends AbstractValidateInputs implements Command {

  StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for create an InFlexible Portfolio using file.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public CreateInflexiblePortfolioFile(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {
    File f;
    try {
      f = new File("src/input.txt");
    } catch (Exception e) {
      throw new Exception("File not found");
    }

    sc = new Scanner(f);
    if (!sc.hasNextLine()) {
      throw new IOException("File Empty");
    }

    String portfolioName = inputNewPortfolioNameFile(model, view, sc);
    if (!sc.hasNextLine()) {
      throw new Exception("No stock mentioned to be bought. Cannot create empty portfolio. " +
              "Creation Aborted.\n");
    }
    Map<String, Double> stockCountMap = new HashMap<>();
    while (sc.hasNext()) {
      String ticker = inputStockNameFile(model, view, sc);
      double quantity = inputQuantityFile(view, sc);
      if (stockCountMap.containsKey(ticker)) {
        stockCountMap.put(ticker, stockCountMap.get(ticker) + quantity);
      } else {
        stockCountMap.put(ticker, quantity);
      }
    }

    model.createNewInFlexiblePortfolio(portfolioName);
    for (Map.Entry<String, Double> entry : stockCountMap.entrySet()) {
      try {
        model.addStockToInFlexiblePortfolio(entry.getKey(), entry.getValue());
      } catch (Exception e) {
        view.write("Portfolio could not be created. Try again");
      }
    }

    try {
      model.finaliseInflexiblePortfolio();
      view.write("\nInflexible Portfolio Created!\n");
    } catch (Exception e) {
      throw new Exception("\nPortfolio could not be created.\n");
    }

  }

  private double inputQuantityFile(StockView view, Scanner sc) throws Exception {
    double quantity;
    if (!sc.hasNext()) {
      throw new Exception("\nNo stock buying quantity specified.\n");
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

  private String inputStockNameFile(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    if (!sc.hasNext()) {
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
    if (!sc.hasNext()) {
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
}
