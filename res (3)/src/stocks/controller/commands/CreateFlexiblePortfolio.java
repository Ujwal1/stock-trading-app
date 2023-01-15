package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Creating a Flexible Portfolio, It contains methods which take care of validation
 * and call appropriate model functions.
 */
public class CreateFlexiblePortfolio extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for create Flexible Portfolio.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public CreateFlexiblePortfolio(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  /**
   * Take input and show view message.
   *
   * @param model represents the model where the command will take place.
   */
  @Override
  public void execute(IModelMain model) {
    String portfolioName = inputNewPortfolioName(model, view, sc);
    String ticker = inputStockNameNormal(model, view, sc);
    String date = inputStockDateNormal(model, view, sc, ticker);
    double quantity = inputQuantityNormal(view, sc);
    double fee = inputCommissionFee(view, sc);
    try {
      model.createFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
    } catch (Exception e) {
      view.write("Portfolio could not be created. Try again");
    }
    view.write("Type YES to create a Strategy, press any other key for No\n");
    String s = sc.next();
    if (s.equalsIgnoreCase("YES")) {
      createStrategyInModel(model, view, sc, portfolioName, date);
    }
    view.write("Portfolio created successfully.\n");
  }

  protected String inputNewPortfolioName(IModelMain model, StockView view, Scanner sc) {
    view.write("Enter the name of the new portfolio to be created");
    while (true) {
      try {
        String portfolioName = sc.next();
        if (!model.checkPortfolioExists(portfolioName)) {
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
