package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Composition of Portfolio, It contains methods which take care of validation
 * and call appropriate model functions.
 */
public class CompositionOfPortfolio extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input for Composition of Portfolio.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public CompositionOfPortfolio(StockView view, Scanner sc) {
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
    String portfolioName = inputOldAnyPortfolioName(model, view, sc);
    try {
      String composition = model.compositionOfPortfolio(portfolioName);
      view.write("\nComposition of '" + portfolioName + "' :\n" + composition);
    } catch (Exception e) {
      view.write("Could not calculate the composition of the portfolio. Please try again. \n");
    }
  }

  private String inputOldAnyPortfolioName(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    view.write("Enter the name of the portfolio you want to find the composition of: ");
    while (true) {
      try {
        view.write("Here's list of available portfolios:");
        view.write(model.getAllPortfolioList());
      } catch (Exception e) {
        throw new Exception("Unable to fetch list of available portfolios." +
                " Check data-store availability");
      }
      try {
        String portfolioName = sc.next();
        if (model.checkPortfolioExists(portfolioName)) {
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
