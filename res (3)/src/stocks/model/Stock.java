package stocks.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a simple representation of the StockModel.
 * A stock entity has a uniquely identifying stockId and a map holding its price corresponding to
 * different dates.
 */
public class Stock implements StockModel {
  private final String stockId;
  private Map<String, Double> datePriceMap;

  /**
   * Represents a constructor for Stock class that takes in just the stockId as a parameter.
   *
   * @param stockId Stock Symbol of a stock
   */
  public Stock(String stockId) {
    this.stockId = stockId;
    datePriceMap = new HashMap<>();
  }

  @Override
  public void insertDatePrice(String date, double price) {
    datePriceMap.put(date, price);
  }

  @Override
  public double getPrice(String date) {
    return datePriceMap.get(date);
  }

  @Override
  public Map<String, Double> getDatePriceMap() {
    return this.datePriceMap;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof Stock)) {
      return false;
    } else {
      return this.stockId.equals(((Stock) other).stockId);
    }
  }

  @Override
  public int hashCode() {
    return this.stockId.hashCode();
  }

}
