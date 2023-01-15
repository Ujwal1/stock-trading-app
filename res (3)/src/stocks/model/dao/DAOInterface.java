package stocks.model.dao;

import java.time.LocalDate;
import java.util.Map;

import stocks.model.StockModel;

/**
 * This class represents all the stocks that are available to our application.
 */
public interface DAOInterface {

  /**
   * To buy stock on Date.
   *
   * @param ticker         Symbol of a stock
   * @param quantity       Quantity of a stock
   * @param dateOfPurchase dateofPurcharse of a stock.
   * @return a map
   */
  Map<StockModel, Double> buyStockOnDate(String ticker, Double quantity, LocalDate dateOfPurchase);

  /**
   * The value of portfolio on a certain date.
   *
   * @param ticker Symbol of thr ticker
   * @param date   Localdate of the
   * @return the value of the stock.
   */
  Double valueOfStockPreDate(String ticker, LocalDate date);

  /**
   * This function is used to get the value of a particular stock on a certain date.
   *
   * @param ticker represents the tickerSymbol of a stock.
   * @param date   represents the date on which the value of stock is needed.
   * @return the value of the stock on the given date.
   */
  double valueOfStockOnDate(String ticker, LocalDate date) throws IllegalArgumentException;

  /**
   * This function checks if a stock traded on a given date.
   *
   * @param ticker represent the stock symbol.
   * @param date   represent the date.
   * @return true if the stock traded on a certain date. False otherwise.
   */
  boolean stockOnDateExists(String ticker, LocalDate date);

  /**
   * This function checks is a stock symbol is valid and is supported by AlphaVantage API.
   *
   * @param ticker represents the stock symbol.
   * @return true if the stock symbol is valid. False otherwise
   */
  boolean stockExists(String ticker);
}
