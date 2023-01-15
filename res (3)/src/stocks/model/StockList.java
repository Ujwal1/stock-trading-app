package stocks.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a list of all available stocks.
 */
public class StockList implements StockListI {

  private final Map<String, Stock> stringStockMap;

  /**
   * Constructor for loading the list of all stocks available already in the local stored file.
   *
   */
  public StockList() throws FileNotFoundException {
    stringStockMap = new HashMap<>();
    Scanner sc1 = new Scanner(new File("src/input.csv"));
    while (sc1.hasNextLine()) {
      String[] x = sc1.nextLine().split(",");
      if (x[1].equals("timestamp")) {
        sc1.nextLine();
        continue;
      }
      if (x.length != 7) {
        continue;
      }
      if (!stringStockMap.containsKey(x[0])) {
        stringStockMap.put(x[0], new Stock(x[0]));
      }
      if (stringStockMap.containsKey(x[0])) {
        Stock temp = stringStockMap.get(x[0]);
        temp.insertDatePrice((x[1]), Double.parseDouble(x[5]));
      }
    }
  }

  @Override
  public Stock getStock(String stockId) {

    if (checkIfExists(stockId)) {
      return stringStockMap.get(stockId);
    }
    return null;
  }

  //  private  Map<String, Stock> getStringStockMap() {
  //    return this.stringStockMap;
  //  }

  @Override
  public List<String> getAvailableStockList() {
    return new ArrayList<>(this.stringStockMap.keySet());
  }

  @Override
  public int size() {
    return stringStockMap.size();
  }

  /**
   * Checks if an Id is a supported stock on our application.
   *
   * @param stockId represents the stockId to be checked.
   * @return true if the stock exists, false otherwise.
   */
  protected static boolean checkIfExists(String stockId) {
    return true;
  }


}
