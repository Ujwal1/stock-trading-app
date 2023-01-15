package stocks.controller.commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * Class to handle plotting a line chart showing the performance of the portfolio
 * over a period of time.
 * It contains methods which take care of validation and call appropriate model functions.
 */
public class PlotPerformanceChart extends AbstractValidateInputs implements Command {

  private final StockView view;
  private final Scanner sc;

  /**
   * Constructor to take view object and scanner obj to show appropriate
   * message to display and take input to create appropriate chart.
   *
   * @param view obj to show appropriate message
   * @param sc   obj to take appropriate input.
   */
  public PlotPerformanceChart(StockView view, Scanner sc) {
    this.view = view;
    this.sc = sc;
  }

  @Override
  public void execute(IModelMain model) throws Exception {
    String portfolioName = inputOldPortfolioName(model, view, sc);
    String startDate = inputDateNormal(view, sc);
    String endDate = inputEndDate(view, sc, startDate);
    try {
      String formattedChart = model.getPerformanceChart(portfolioName, startDate, endDate);
      view.write("\n" + formattedChart + "\n");
    } catch (Exception e) {
      throw new Exception("\nError displaying the performance chart :'( \n");
    }
  }

  private String inputEndDate(StockView view, Scanner sc, String startDate) {
    view.write("Enter the ending date till when you wish to view the performance" +
            " in the format yyyy-MM-dd: \n");
    String endDate;
    while (true) {
      try {
        endDate = sc.next();
        long days = ChronoUnit.DAYS.between(LocalDate.parse(startDate),
                LocalDate.parse(endDate)) + 1;
        if (days < 5) {
          view.write("\nStarting and end date should be atleast 5 days apart. " +
                  "Try entering end date again. \n");
        } else {
          return endDate;
        }
      } catch (Exception e) {
        view.write("Invalid date format. Try Again.");
      }
    }
  }

  private String inputDateNormal(StockView view, Scanner sc) {
    view.write("Enter the starting date from when you wish to view the performance" +
            " in the format yyyy-MM-dd: \n");
    while (true) {
      try {
        String date = sc.next();
        return date;
      } catch (Exception e) {
        view.write("Invalid Input. Try Again");
      }
    }
  }

  private String inputOldPortfolioName(IModelMain model, StockView view, Scanner sc)
          throws Exception {
    view.write("Enter the name of the portfolio you want to plot the performance chart of: ");
    while (true) {
      try {
        view.write("Here's list of available flexible portfolios:");
        view.write(model.getAllPortfolioList());
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
