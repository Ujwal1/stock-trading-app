package stocks.model;

import java.util.Map;

/**
 * Represents all functionalities of a stock.
 */
public interface StockModel {

  /**
   * Inserts the price of the stock on a certain date into stock data.
   *
   * @param date  represents the date.
   * @param price represents the price of the stock on the said date.
   */
  public void insertDatePrice(String date, double price);

  /**
   * Gets the price of the stock on a certain date.
   *
   * @param date represents the date on which the stock-price is requested.
   * @return the price of the stock.
   */
  public double getPrice(String date);

  /**
   * Gets the map holding the date-price pairs of a stock.
   *
   * @return the map.
   */
  Map<String, Double> getDatePriceMap();
}
