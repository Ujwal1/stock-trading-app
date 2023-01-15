package stocks.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stocks.controller.commands.BuyFlexibleStockFile;
import stocks.controller.commands.CreateFlexiblePortfolioFile;
import stocks.controller.commands.SellFlexibleStockFile;
import stocks.model.IModelMain;
import stocks.view.GUIView;

/**
 * FeaturesImpl which Implements all the functionality of feature interface, it also extends GUI
 * Portfolio mangerImpl to display the corresponding message in GUI.
 */

public class FeaturesImpl extends GUIPortfolioManagerImpl implements Features {
  /**
   * Constructor to take input from view and display an appropriate after talking to model.
   *
   * @param model Model on which all the interactions will take place.
   * @param view  view on which all the interactions will take place.
   */
  public FeaturesImpl(IModelMain model, GUIView view) {
    super(model, view);
  }

  @Override
  public boolean inputNewPortfolioName(String newPName) {

    try {
      if (newPName.strip().equals("")) {
        view.write("Empty portfolio name not accepted.\n");
        return false;
      }
      if (newPName.contains(" ")) {
        view.write("Spaces not allowed in name.\n");
        return false;
      }

      if (!model.checkPortfolioExists(newPName)) {

        view.write("Portfolio name accepted. " + newPName + "\n");
        return true;
      } else {
        view.write("This Portfolio name already exists. Try Another");
        return false;
      }
    } catch (Exception e) {
      view.write("Unable to validate new portfolio name.");
      return false;
    }

  }

  @Override
  public boolean inputValidStock(String ticker) {
    if (ticker.equals("")) {
      view.write("Field cannot be empty");
      return false;
    }
    if (model.stockExists(ticker)) {
      view.write("Ticker symbol valid.\n");
      return true;
    } else {
      view.write("The entered stock does not exist. Try again.");
      return false;
    }
  }

  @Override
  public boolean inputDateNormal(String date, String ticker) {
    try {
      LocalDate date1 = LocalDate.parse(date);
      if (model.stockOnDateExists(ticker, date)) {
        view.write("Entered date accepted. The stock trades on the given day.");
        return true;
      } else {
        view.write("The given stock did not trade on the entered date. Try Entering again");
        return false;
      }
    } catch (Exception e) {
      view.write("Invalid Input. Try Again");
      return false;
    }
  }

  @Override
  public boolean inputIntegerNormal(String quant) {
    try {
      int quantity = Integer.parseInt(quant);
      if (quantity > 0) {
        view.write("Entered value accepted. :)\n");
        return true;
      } else {
        view.write("Please enter a positive number. Try again\n");
        return false;
      }
    } catch (NumberFormatException e) {
      view.write("Please a whole no. Found fractional or non-numeric entry. Try again\n");
      return false;
    }
  }

  @Override
  public void createFlexiblePortfolio(String portfolioName, String ticker,
                                      String date, double quantity, double fee) {
    try {
      model.createFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.write("Creating portfolio Successful.");
    } catch (Exception e) {
      view.home("Action Failed.");
    }

  }

  @Override
  public void buyStockFlexible(String portfolioName, String ticker,
                               String date, double quantity, double fee) {
    try {
      model.createFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.home("Buy Successful.");
    } catch (Exception e) {
      view.home("Action Failed.");
    }

  }

  @Override
  public Boolean inputOldPortfolioName(String oldPName) {
    try {
      if (model.checkFlexiblePortfolioExists(oldPName)) {
        view.write("Existing portfolio name accepted");
        return true;
      } else {
        view.write("This Portfolio does not exist. Try entering another.");
        return false;
      }
    } catch (Exception e) {
      view.write("Invalid portfolio name. Try again");
      return false;
    }
  }

  @Override
  public Boolean inputDateForwardInTime(String date, String ticker, String portfolioName) {
    try {
      LocalDate date1 = LocalDate.parse(date);
      if (model.stockOnDateExists(ticker, date)) {

        if (model.checkTransactionDateValidFlexible(portfolioName, date)) {
          view.write("Entered date accepted. The stock trades on the given day and the " +
                  "asked transaction date is equal or larger than past latest transcation");
          return true;
        } else {
          view.write("Enter a date larger than last transaction date for the portfolio.");
          return false;
        }

      } else {
        view.write("The given stock did not trade on the entered date. Try Entering again");
        return false;
      }
    } catch (Exception e) {
      view.write("Invalid Input. Try Again");
      return false;
    }
  }

