package stocks.controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle Cost Basis of Portfolio, It contains methods which take care of validation
 * and call appropriate model functions.
 */
public class CostBasisOfFlexiblePortfolio extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  public CostBasisOfFlexiblePortfolio(StockView view, Scanner sc) {
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
    String date = inputDateNormalCostBasis(model, view, sc);
    try {
      Double costBasis = model.getCostBasisOfPortfolio(portfolioName, date);
      view.write("\nCost Basis := " + costBasis.toString());
    } catch (Exception e) {
      view.write("Could not calculate the cost basis of the portfolio. Please try again. \n");
    }

  }

  private String inputDateNormalCostBasis(IModelMain model, StockView view, Scanner sc) {
    view.write("Enter the date on which you want to find the" +
            " cost-basis of portfolio in the format yyyy-MM-dd.\n");
    while (true) {
      try {
        String date = sc.next();
        LocalDate date1 = LocalDate.parse(date);
        return date1.toString();
      } catch (Exception e) {
        view.write("Invalid Input. Try Again");
      }
    }
  }

  private String inputOldPortfolioName(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    view.write("Enter the name of the flexible portfolio you want to check the cost-basis of: ");
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
