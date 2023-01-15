package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Create Flexible Portfolio.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class CreateInFlexiblePortfolio extends AbstractValidateInputs implements Command {

  private final StockView view;
  private Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for create an InFlexible Portfolio.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public CreateInFlexiblePortfolio(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {
    String portfolioName = inputNewPortfolioName(model, view, sc);
    model.createNewInFlexiblePortfolio(portfolioName);

    while (true) {
      String ticker = inputStockNameNormal(model, view, sc);
      double quantity = inputQuantityNormal(view, sc);
      try {
        model.addStockToInFlexiblePortfolio(ticker, quantity);
      } catch (Exception e) {
        view.write("Portfolio could not be created. Try again");
      }
      view.write("Enter c/continue to add more stocks to this inflexible portfolio." +
              "\n Or press anything else to finalise this portfolio.");
      String entry = sc.next();
      if (!entry.equalsIgnoreCase("c")
              && !entry.equalsIgnoreCase("continue")) {
        model.finaliseInflexiblePortfolio();
        view.write("\nInflexible portfolio created successfully.\n");
        return;
      }
    }

  }

  protected String inputNewPortfolioName(IModelMain model, StockView view, Scanner sc) {
    view.write("Enter the name of the new portfolio to be created");
    while (true) {
      try {
        String portfolioName = sc.next();
        if (!model.checkFlexiblePortfolioExists(portfolioName)) {
          return portfolioName;
        } else {
          view.write("This Portfolio name already exists. Try Another");
        }
      } catch (Exception e) {
        view.write("Invalid portfolio name. Try again");
      }
    }
  }
}