  @Override
  public Boolean inputSellQuantity(String portfolioName, String ticker, String date, String quant) {
    try {
      int quantity = Integer.parseInt(quant);
      if (quantity > 0) {
        try {
          //checking if desired sell count even exists on the desired selling date
          if (model.checkSellingQuantityandDateFlexible(portfolioName,
                  ticker, date, (double) quantity)) {
            view.write("Desired selling quantity of the stock accepted.");
            return true;
          } else {
            view.write("\nCannot sell the said quantity of " +
                    "the stock from this portfolio. Check if enough count of the stocks exists\n");
            return false;
          }
        } catch (Exception e) {
          view.write("\nCannot sell the said quantity of " +
                  "the stock from this portfolio. Check if enough count of the stocks exists\n");
          return false;
        }
      } else {
        view.write("Please enter a positive number. Try again\n");
        return false;
      }
    } catch (Exception e) {
      view.write("Please a whole no. Found fractional or non-numeric entry. Try again\n");
      return false;
    }

  }

  @Override
  public void sellStockFlexible(String portfolioName, String ticker, String date,
                                double quantity, double fee) {
    try {
      model.sellStockFromFlexiblePortfolio(portfolioName, ticker, date, quantity, fee);
      view.home("\nSelling stock successful.\n");
    } catch (Exception e) {
      view.home("\nStock could not be sold. Try again.\n");
    }
  }

  @Override
  public Boolean inputRightDateFormat(String date) {
    try {
      LocalDate date1 = LocalDate.parse(date);
      view.write("Date Accepted.");
      return true;
    } catch (Exception e) {
      view.write("Invalid date format. Try Again");
      return false;
    }
  }

  @Override
  public void getCostBasisOfPortfolio(String portfolioName, String date) {
    try {
      Double costBasis = model.getCostBasisOfPortfolio(portfolioName, date);
      view.write("\nCost Basis of " + portfolioName + " on "
              + date + " = $" + costBasis.toString());
    } catch (Exception e) {
      view.write("Could not calculate the cost basis of the portfolio. Please try again. \n");
    }
  }

  @Override
  public void getValueOfPortfolioDate(String portfolioName, String date) {
    try {
      Double value = model.valueOfPortfolio(portfolioName, date);
      view.write("\nValue of the portfolio := " + value.toString() + "\n");
    } catch (Exception e) {
      view.write("Could not calculate the Value of the portfolio. Please try again. \n");
    }
  }

  @Override
  public void home() {
    view.home("Home Screen");
  }

  @Override
  public Boolean inputNewStrategyName(String strategyName) {
    if (!model.checkStrategy(strategyName)) {
      view.write("Strategy name valid.");
      return true;
    } else {
      view.write("This Strategy name already exists. Try Another");
      return false;
    }
  }

  @Override
  public Boolean inputAmountNormal(String investment) {
    try {
      Double amount = Double.parseDouble(investment);
      if (amount > 0) {
        view.write("Entered investment accepted");
        return true;
      } else {
        view.write("Enter a positive Number\n");
        return false;
      }
    } catch (NumberFormatException e) {
      view.write("Given a non-numeric entry. Try again\n");
      return false;
    }
  }

  @Override
  public Boolean inputWeightNormal(String weight) {
    try {
      Double amount = Double.parseDouble(weight);
      if (amount > 0) {
        view.write("Entered weight accepted");
        return true;
      } else {
        view.write("Enter a positive Number\n");
        return false;
      }
    } catch (NumberFormatException e) {
      view.write("Given a non-numeric entry. Try again\n");
      return false;
    }
  }

  @Override
  public boolean isSumHundred(ArrayList<Integer> weights) {
    Integer sum = 0;
    for (Integer i : weights) {
      sum += i;
    }
    if (sum == 100) {
      view.write("Sum of all weights equals 100. :)");
      return true;
    } else {
      view.write("Sum of all weights is not 100 :(");
      return false;
    }

  }

