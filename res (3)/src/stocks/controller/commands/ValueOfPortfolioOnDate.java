package stocks.controller.commands;

import java.time.LocalDate;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle the inputs and get the value of the portfolio on a certain date.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class ValueOfPortfolioOnDate extends AbstractValidateInputs implements Command {

  private final StockView view;
  Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input to view the value of a portfolio on a certain date.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public ValueOfPortfolioOnDate(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {
    String portfolioName = inputOldAnyPortfolioName(model, view, sc);
    String date = inputDateNormalValue(model, view, sc);
    try {
      Double value = model.valueOfPortfolio(portfolioName, date);
      view.write("\nValue of the portfolio := " + value.toString());
    } catch (Exception e) {
      view.write("Could not calculate the Value of the portfolio. Please try again. \n");
    }
  }

  private String inputDateNormalValue(IModelMain model, StockView view, Scanner sc) {
    view.write("Enter the date on which you want to find the" +
            " value of portfolio in the format yyyy-MM-dd.\n");
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

  private String inputOldAnyPortfolioName(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    view.write("Enter the name of the portfolio you want to find the value of: ");
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
