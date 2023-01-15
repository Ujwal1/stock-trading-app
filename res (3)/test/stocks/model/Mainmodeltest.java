package stocks.model;


import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import stocks.model.dao.IPersistStatergy;
import stocks.model.dao.PersistStrategyImpl;
import static org.junit.Assert.assertEquals;

/**
 * Test The main model for various scenarios such as composition, total value cost basis for various
 * Scenarios.
 */
public class Mainmodeltest {

  @Test
  public void costBasis() {
    try {
      PortfolioFlexible p = new PortfolioImplFlexible();
      p.createPortfolio("testGui", "GOOG", "2022-09-09",
              4, 2);
      List<List<String>> list = new ArrayList<>();
      List<String> temp = new ArrayList<>();
      temp.add("testStatergy");
      temp.add("2000");
      temp.add("2");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("GOOG");
      temp.add("100");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("2022-09-09");
      temp.add("2022-10-10");
      temp.add("2");
      list.add(temp);
      temp = new ArrayList<>();
      IPersistStatergy iPersistStatergy = new PersistStrategyImpl();
      IStatergy statergy = new DollarCostStatergy(list);
      iPersistStatergy.saveStrategy(statergy);
      p.loadStrategy("testStatergy");
      assertEquals(p.checkCostBasis("testGui", "2022-09-09"),
              449.12, 2);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void costBasis2() {
    try {
      PortfolioFlexible p = new PortfolioImplFlexible();
      p.createPortfolio("testGui2", "GOOG", "2022-09-09",
              4, 2);
      List<List<String>> list = new ArrayList<>();
      List<String> temp = new ArrayList<>();
      temp.add("testStatergy2");
      temp.add("2000");
      temp.add("2");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("GOOG");
      temp.add("100");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("2022-09-09");
      temp.add("2022-10-10");
      temp.add("2");
      list.add(temp);
      IPersistStatergy iPersistStatergy = new PersistStrategyImpl();
      IStatergy statergy = new DollarCostStatergy(list);
      iPersistStatergy.saveStrategy(statergy);
      p.loadStrategy("testStatergy2");
      assertEquals(p.checkCostBasis("testGui2", "2022-09-09"), 449.12, 2);
      assertEquals(p.checkCostBasis("testGui2", "2022-08-09"), 0, 2);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void costBasisEnding() {
    try {
      PortfolioFlexible p = new PortfolioImplFlexible();
      p.createPortfolio("testGui3", "GOOG", "2022-09-09",
              4, 2);
      List<List<String>> list = new ArrayList<>();
      List<String> temp = new ArrayList<>();
      temp.add("testStatergy3");
      temp.add("2000");
      temp.add("2");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("GOOG");
      temp.add("100");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("2022-09-09");
      temp.add("c");
      temp.add("2");
      list.add(temp);
      IPersistStatergy iPersistStatergy = new PersistStrategyImpl();
      IStatergy statergy = new DollarCostStatergy(list);
      iPersistStatergy.saveStrategy(statergy);
      p.loadStrategy("testStatergy3");
      assertEquals(p.checkCostBasis("testGui3", "2022-09-09"), 449.12, 2);
      assertEquals(p.checkCostBasis("testGui3", "2022-08-09"), 0, 2);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testvalueOfPortfoio() {
    try {
      PortfolioFlexible p = new PortfolioImplFlexible();
      p.createPortfolio("testGui4", "GOOG", "2022-09-09",
              4, 2);
      List<List<String>> list = new ArrayList<>();
      List<String> temp = new ArrayList<>();
      temp.add("testStatergy4");
      temp.add("2000");
      temp.add("2");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("GOOG");
      temp.add("100");
      list.add(temp);
      temp = new ArrayList<>();
      temp.add("2022-09-09");
      temp.add("c");
      temp.add("2");
      list.add(temp);
      IPersistStatergy iPersistStatergy = new PersistStrategyImpl();
      IStatergy statergy = new DollarCostStatergy(list);
      iPersistStatergy.saveStrategy(statergy);
      p.loadStrategy("testStatergy4");
      assertEquals(p.getValueOfPortfolio("testGui4", "2022-09-09"),
              449.12, 2);
      assertEquals(p.getValueOfPortfolio("testGui4", "2022-08-09"),
              0, 2);
      assertEquals(p.getValueOfPortfolio("testGui4", "2022-11-10"),
              376.68, 2);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