  @Override
  public Boolean inputDateGreaterOrEqualStrategyStart(String date1, String date2) {
    try {
      LocalDate newDate = LocalDate.parse(date1);
      LocalDate oldDate = LocalDate.parse(date2);
      if (newDate.compareTo(oldDate) < 0) {
        view.write("Strategy Start Date cannot before Portfolio Creation Date");
        return false;
      } else {
        view.write("Strategy start date accepted");
        return true;
      }
    } catch (Exception e) {
      view.write("Invalid Input. Try Again");
      return false;
    }
  }

  @Override
  public Boolean inputDateGreaterOrEqualStrategyEnd(String date1, String date2) {
    if (date1.equals("") || date1.equalsIgnoreCase("c")) {
      view.write("Empty date accepted");
      return true;
    }

    try {
      LocalDate newDate = LocalDate.parse(date1);
      LocalDate oldDate = LocalDate.parse(date2);
      if (newDate.compareTo(oldDate) < 0) {
        view.write("Strategy end Date cannot before its start date");
        return false;
      } else {
        view.write("Strategy end date accepted");
        return true;
      }
    } catch (Exception e) {
      view.write("Invalid Input. Try Again");
      return false;
    }
  }

  @Override
  public void saveStrategy(List<List<String>> strategyDetails) {
    try {
      model.saveStrategy(strategyDetails);
      view.write("Strategy Saved Successfully");
    } catch (Exception e) {
      view.write("Could not save strategy");
    }
  }

  @Override
  public void saveStrategyInPortfolio(String portfolioName, List<String> strategies) {
    try {
      model.saveStrategyInPortfolio(portfolioName, strategies);
      view.write("Strategy Applied to the portfolio successfully");
    } catch (Exception e) {
      view.write("Could not apply strategy to the portfolio. ");
    }
  }

  @Override
  public void createFlexiblePortfolioFile(String path) {
    try {
      new CreateFlexiblePortfolioFile(view, new Scanner(path)).execute(model);
    } catch (Exception e) {
      view.write(e.getMessage());
    }
  }

  @Override
  public void buyFlexibleStockFile(String path) {
    try {
      new BuyFlexibleStockFile(view, new Scanner(path)).execute(model);
    } catch (Exception e) {
      view.write(e.getMessage());
    }
  }

  @Override
  public void sellFlexibleStockFile(String path) {
    try {
      new SellFlexibleStockFile(view, new Scanner(path)).execute(model);
    } catch (Exception e) {
      view.write(e.getMessage());
    }
  }

  @Override
  public String getFlexiblePortfolioList() {
    try {
      return model.getFlexiblePortfolioList();
    } catch (Exception e) {
      view.write(e.getMessage());
      return new String("");
    }
  }

  @Override
  public void investByWeight(String oldPortfolioName, List<List<String>> allInvestmentData) {
    try {
      model.loadRegularStrategy(oldPortfolioName, allInvestmentData);
      view.write("Requested purchases successful.");
    } catch (Exception e) {
      view.write("Unable to make the requested purchases.");
    }
  }

  @Override
  public Boolean inputEndDateForGraph(String endDate, String startDate) {
    try {
      long days = ChronoUnit.DAYS.between(LocalDate.parse(startDate),
              LocalDate.parse(endDate)) + 1;
      if (days < 5) {
        view.write("\nStarting and end date should be atleast 5 days apart. " +
                "Try entering end date again. \n");
        return false;
      } else {
        view.write("End date accepted.");
        return true;
      }
    } catch (Exception e) {
      view.write("Invalid date format. Try Again.");
      return false;
    }
  }

  @Override
  public String getPerformanceChart(String portfolioName, String startDate, String endDate) {
    try {
      return model.getPerformanceChart(portfolioName,startDate,endDate);
    }
    catch (Exception e) {
      view.write("Could not get performance chart.");
      return "";
    }

  }

  @Override
  public String getComposition(String portfolioName) {
    try {
      String composition = model.compositionOfPortfolio(portfolioName);
      view.write("\nComposition of requested portfolio generated successfully.");
      return composition;
    } catch (Exception e) {
      view.write("Could not calculate the composition of the portfolio. Please try again. \n");
      return "";
    }
  }

}
