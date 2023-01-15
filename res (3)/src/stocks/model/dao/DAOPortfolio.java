package stocks.model.dao;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import stocks.model.Portfolio;

/**
 * It represents all operations that can be performed on available portfolios.
 */
public interface DAOPortfolio {

  /**
   * Inserts an inflexible portfolio to the data store.
   *
   * @param p represent the object of the inflexible portfolio.
   */
  void insertInflexiblePortfolio(Portfolio p);

  /**
   * Creates a flexible portfolio if given portfolio name does not exist.
   * Adds stock to the portfolio if the given portfolio name exists.
   *
   * @param id           represent the portfolio name.
   * @param tickerSymbol represent the stock symbol.
   * @param count        represent the quantity of stocks to be bought.
   * @param localDate    represent the date at which the purchase needs to be made.
   * @param fee          represents the commission fee for the transaction.
   * @throws IOException  when the input given is invalid.
   * @throws SAXException when parsing the file used for data store fails.
   */
  void modifyFlexiblePortfolio(String id, String tickerSymbol, Double count, String localDate,
                               double fee) throws IOException, SAXException;

  //  /**
  //   *
  //   * @param ID
  //   * @param tickerSymbol
  //   * @param count
  //   */
  //  void modifyInflexiblePortfolio(String ID, String tickerSymbol, Double count);

  /**
   * Checks if a portfolio exists or not.
   *
   * @param id represent the portfolioName to be checked.
   * @return true if the portfolio name already exists. False otherwise.
   * @throws ParserConfigurationException if creating/editing the file fails.
   * @throws IOException                  if given input is invalid.
   * @throws SAXException                 if parsing the file fails.
   */
  boolean checkPortfolio(String id) throws ParserConfigurationException, IOException, SAXException;

  /**
   * Retrives all transactions corresponding to a portfolio.
   *
   * @param id represents the portfolio name.
   * @return a list of list holding data corresponding to that portfolio.
   * @throws ParserConfigurationException if creating/editing the file fails.
   * @throws IOException                  if given input is invalid.
   * @throws SAXException                 if parsing the file fails.
   */
  List<List<String>> retrieveTransaction(String id)
          throws ParserConfigurationException, IOException, SAXException;

  /**
   * Returns a list of names of all portfolios based on the given type.
   *
   * @param type represent the type of the portfolio. Flexible or InFlexible.
   * @return a list of strings.
   */
  List<String> getPotfolioList(String type);

  void addStrategyToPortfolio(String portfolioName, List<String> strategies)
          throws ParserConfigurationException, IOException, SAXException;

  List<String> getStrategies(String portfolioName) throws IOException, SAXException;
}
