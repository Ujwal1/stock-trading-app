package stocks.model;

import java.util.HashMap;
import java.util.Map;

import stocks.model.dao.DAOPortfolio;
import stocks.model.dao.DAOPortfolioImpl;

/**
 * Represents an implementation of the Inflexible portfolio.
 * The implementation is simple holding stocks and their quantities in addition to portfolio id
 * and creation date.
 */

public class PortfolioImpl implements Portfolio {

  private final String id;
  private final Map<String, Double> stockCountMap;

  /**
   * Represents the constructor of the portfolioImpl.
   *
   * @param id            represents Id of the portfolio.
   * @param stockCountMap represents the map holding stocks and their quantities.
   */
  public PortfolioImpl(String id, Map<String, Double> stockCountMap) throws Exception {
    this.id = id;
    this.stockCountMap = stockCountMap;
    try {
      DAOPortfolio a = new DAOPortfolioImpl();
      a.insertInflexiblePortfolio(this);
    } catch (Exception e) {
      throw new Exception("Could not save the portfolio");
    }

  }

  /**
   * A builder function for PortfolioImpl.
   *
   * @param id represents id of the portfolio.
   * @return a PortfolioCreator object.
   */
  public static PortfolioCreator getBuilder(String id) {
    return new PortfolioCreator(id);
  }

  @Override
  public double getValue(String date) {
    double totalValue = 0;
    return 0;
  }

  @Override
  public String getSummary() {
    String result = "";
    result = result + "Portfolio Id: " + this.id + "\n";
    result = result + "Stock" + "    " + "Qty" + "\n";
    for (Map.Entry<String, Double> entry : stockCountMap.entrySet()) {
      result = result + entry.getKey() + " " + entry.getValue() + "\n";
    }
    return result;
  }


  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public Map<String, Double> getMap() {
    return this.stockCountMap;
  }

  /**
   * A private creator class for the Portfolio class.
   */
  public static class PortfolioCreator {
    private String id;
    private Map<String, Double> stockCountMap;

    /**
     * Represents the constructor for the portfolio creator class.
     *
     * @param id represents id of the portfolio.
     */
    private PortfolioCreator(String id) {
      this.id = id;
      this.stockCountMap = new HashMap<>();
    }

    /**
     * Finalises the creation of a portfolio. Portfolio can no longer be modified.
     *
     * @return a portfolio.
     * @throws Exception Throws Exception
     */
    public Portfolio create() throws Exception {
      return new PortfolioImpl(this.id, this.stockCountMap);
    }

    /**
     * Adds new stock to the portfolio.
     *
     * @param stockId represents the stock to be added
     * @param count   represents the no of stocks of the particular stock
     * @return returns true if the stock was successfully added to the portfolio, false otherwise.
     */
    public PortfolioCreator addStock(String stockId, Double count) {
      if (StockList.checkIfExists(stockId)) {
        stockCountMap.put(stockId, count);
        return this;
      } else {
        return null;
      }
    }

  }

}
