package stocks.model;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * This interface acts as a single point of communication between the model and the controller.
 * It represents all the functionalities the model provides to the controller.
 */
public interface IModelMain {

  /**
   * Creates a new inflexible portfolio.
   *
   * @param portfolioName represents the name of the portfolio.
   * @throws Exception if the portfolio could not be created.
   */
  void createNewInFlexiblePortfolio(String portfolioName) throws Exception;

  /**
   * Adds stocks to an inflexible portfolio.
   *
   * @param tickerSymbol represents the symbol of the stock to be added.
   * @param quantity     the quantity to be bought.
   */
  void addStockToInFlexiblePortfolio(String tickerSymbol, double quantity);

  /**
   * Finalises the creation of an inflexible portfolio.
   *
   * @throws Exception if portfolio could not be finalised.
   */
  void finaliseInflexiblePortfolio() throws Exception;

  /**
   * This method creates a new portfolio if the @param portfolio name does not exist.
   * Adds stock to the portfolio if it already exists.
   *
   * @param portfolioName represents portfolio name.
   * @param tickerSymbol  represents stock symbol.
   * @param date          represents date on which stock needs to be bought.
   * @param quantity      represent the quantity of the stock to be bought.
   * @param fee           represents the commission fee for the transaction.
   * @throws ParserConfigurationException if file parsing fails.
   */
  void createFlexiblePortfolio(String portfolioName, String tickerSymbol,
                               String date, double quantity, double fee)
          throws ParserConfigurationException;

  /**
   * Sells stocks from an existing portfolio.
   *
   * @param portfolioName represents portfolio name.
   * @param tickerSymbol  represents stock symbol.
   * @param date          represents date on which stock needs to be bought.
   * @param quantity      represent the quantity of the stock to be bought.
   * @param fee           represents the commission fee for the transaction.
   * @throws ParserConfigurationException if file parsing fails.
   */
  void sellStockFromFlexiblePortfolio(String portfolioName, String tickerSymbol,
                                      String date, double quantity, double fee)
          throws ParserConfigurationException;


  /**
   * Checks if a flexible portfolio exists.
   *
   * @param portfolioName represents the portfolio name.
   * @return true if it exists, false otherwise.
   * @throws Exception if connecting to data-source fails.
   */
  boolean checkFlexiblePortfolioExists(String portfolioName) throws Exception;

  /**
   * Checks if a flexible portfolio exists.
   *
   * @param portfolioName represents the portfolio name.
   * @return true if it exists, false otherwise.
   * @throws Exception if connecting to data-source fails.
   */
  boolean checkPortfolioExists(String portfolioName) throws Exception;

  /**
   * Checks if a stock is valid.
   *
   * @param ticker represent the ticker symbol for the stock.
   * @return true if the stock exists. False otherwise.
   */
  boolean stockExists(String ticker);

  /**
   * Checks if a stock traded on a given date.
   *
   * @param ticker represent the ticker symbol for the stock.
   * @param date   represents date.
   * @return true if the stock data is available for the date. False otherwise.
   */
  boolean stockOnDateExists(String ticker, String date);

  /**
   * Returns the list of names of all available flexible-portfolios.
   *
   * @return a formatted string.
   * @throws Exception when connection to the data store fails.
   */
  String getFlexiblePortfolioList() throws Exception;

  /**
   * Gets list of all portfolios as a formatted string.
   *
   * @return the string.
   * @throws Exception if getting the result fails.
   */
  String getAllPortfolioList() throws Exception;

  /**
   * Checks if a transaction date is valid for a flexible portfolio.
   *
   * @param portfolioName represent portfolio name.
   * @param date          represent date.
   * @return true if transaction date valid. False otherwise.
   * @throws Exception if connection to data-source fails.
   */
  boolean checkTransactionDateValidFlexible(String portfolioName, String date) throws Exception;

  /**
   * Checks if selling a certain no of stocks is possible for a portfolio.
   *
   * @param portfolioName  represent the portfolio name.
   * @param tickerSymbol   represent the stock symbol.
   * @param localDate      represent the date of transaction,
   * @param sellStockCount represent the quantity requested to be sold.
   * @return true if sale is possible, false otherwise.
   * @throws Exception if connection to the data-source fails.
   */
  boolean checkSellingQuantityandDateFlexible(
          String portfolioName, String tickerSymbol, String localDate, Double sellStockCount)
          throws Exception;

  /**
   * Gets the cost basis of a portfolio on a certain date.
   *
   * @param portfolioName represents the portfolio name.
   * @param date          represent the date.
   * @return the cost-basis value.
   * @throws Exception if connection to data source fails.
   */
  Double getCostBasisOfPortfolio(String portfolioName, String date) throws Exception;

  /**
   * Calculates value of a portfolio on a certain date.
   *
   * @param portfolioName represents the portfolio name.
   * @param date          represents the date on which.
   * @return the value of the portfolio on the date.
   * @throws Exception if read from the file fails.
   */
  Double valueOfPortfolio(String portfolioName, String date) throws Exception;

  /**
   * Gets the composition of a portfolio as a formatted string.
   *
   * @param portfolioName represents the name of the portfolio.
   * @return a formatted string.
   * @throws Exception if read from the data-files fails.
   */
  String compositionOfPortfolio(String portfolioName) throws Exception;

  /**
   * Gets the performance chart of the portfolio for a given date range.
   *
   * @param portfolioName represents the portfolio name.
   * @param startDate     represent the start date.
   * @param endDate       represent the end date.
   * @return represent the formatted string.
   * @throws Exception if some error occurs while reading the data files.
   */
  String getPerformanceChart(String portfolioName, String startDate, String endDate)
          throws Exception;

  /**
   * Save Stratergies to be used later.
   *
   * @param statergyDetails lust of strings having all strategy details
   */
  void saveStrategy(List<List<String>> statergyDetails);

  /**
   * Check if a strategy with the nanme already exists or not.
   *
   * @param strategyName Name of the stratergy to be checked.
   * @return a Boolean if the stratergy is possible or not.
   */

  Boolean checkStrategy(String strategyName);

  /**
   * Save Strategies In portfolio.
   *
   * @param portfolioName Portfolio name on which the strategies has to be saved.
   * @param strategies    All the strategies which will be tagged to a portfolio.
   */
  void saveStrategyInPortfolio(String portfolioName, List<String> strategies);

  /**
   * Load Statergy into the portfolio as transcations.
   *
   * @param porfolioName name of the portfolio o which a strategy has to be loaded.
   * @param data         date to be loaded as transactions
   * @throws ParserConfigurationException throws a file not find exception.
   * @throws IOException                  throws a IO exception.
   * @throws SAXException                 throws a SAX exception.
   */

  void loadRegularStrategy(String porfolioName, List<List<String>> data) throws
          ParserConfigurationException, IOException, SAXException;


}
