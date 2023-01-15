package stocks.view;

/**
 * Mock for view to display messages to User.
 */
public class MockView implements StockView {

  public StringBuilder log;

  public MockView(StringBuilder log) {
    this.log = log;
  }


  @Override
  public void write(String s) {
    int i = 0;
  }
}
