package stocks.model;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import stocks.model.dao.DAOAlphaApi;
import stocks.model.dao.DAOInterface;
import stocks.model.dao.DAOPortfolio;
import stocks.model.dao.DAOPortfolioImpl;
import stocks.model.dao.IPersistStatergy;
import stocks.model.dao.PersistStrategyImpl;

/**
 * Represents an implementation of IModelMain.
 * This class currently is the point to communication between the controller and rest of the model.
 */
public class ModelMain implements IModelMain {
  private PortfolioImpl.PortfolioCreator portfolio;

  @Override
  public void createNewInFlexiblePortfolio(String portfolioName) throws Exception {
    portfolio = PortfolioImpl.getBuilder(portfolioName);
  }

  @Override
  public void addStockToInFlexiblePortfolio(String tickerSymbol, double quantity) {
    portfolio = portfolio.addStock(tickerSymbol, quantity);
  }

  @Override
  public void finaliseInflexiblePortfolio() throws Exception {
    PortfolioImpl portfolio1 = (PortfolioImpl) portfolio.create();
  }

  @Override
  public void createFlexiblePortfolio(String portfolioName, String tickerSymbol,
                                      String date, double quantity, double fee)
          throws ParserConfigurationException {
    PortfolioFlexible a = new PortfolioImplFlexible();
    a.createPortfolio(portfolioName, tickerSymbol, date, quantity, fee);
  }

  @Override
  public void sellStockFromFlexiblePortfolio(String portfolioName, String tickerSymbol,
                                             String date, double quantity, double fee) {
    PortfolioFlexible a = new PortfolioImplFlexible();
    a.sellStockFromPortfolio(portfolioName, tickerSymbol, date, quantity, fee);
  }

  @Override
  public boolean checkFlexiblePortfolioExists(String portfolioName) throws Exception {
    DAOPortfolio a;
    try {
      a = new DAOPortfolioImpl();
    } catch (Exception e) {
      throw new Exception("\nUnable to connect to the portfolio data storage.\n");
    }

    List<String> list = a.getPotfolioList("Flexible");
    return list.contains(portfolioName);
  }

  @Override
  public boolean checkPortfolioExists(String portfolioName) throws Exception {
    DAOPortfolio a;
    try {
      a = new DAOPortfolioImpl();
    } catch (Exception e) {
      throw new Exception("\nUnable to connect to the portfolio data storage.\n");
    }

    List<String> list1 = a.getPotfolioList("Flexible");
    List<String> list2 = a.getPotfolioList("InFlexible");
    List<String> listFinal = new ArrayList<>();
    listFinal.addAll(list1);
    listFinal.addAll(list2);
    return listFinal.contains(portfolioName);
  }

  @Override
  public boolean stockExists(String ticker) {
    DAOInterface a = new DAOAlphaApi();
    return a.stockExists(ticker);
  }

  @Override
  public boolean stockOnDateExists(String ticker, String date) {
    DAOInterface a = new DAOAlphaApi();
    return a.stockOnDateExists(ticker, LocalDate.parse(date));
  }


  @Override
  public String getFlexiblePortfolioList() throws Exception {
    DAOPortfolio a;
    try {
      a = new DAOPortfolioImpl();
    } catch (Exception e) {
      throw new Exception("\nUnable to connect to the portfolio data storage.\n");
    }

    List<String> list = a.getPotfolioList("Flexible");
    String formattedList = new String("");
    for (String s : list) {
      formattedList += "\n" + s;
    }
    formattedList += "\n";
    return formattedList;
  }

  private List<String> getFliexibleList() throws Exception {
    DAOPortfolio a;
    try {
      a = new DAOPortfolioImpl();
    } catch (Exception e) {
      throw new Exception("\nUnable to connect to the portfolio data storage.\n");
    }

    List<String> list = a.getPotfolioList("Flexible");
    return list;
  }

