package stocks.model;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Interface to Represent Flexible Portfolios, it contains all the methods which can use to
 * represent  transactions  in a flexible portfolio like buying and selling as well as check for
 * total value and cost basis of a portfolio.
 */
public interface PortfolioFlexible extends Portfolio {

  /**
   * Create or modify a flexible stocks.
   *
   * @param portfolioName portfilo id.
   * @param tickerSymbol  ticker symbol of a stock
   * @param date          on which we have to do transcation
   * @param quantity      qyantity of the
   * @param fee           fee of transaction
   * @throws ParserConfigurationException throws a error
   */
  void createPortfolio(String portfolioName, String tickerSymbol, String date,
                       double quantity, double fee) throws ParserConfigurationException;

  /**
   * Sell a stock from a given portfolio on a particular date.
   *
   * @param portfolioName Portfolio on which you want to sell a stock.
   * @param tickerSymbol  Symbol of the stock to be sold.
   * @param date          Date on which the transaction has to be made.
   * @param quantity      The quantity of asked stock to be sold.
   * @param fee           Fee for transactions
   */
  void sellStockFromPortfolio(String portfolioName, String tickerSymbol, String date,
                              double quantity, double fee);

  void purchaseStock(String portfolioName, String tickerSymbol, String date, double quantity,
                     double fee);

  /**
   * Check CostBasis of a Portfolio based on an askedDate,which is total amount invested in a stock
   * and the corresponding commission fee incorporated.
   *
   * @param id        The ID of the portfolio to calculate costBasis
   * @param askedDate The transcation upto which we need to calculate CostBasis.
   * @return The cost basis value upto the asked Date.
   */
  Double checkCostBasis(String id, String askedDate);

  /**
   * Check Total value of a Portfolio based on an askedDate, which is the current portfolio value.
   *
   * @param portfolioName The ID of the portfolio to caalcualte costBasis
   * @param askedDate     The date upto which we need to calculate total portfolio value.
   * @return The Total value of Portfolio upto the given date.
   */
  Double getValueOfPortfolio(String portfolioName, String askedDate);


  /**
   * Get the value of the Inflexible Portfolio.
   *
   * @param id        of the portfolio of which value is requried.
   * @param askedDate Value on the current Date.
   * @return The total value of the portfolio.
   */
  Double getValueOfPortfolioInflexible(String id, String askedDate);

  /**
   * Check if a portfolio exists or not.
   *
   * @param portfolioName The portfolio to be checked
   * @return if exits or not
   * @throws IOException                  exception
   * @throws SAXException                 exception
   * @throws ParserConfigurationException exception
   */
  boolean isPortfolioExists(String portfolioName) throws IOException, SAXException,
          ParserConfigurationException;

  /**
   * Check if buy of a stock is possible or not.
   *
   * @param id        the id of portfolio
   * @param localDate date when we want to buy
   * @return a boolean if buy is possible or not.
   */
  Boolean checkBuyPossible(String id, String localDate);

  /**
   * check if sell is possible or not.
   *
   * @param id             of the portfollio.
   * @param tickerSymbol   stock symbol.
   * @param localDate      local date to be searched
   * @param sellStockCount stock count to be searched.
   * @return if sale is passible or not.
   */
  Boolean checkSellPossible(String id, String tickerSymbol, String localDate,
                            Double sellStockCount);

  /**
   * Get Composition of the portfolio.
   *
   * @param portfolioName Get portfolio name to be searched.
   * @return a formatted string of composition.
   */
  String getCompositionOfPortfolio(String portfolioName);

  /**
   * Compute the performance of a portfolio for a given date Interval.
   *
   * @param startDate     Start Date on  which performance graph need to be calculated.
   * @param endDate       End Date on which performance graph need to be calculated.
   * @param portfolioName On which Performance graph need to be calculated.
   * @return a String which need to be shown
   */
  String computeGraph(String startDate, String endDate, String portfolioName);

  /**
   * Load Strategy into the portfolio.
   *
   * @param portfolioName portfolio name on which strategies has to be loaded.
   * @throws IOException                  throws IO exception
   * @throws SAXException                 throws SAX exception
   * @throws ParserConfigurationException throws Parse exception
   */
  void loadStrategy(String portfolioName) throws IOException, SAXException,
          ParserConfigurationException;

  /**
   * Load Strategy into the portfolio as transcations.
   *
   * @param portfolioName name of the portfolio o which a strategy has to be loaded.
   * @param data          date to be loaded as transactions
   * @throws ParserConfigurationException throws a file not find exception.
   * @throws IOException                  throws a IO exception.
   * @throws SAXException                 throws a SAX exception.
   */
  void loadRegularStrategy(String portfolioName, List<List<String>> data)
          throws ParserConfigurationException, IOException, SAXException;
}
