package stocks.controller.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * This class contains all the abstract and common methods for all implementation of the
 * Command Interface.
 */
public abstract class AbstractValidateInputs implements Command {

  protected String inputStockNameNormal(IModelMain model, StockView view, Scanner sc) {
    view.write("Enter the stock name.");
    while (true) {
      try {
        String ticker = sc.next();
        if (model.stockExists(ticker)) {
          return ticker;
        } else {
          view.write("The entered stock does not exist. Try again.");
        }
      } catch (Exception e) {
        view.write("Invalid input. Try Again");
      }
    }
  }

  protected String inputStockDateNormal(IModelMain model,
                                        StockView view, Scanner sc, String ticker) {
    view.write("Enter the date on which you want to make " +
            "the transaction the stock in the format yyyy-MM-dd.");
    while (true) {
      try {
        String date = sc.next();
        LocalDate date1 = LocalDate.parse(date);
        if (model.stockOnDateExists(ticker, date)) {
          return date;
        } else {
          view.write("The given stock did not trade on the entered date. Try Entering again");
        }
      } catch (Exception e) {
        view.write("Invalid Input. Try Again");
      }
    }
  }

  protected double inputQuantityNormal(StockView view, Scanner sc) {
    double quantity;
    view.write("Enter the no of stock you want to make the transaction of. " +
            "Enter a positive whole no. ");
    while (true) {
      String input = sc.next();
      try {
        quantity = Integer.parseInt(input);
        if (quantity > 0) {
          return quantity;
        } else {
          view.write("Please enter a positive number. Try again\n");
        }
      } catch (NumberFormatException e) {
        view.write("Please a whole no. Found fractional or non-numeric entry. Try again\n");
      }
    }
  }

  protected double inputCommissionFee(StockView view, Scanner sc) {
    double quantity;
    view.write("Enter a non-Fractional Positive Commission Fee ");
    while (true) {
      String input = sc.next();
      try {
        quantity = Integer.parseInt(input);
        if (quantity > 0) {
          return quantity;
        } else {
          view.write("Please enter a positive number. Try again\n");
        }
      } catch (NumberFormatException e) {
        view.write("Please a whole no. Found fractional or non-numeric entry. Try again\n");
      }
    }
  }

  protected double inputValidPercentage(StockView view, Scanner sc) {
    double percentage;
    while (true) {
      String input = sc.next();
      try {
        percentage = Double.parseDouble(input);
        if ((percentage > 100) || (percentage < 0)) {
          view.write("Please enter a valid percentage\n");
        } else {
          return percentage;
        }
      } catch (NumberFormatException e) {
        view.write("Given a non-numeric entry. Try again\n");
      }
    }
  }

  protected void createStrategyInModel(IModelMain model, StockView view, Scanner sc, String port,
                                       String date) {
    List<String> strategies = new ArrayList<>();
    while (true) {
      String strategyName = createStrategy(model, view, sc, date);
      strategies.add(strategyName);
      view.write("Type Continue to Enter more strategies, Type any other thing to Create" +
              "Portfolio\n");
      String input = sc.next();
      if (!input.equalsIgnoreCase("Continue")) {
        break;
      }
    }
    model.saveStrategyInPortfolio(port, strategies);

  }

