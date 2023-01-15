package stocks.view;

import java.io.PrintStream;

/**
 * Represents Implementation of Stock View.
 */
public class StockViewImpl implements StockView {
  PrintStream out;

  public StockViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void write(String s) {
    this.out.append(s);
  }


}
