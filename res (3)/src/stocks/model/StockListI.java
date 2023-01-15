package stocks.model;

import java.util.List;

/**
 * Represents an interface to all the available stocks.
 */
public interface StockListI {

  /**
   * Checks if a stock is present or not.
   *
   * @param stockId represents the tickerNo/stockId to be checked.
   * @return the stock if its present. Returns null otherwise.
   */
  Stock getStock(String stockId);

  /**
   * Gets the list of names of all available stocks.
   *
   * @return a list of strings.
   */
  List<String> getAvailableStockList();

  /**
   * Gets the no of available stocks on our application.
   *
   * @return the no of available stocks.
   */
  int size();
}
