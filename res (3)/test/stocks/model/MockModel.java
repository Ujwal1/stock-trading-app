package stocks.model;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Mock Model for IModelMain.
 */
public class MockModel implements IModelMain {
  private final StringBuilder log;
  private final Double uniqueCode;

  public MockModel(StringBuilder log, Double uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void createNewInFlexiblePortfolio(String portfolioName) throws Exception {
    log.append("Inside Create New Inflexible").append("\n");
  }

  @Override
  public void addStockToInFlexiblePortfolio(String tickerSymbol, double quantity) {
    log.append("Inside Create add Stock in flexible").append("\n");
  }

  @Override
  public void finaliseInflexiblePortfolio() throws Exception {
    log.append("Inside Finalize Inflexible Portolio").append("\n");
  }

  @Override
  public void createFlexiblePortfolio(String portfolioName,
                                      String tickerSymbol, String date, double quantity,
                                      double fee) throws ParserConfigurationException {
    log.append("Inside Create Flexible Portfolio").append("\n");
  }

  @Override
  public void sellStockFromFlexiblePortfolio(String portfolioName,
                                             String tickerSymbol, String date, double quantity,
                                             double fee) throws ParserConfigurationException {
    if (fee < 0) {
      log.append("Negative Try Again");
    } else if (date.equals("20222-09-09")) {
      log.append("Date Invalid");
    } else if (quantity == 20) {
      log.append("Cant Sell More");
    } else {
      log.append("Inside Sell Stock Flexible Portfolio").append("\n");
    }

  }

  @Override
  public boolean checkFlexiblePortfolioExists(String portfolioName)
          throws ParserConfigurationException, IOException, SAXException {
    log.append("Inside Check Flexible Portfolio Exists").append("\n");
    return false;
  }

  @Override
  public boolean checkPortfolioExists(String portfolioName) throws Exception {
    log.append("Inside check Portfolio Exists").append("\n");
    return true;
  }

  @Override
  public boolean stockExists(String ticker) {
    return false;
  }

  @Override
  public boolean stockOnDateExists(String ticker, String date) {
    return false;
  }

  @Override
  public String getFlexiblePortfolioList() throws Exception {
    log.append("Inside getFlexible").append("\n");
    return log.toString();
  }

  @Override
  public String getAllPortfolioList() throws Exception {
    log.append("All portfolio List").append("\n");
    return log.toString();
  }

  @Override
  public boolean checkTransactionDateValidFlexible(String portfolioName, String date)
          throws Exception {
    log.append("Inside Check Transactions").append("\n");
    return false;
  }

  @Override
  public boolean checkSellingQuantityandDateFlexible(String portfolioName,
                                                     String tickerSymbol,
                                                     String localDate,
                                                     Double sellStockCount)
          throws Exception {
    log.append("Inside Check Selling Quantity").append("\n");
    return false;
  }

  @Override
  public Double getCostBasisOfPortfolio(String portfolioName, String date) throws Exception {
    log.append("Inside Cost Basis").append("\n");
    return uniqueCode;
  }

  @Override
  public Double valueOfPortfolio(String portfolioName, String date) throws Exception {
    log.append("valueOfPortflio").append("\n");
    return uniqueCode;
  }

  @Override
  public String compositionOfPortfolio(String portfolioName) throws Exception {
    log.append("Composition of Portfolio").append("\n");
    return log.toString();
  }

  @Override
  public String getPerformanceChart(String portfolioName,
                                    String startDate,
                                    String endDate)
          throws Exception {
    log.append("Performance Chat").append("\n");
    return log.toString();
  }

  @Override
  public void saveStrategy(List<List<String>> statergyDetails) {
    //ignore
  }

  @Override
  public Boolean checkStrategy(String strategyName) {
    return null;
  }

  @Override
  public void saveStrategyInPortfolio(String portfolioName, List<String> strategies) {
    int i = 0;
  }

  @Override
  public void loadRegularStrategy(String porfolioName, List<List<String>> data)
          throws ParserConfigurationException, IOException, SAXException {
    int i = 0;
  }


}


