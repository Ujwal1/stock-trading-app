package stocks.model;

import java.util.List;

/**
 * Class to implement Dollar Cost strategy.
 */
public class DollarCostStatergy implements IStatergy {

  List<List<String>> strategyDetails;

  public DollarCostStatergy(List<List<String>> strategyDetails) {
    this.strategyDetails = strategyDetails;
  }

  @Override
  public List<List<String>> getDetails() {
    return strategyDetails;
  }
}