  protected String createStrategy(IModelMain model, StockView view, Scanner sc, String date) {
    List<List<String>> strategyDetails = new ArrayList<>();
    String strategyName = null;
    List<String> temp = new ArrayList<>();
    try {
      strategyName = inputStrategyName(model, view, sc);
      double amount = inputAmount(view, sc);
      Double commissionFee = inputCommissionFee(view, sc);
      temp.add(strategyName);
      temp.add(String.valueOf(amount));
      temp.add(String.valueOf(commissionFee));
      strategyDetails.add(temp);
      view.write("Please Enter Stock Name followed by % you wanna invest,");
      view.write("Remember the total % should exactly be 100.\n");
      double total = 0.0;
      while ((100 - total) > 0) {
        temp = new ArrayList<>();
        String stkName = inputStockNameNormal(model, view, sc);
        Double percentage = inputPercentage(view, sc, total);
        if (total + percentage <= 100) {
          total += percentage;
          temp.add(stkName);
          temp.add(String.valueOf(percentage));
          strategyDetails.add(temp);
        } else {
          view.write("Please Enter Percentage no more than " + (100 - total) + "\n");
        }
      }
      temp = new ArrayList<>();
      String startDate = validaStartDate(view, sc, date);
      String endDate = validateEndDate(view, sc, startDate);
      Integer frequency = validateInterval(view, sc);
      temp.add(startDate);
      temp.add(endDate);
      temp.add(String.valueOf(frequency));
      strategyDetails.add(temp);
    } catch (IllegalArgumentException e) {
      view.write(String.valueOf(e));
    }
    try {
      model.saveStrategy(strategyDetails);
    } catch (Exception e) {
      view.write("Could not save strategy");
    }
    return strategyName;
  }

  protected double inputAmount(StockView view, Scanner sc) {
    double amount;
    view.write("Enter the amount to invest\n");
    while (true) {
      String input = sc.next();
      try {
        amount = Double.parseDouble(input);
        if (amount > 0) {
          return amount;
        } else {
          view.write("Enter a positive Number\n");
        }
      } catch (NumberFormatException e) {
        view.write("GIven a nin-numeric entry. Try again\n");
      }
    }
  }

  protected String validaStartDate(StockView view, Scanner sc, String creationDate) {
    String date;
    view.write("Enter the start date of the Strategy\n");
    while (true) {
      try {
        date = sc.next();
        LocalDate startDate = LocalDate.parse(creationDate);
        LocalDate date1 = LocalDate.parse(date);
        if (date1.compareTo(startDate) < 0) {
          view.write("Strategy Start Date cannot before Portfolio Creation Date");
        } else {
          return date;
        }
      } catch (Exception e) {
        view.write("Invalid Input. Try Again");
      }
    }
  }

  protected String validateEndDate(StockView view, Scanner sc, String startDate) {
    String date;
    view.write("Enter the End date of the Strategy");
    view.write("If there is no end Date press c/C\n");
    while (true) {
      try {
        date = sc.next();
        if (date.equalsIgnoreCase("c")) {
          return date;
        }
        LocalDate date1 = LocalDate.parse(date);
        LocalDate start = LocalDate.parse(startDate);
        if (date1.compareTo(start) >= 0) {
          System.out.println(date1);
          return date;
        } else {
          view.write("Please Don't Enter Past Dates");
        }
      } catch (Exception e) {
        view.write("Invalid Input. Try Again\n");
      }
    }
  }

  protected Integer validateInterval(StockView view, Scanner sc) {
    int freq;
    view.write("Please enter the Interval Days of Buying");
    while (true) {
      String input = sc.next();
      try {
        freq = Integer.parseInt(input);
        if (freq > 0) {
          return freq;
        } else {
          view.write("Please enter a positive number. Try again\n");
        }
      } catch (NumberFormatException e) {
        view.write("Please a whole no. Found fractional or non-numeric entry. Try again\n");
      }
    }
  }

  private String inputStrategyName(IModelMain main, StockView view, Scanner sc) {
    view.write("Enter the name of the Strategy.\n");

    while (true) {

      String strategyName = sc.next();
      if (!main.checkStrategy(strategyName)) {
        return strategyName;
      } else {
        view.write("This Strategy name already exists. Try Another");
      }
    }
  }

  private Double inputPercentage(StockView view, Scanner sc, double percentage) {
    view.write("Enter the Valid Percentage you wanna invest in each Stock\n");
    view.write("Percentage should be less than equal to " + (100 - percentage) + "\n");

    return inputValidPercentage(view, sc);

  }


  @Override
  public abstract void execute(IModelMain model) throws Exception;
}