  @Override
  public String getAllPortfolioList() throws Exception {
    DAOPortfolio a;
    try {
      a = new DAOPortfolioImpl();
    } catch (Exception e) {
      throw new Exception("\nUnable to connect to the portfolio data storage.\n");
    }

    List<String> list1 = a.getPotfolioList("Flexible");
    List<String> list2 = a.getPotfolioList("InFlexible");
    List<String> listFinal = new ArrayList<>();
    listFinal.addAll(list1);
    listFinal.addAll(list2);
    String formattedList = new String("");
    for (String s : listFinal) {
      formattedList += "\n" + s;
    }
    formattedList += "\n";
    return formattedList;
  }

  @Override
  public boolean checkTransactionDateValidFlexible(String portfolioName, String date)
          throws Exception {
    PortfolioFlexible a;
    try {
      a = new PortfolioImplFlexible();
    } catch (Exception e) {
      throw new Exception("\nUnable to connect to the portfolio data storage.\n");
    }
    return a.checkBuyPossible(portfolioName, date);
  }

  @Override
  public boolean checkSellingQuantityandDateFlexible(
          String portfolioName, String tickerSymbol, String localDate, Double sellStockCount)
          throws Exception {
    PortfolioFlexible a = new PortfolioImplFlexible();
    try {
      return a.checkSellPossible(portfolioName, tickerSymbol, localDate, sellStockCount);
    } catch (Exception e) {
      throw new Exception("Trouble selling the stock on the said date");
    }
  }

  @Override
  public Double getCostBasisOfPortfolio(String portfolioName, String date) throws Exception {
    try {
      PortfolioFlexible a = new PortfolioImplFlexible();
      a.loadStrategy(portfolioName);
      return a.checkCostBasis(portfolioName, date);
    } catch (Exception e) {
      throw new Exception("Could not calculate the cost basis of the portfolio." +
              " Please try again. \n");
    }
  }

  @Override
  public Double valueOfPortfolio(String portfolioName, String date) throws Exception {
    try {
      List<String> list1 = getFliexibleList();
      PortfolioFlexible a = new PortfolioImplFlexible();
      a.loadStrategy(portfolioName);
      if (list1.contains(portfolioName)) {
        return a.getValueOfPortfolio(portfolioName, date);
      } else {
        return a.getValueOfPortfolioInflexible(portfolioName, date);
      }


    } catch (Exception e) {
      throw new Exception("Could not calculate the cost basis of the portfolio." +
              " Please try again. \n");
    }
  }

  @Override
  public String compositionOfPortfolio(String portfolioName) throws Exception {
    try {
      PortfolioFlexible a = new PortfolioImplFlexible();
      a.loadStrategy(portfolioName);
      return a.getCompositionOfPortfolio(portfolioName);
    } catch (Exception e) {
      throw new Exception("Could not calculate the composition of portfolio." +
              " Please try again. \n");
    }
  }

  @Override
  public String getPerformanceChart(String portfolioName, String startDate, String endDate)
          throws Exception {
    try {
      PortfolioFlexible a = new PortfolioImplFlexible();
      a.loadStrategy(portfolioName);
      String performanceChart = a.computeGraph(startDate, endDate, portfolioName);
      return performanceChart;
    } catch (Exception e) {
      throw new Exception("Could not get the performance of portfolio." +
              " Please try again. \n");
    }
  }

  @Override
  public void saveStrategy(List<List<String>> statergyDetails) {
    IPersistStatergy strategy = new PersistStrategyImpl();
    IStatergy strategyEntry = new DollarCostStatergy(statergyDetails);
    strategy.saveStrategy(strategyEntry);
  }

  @Override
  public Boolean checkStrategy(String strategyName) {
    List<String> strategiesName = new ArrayList<>();
    IPersistStatergy strategy = new PersistStrategyImpl();
    try {
      strategiesName = strategy.allStrategies();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
    return strategiesName.contains(strategyName);
  }

  @Override
  public void saveStrategyInPortfolio(String portfolioName, List<String> strategies) {
    DAOPortfolio a = null;
    try {
      a = new DAOPortfolioImpl();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    try {
      a.addStrategyToPortfolio(portfolioName, strategies);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void loadRegularStrategy(String porfolioName, List<List<String>> data)
          throws ParserConfigurationException, IOException, SAXException {
    PortfolioFlexible a = new PortfolioImplFlexible();
    a.loadRegularStrategy(porfolioName, data);
  }
}
