package stocks.controller;

import org.junit.Test;

import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import stocks.model.IModelMain;
import stocks.model.MockModel;
import stocks.view.MockView;

import static org.junit.Assert.assertEquals;

/**
 * Test CLass for PortfolioManager Controller.
 */
public class PortfolioManageImplTest {


  @Test
  public void testCompositionOfPortfolio() {
    StringBuilder log = new StringBuilder();
    MockModel model = new MockModel(log, 12345.0);
    String s = "portfolio_composition 1234";
    Readable in = new StringReader(s);
    StringBuilder out = new StringBuilder();
    MockView view = new MockView(out);
    PortfolioManager manager = new PortfolioManagerImpl(model, view, in);
    try {
      String str = model.compositionOfPortfolio("1234");
      String expected = "Composition of Portfolio" + "\n";
      assertEquals(expected, log.toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCostBasisOfPortfolio() {
    StringBuilder log = new StringBuilder();
    IModelMain model = new MockModel(log, 12345.0);
    String s = "cost_flexible_basis 1234 2022-09-09";
    Readable in = new StringReader(s);
    StringBuilder out = new StringBuilder();
    MockView view = new MockView(out);
    PortfolioManager manager = new PortfolioManagerImpl(model, view, in);
    Double str = null;
    try {
      str = model.getCostBasisOfPortfolio("1234", "2022-09-09");
      assertEquals(12345, str, 0.0);
      assertEquals("Inside Cost Basis" + "\n", log.toString());
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void negativeCommissionFee() {
    StringBuilder log = new StringBuilder();
    IModelMain model = new MockModel(log, 12345.0);
    String s = "add_flexible_stock 1234 GOOG 4 -1";
    Readable in = new StringReader(s);
    StringBuilder out = new StringBuilder();
    MockView view = new MockView(out);
    PortfolioManager manager = new PortfolioManagerImpl(model, view, in);
    try {
      model.sellStockFromFlexiblePortfolio("1234", "GOGG",
              "2022-09-09", 2, -1);
      assertEquals(log.toString(), "Negative Try Again");
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void negativeDate() {
    StringBuilder log = new StringBuilder();
    IModelMain model = new MockModel(log, 12345.0);
    try {
      model.sellStockFromFlexiblePortfolio("1234", "GOGG",
              "20222-09-09", 2, 1);
      assertEquals(log.toString(), "Date Invalid");
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void sellMoreDate() {
    StringBuilder log = new StringBuilder();
    IModelMain model = new MockModel(log, 12345.0);
    String s = "add_flexible_stock 1234 GOOG 20 1";
    Readable in = new StringReader(s);
    StringBuilder out = new StringBuilder();
    MockView view = new MockView(out);
    PortfolioManager manager = new PortfolioManagerImpl(model, view, in);
    try {
      model.sellStockFromFlexiblePortfolio("1234", "GOGG",
              "2022-09-09", 20, 1);
      assertEquals(log.toString(), "Cant Sell More");
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

}
