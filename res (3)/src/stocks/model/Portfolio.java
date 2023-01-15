package stocks.model;

import java.util.Map;

/**
 * Represnts portfolio of stocks.
 */
public interface Portfolio {

  //  void modifyInflexiblePortfolio(String portfolioName, String ticker, double quantity)
  //  throws Exception;


  /**
   * Gets the total value of the portfolio on a certain date.
   *
   * @param date represents the date on which we want the value of the portfolio.
   * @return the total value.
   */
  double getValue(String date);

  /**
   * Gets the summary of a portfolio.
   *
   * @return the summary of the portfolio.
   */
  String getSummary();


  /**
   * Return the unique id of a portfolio.
   *
   * @return the id of the portfolio.
   */
  public String getId();

  /**
   * returns the map representing stock and its quantity in the portfolio.
   *
   * @return the stock-qty map of the portfolio.
   */
  Map<String, Double> getMap();
}
