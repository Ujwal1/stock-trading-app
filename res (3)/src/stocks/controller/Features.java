package stocks.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the interface that represents the features of our model.
 * It has all the functionality of the day-to-day transactions of stocks.
 * It takes input from view and talk to model and show results in interactive manner.
 */
public interface Features {

  /**
   * Input a PortfolioName from a user and in real time displays him if portfolio name is accepted
   * or not by validating in model.
   *
   * @param newPName Name of the portfolio given by user.
   * @return a boolean stating if It's accepted or not.
   */
  boolean inputNewPortfolioName(String newPName);

  /**
   * Take a Ticker Symbol as input from user, and it checks if its a valid ticker symbol or not.
   *
   * @param ticker Ticker Symbol given by the user.
   * @return a boolean to state if it's a valid ticker symbol or not.
   */
  boolean inputValidStock(String ticker);

  /**
   * Take a date as input from user, and it checks if it's a valid date format or not and if the
   * stock has traded that day or not.
   *
   * @param date   date given by user
   * @param ticker ticker symbol given by user.
   * @return a boolean stating the validity of input.
   */
  boolean inputDateNormal(String date, String ticker);

  /**
   * A validator to check if given quantity of stocks is correct or not from user..
   *
   * @param quantity Quantity of stocks is correct or not.
   * @return a boolean stating the validity.
   */
  boolean inputIntegerNormal(String quantity);

  /**
   * Create a flexible portfolio by the user input. It has necessary validators and gives a real
   * time interaction to user.
   *
   * @param portfolioName Name of the portfolio given by the user.
   * @param ticker        Ticker Symbol given by the user.
   * @param date          date on which portfolio should be created.
   * @param quantity      quantity of the stocks to buy.
   * @param fee           commission fee of the user.
   */
  void createFlexiblePortfolio(String portfolioName, String ticker,
                               String date, double quantity, double fee);

  /**
   * Buy a stock in the flexible portfolio as input given by the user.
   *
   * @param portfolioName Name of the portfolio in which the buy transcations has to be made.
   * @param ticker        Name of the ticker to be added in portfolio.
   * @param date          Date on which the transaction has to be made.
   * @param quantity      Quantity of stock to be bought.
   * @param fee           Commission fee to be incurred.
   */
  void buyStockFlexible(String portfolioName, String ticker,
                        String date, double quantity, double fee);

  Boolean inputOldPortfolioName(String oldPName);

  /**
   * Check if stocks exists on the inputted date.
   *
   * @param date          on which stock transcation has to be checked.
   * @param ticker        ticker symbol on which date has to be checked.
   * @param portfolioName name of the portfolio on which is has to be checked.
   * @return
   */
  Boolean inputDateForwardInTime(String date, String ticker, String portfolioName);

  /**
   * Check if the sale of the quantity on corresponding stock in the given portfolio in possible or
   * not by that given date.
   *
   * @param portfolioName on which sale of stock has to be checked.
   * @param ticker        Ticker symbol on which we need the sale.
   * @param date          Date on which we want the transactions.
   * @param quantity      Quantity to be deducted from that stock.
   * @return a boolean to see if the stock sale is possible or not.
   */
  Boolean inputSellQuantity(String portfolioName, String ticker, String date, String quantity);

  /**
   * If sales the given quantity of stocks from the portfolio on the given date.
   *
   * @param portfolioName on which sale of stock has to be checked.
   * @param ticker        Ticker Symbol on which we need the sale.
   * @param date          Date on sale transactions.
   * @param quantity      Quantity of the stock to be sold.
   * @param fee           commission fee of the transactions.
   */
  void sellStockFlexible(String portfolioName, String ticker, String date,
                         double quantity, double fee);

  /**
   * Check the inputted date is in the correct format or not.
   *
   * @param date Date inputted .
   * @return if the date is in correct format or not.
   */
  Boolean inputRightDateFormat(String date);

  /**
   * Get the cost basis of the corresponding portfolio on the given date.
   *
   * @param portfolioName Name of the portfolio of which we need the cost basis.
   * @param date          Date on which we need the cost basis.
   */
  void getCostBasisOfPortfolio(String portfolioName, String date);

  /**
   * Get the value of portfolio of the corresponding portfolio on the given date.
   *
   * @param portfolioName Name of the portfolio on which we need the total value.
   * @param date          Date on which we need the total value.
   */
  void getValueOfPortfolioDate(String portfolioName, String date);

  /**
   * Show user the Message as home screen.
   */
  void home();

  /**
   * Take new strategy name as input from user and validate it.
   *
   * @param strategyName Name of the strategy given by the user.
   * @return If the strategy Name is accepted or not.
   */
  Boolean inputNewStrategyName(String strategyName);

  /**
   * Take Amount as input from user and validate it.
   *
   * @param amount to be inputted from user.
   * @return a boolean if the accepted amount is accepted or not.
   */
  Boolean inputAmountNormal(String amount);

  /**
   * Take Weight as input form user and validate it.
   *
   * @param weight to be inputted from user.
   * @return a boolean if the accepted amounts accepted ort not.
   */
  Boolean inputWeightNormal(String weight);

  /**
   * Check if the Inputted sum of weight is corresponding to hundred or not.
   *
   * @param weights of the total input by user.
   * @return a boolean to state a success or re-enter message again.
   */
  boolean isSumHundred(ArrayList<Integer> weights);

  /**
   * Validate user input strategy date which should be greater than equal to Portfolio start date.
   *
   * @param portfolioDate Portfolio date of the portfolio creation
   * @param strategyDate  strategy date of the strategy
   * @return a boolean stating if strategy is accepted or not.
   */
  Boolean inputDateGreaterOrEqualStrategyStart(String portfolioDate, String strategyDate);


  /**
   * Validate the end date of statergy based on startDate of strategy.
   *
   * @param startDate startDate of the strategy.
   * @param endDate   end of the strategy.
   * @return check if endDate is after Start Date of strategy.
   */
  Boolean inputDateGreaterOrEqualStrategyEnd(String startDate, String endDate);

  /**
   * Save strategy details into a file.
   *
   * @param strategyDetails to be saved.
   */
  void saveStrategy(List<List<String>> strategyDetails);

  /**
   * Tag the corresponding strategies against the portfolio.
   *
   * @param portfolioName Name of the portfolio o which Strategies is to be added.
   * @param strategies    List of strategies to be added to a portfolio.
   */
  void saveStrategyInPortfolio(String portfolioName, List<String> strategies);

  /**
   * Create Flexible portfolio by a file input.
   *
   * @param path path of the place where file is
   */
  void createFlexiblePortfolioFile(String path);

  /**
   * Buy Flexible portfolio by a file input.
   *
   * @param path path of the place where file is which will be taken as input.
   */
  void buyFlexibleStockFile(String path);

  /**
   * Sell Flexible Portfolio by a file input.
   *
   * @param path path of the place where file is which will be taken as input.
   */
  void sellFlexibleStockFile(String path);

  /**
   * Get the flexible portfolio List from the model.
   *
   * @return the string which has all the list of available flexible portfolio.
   */
  String getFlexiblePortfolioList();

  /**
   * Invest in regular strategies on a portfolio which will be based on weights.
   *
   * @param oldPortfolioName  Portfolio name on wich the investment has to be made.
   * @param allInvestmentData all the investment data to be added on portfolio.
   */
  void investByWeight(String oldPortfolioName, List<List<String>> allInvestmentData);

  Boolean inputEndDateForGraph(String endDate, String startDate);

  String getPerformanceChart(String portfolioName, String startDate, String endDate);

  String getComposition(String portfolioName);
}
