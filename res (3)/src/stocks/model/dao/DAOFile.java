package stocks.model.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

import stocks.model.StockModel;

/**
 * This is an implementation of DAOInterface.
 * It represents collection of all the stocks that are available locally.
 */
public class DAOFile implements DAOInterface {

  @Override
  public Map<StockModel, Double> buyStockOnDate(String ticker, Double quantity,
                                                LocalDate dateOfPurchase) {
    return null;
  }

  @Override
  public Double valueOfStockPreDate(String ticker, LocalDate date) {
    String[] stockData = getStockDataFromCSVPreDate(ticker, date);
    String strStockPrice = stockData[4];
    return Double.parseDouble(strStockPrice);
  }


  @Override
  public double valueOfStockOnDate(String ticker, LocalDate date) throws IllegalArgumentException {
    String[] stockData = getStockDataFromCSV(ticker, date);
    String strStockPrice = stockData[4];
    return Double.parseDouble(strStockPrice);
  }


  @Override
  public boolean stockOnDateExists(String ticker, LocalDate date) {
    try {
      String[] ignoreStockData = getStockDataFromCSV(ticker, date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean stockExists(String ticker) {
    try {
      Scanner scan = new Scanner(new File("src/stocksDataDump/" + "allStockList" + ".csv"));
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        line = line.split(",")[0];
        if (line.equals(ticker)) {
          scan.close();
          return true;
        }
      }
      scan.close();
      return false;
    } catch (FileNotFoundException e) {
      return false;
      //      throw new IllegalArgumentException("All stocks names file not found\n");
    }

  }

  private String[] getStockDataFromCSV(String ticker, LocalDate date) throws
          IllegalArgumentException {
    String strDate = convertDateFormat(date);
    try {
      Scanner scan = new Scanner(new File("src/stocksDataDump/" + ticker + ".csv"));
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        if (line.contains(strDate)) {
          scan.close();
          return line.split(",");
        }
      }
      scan.close();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Entered stock symbol file doesn't exist. " +
              "\nEnter a valid stock ticker symbol.\n");
    }
    throw new IllegalArgumentException("Enter valid date for given stock.\n");
  }

  private String[] getStockDataFromCSVPreDate(String ticker, LocalDate date) {
    String strDate = convertDateFormat(date);
    try {
      Scanner scan = new Scanner(new File("src/stocksDataDump/" + ticker + ".csv"));

      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        String dateCurrent = line.split(",")[0];
        if (line.contains("timestamp")) {
          continue;
        }
        if (line.contains(strDate)) {
          scan.close();
          return line.split(",");
        } else if (date.isAfter(LocalDate.parse(dateCurrent))) {
          scan.close();
          return line.split(",");
        }
      }
      scan.close();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Entered stock symbol file doesn't exist. " +
              "\nEnter a valid stock ticker symbol.\n");
    }
    throw new IllegalArgumentException("Enter valid date for given stock.\n");
  }

  private String convertDateFormat(LocalDate date) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return dateFormatter.format(date);
  }

}
