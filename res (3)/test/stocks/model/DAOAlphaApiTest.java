package stocks.model;

import org.junit.Test;
//import org.xml.sax.SAXException;
//
//import java.io.IOException;
import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.xml.parsers.ParserConfigurationException;

import stocks.model.dao.DAOAlphaApi;
import stocks.model.dao.DAOFile;
import stocks.model.dao.DAOInterface;
//import stocks.model.dao.DAOPortfolio;
//import stocks.model.dao.DAOPortfolioImpl;
//import stocks.model.dao.IPersistStatergy;
//import stocks.model.dao.PersistStrategyImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test DAOAplhaTest Class.
 */
public class DAOAlphaApiTest {

  @Test
  public void priceOfStock1() {
    DAOInterface a = new DAOFile();
    LocalDate date = LocalDate.parse("2022-11-10");
    double price = a.valueOfStockOnDate("IBM", date);
    assertEquals(141.23, price, 0.0);
  }


  @Test
  public void priceOfStock3() {
    DAOInterface a = new DAOAlphaApi();
    LocalDate date = LocalDate.parse("2022-11-10");
    double price = a.valueOfStockOnDate("GOOG", date);
    assertEquals(94.17, price, 0.0);
  }

  @Test
  public void isStockExistsonDate() {
    DAOInterface a = new DAOAlphaApi();
    assertEquals(false, a.stockOnDateExists("GOOG", LocalDate.ofEpochDay(2022 - 11 - 06)));
  }

  @Test
  public void isStockExists() {
    DAOInterface a = new DAOAlphaApi();
    assertEquals(false, a.stockExists("GOG"));
  }

  @Test
  public void isStockExists2() {
    DAOInterface a = new DAOAlphaApi();
    assertEquals(false, a.stockExists("GOG"));
  }

  @Test
  public void isStockExists3() {
    DAOInterface a = new DAOAlphaApi();
    assertEquals(true, a.stockExists("RELIANCE.BSE"));
  }

  @Test
  public void isStockExistsonDate3() {
    DAOInterface a = new DAOAlphaApi();
    assertEquals(true, a.stockExists("DAI.DEX"));
  }

  @Test
  public void priceOnDate() {
    DAOInterface a = new DAOAlphaApi();
    assertEquals(2441.8501,
            a.valueOfStockOnDate("RELIANCE.BSE",
                    LocalDate.parse("2022-10-25")), 0.0);
  }



}
