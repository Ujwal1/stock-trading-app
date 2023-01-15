package stocks.model.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

import stocks.model.StockModel;

/**
 * This is an implementation of DAOInterface.
 * It represents collection of all the stocks that are available by the AlphaVantageAPI.
 * It interacts with another implementation DAOFile to see if the requested stocks are
 * available locally.
 */
public class DAOAlphaApi implements DAOInterface {

  @Override
  public Map<StockModel, Double> buyStockOnDate(String ticker,
                                                Double quantity, LocalDate dateOfPurchase) {

    return null;
  }

  @Override
  public Double valueOfStockPreDate(String ticker, LocalDate date) {
    updateStorageIfRequired(ticker, date);
    DAOInterface fileobj = new DAOFile();
    return fileobj.valueOfStockPreDate(ticker, date);
  }


  @Override
  public double valueOfStockOnDate(String ticker, LocalDate date) {
    updateStorageIfRequired(ticker, date);
    DAOInterface fileobj = new DAOFile();
    return fileobj.valueOfStockOnDate(ticker, date);
  }

  private void updateStorageIfRequired(String ticker, LocalDate date) {
    if (!isStockInStorage(ticker)) {
      fetchStockDataToStorage(ticker);
    } else {
      if (!new DAOFile().stockOnDateExists(ticker, date)) {
        try {
          Files.deleteIfExists(Paths.get("src/stocksDataDump/" + ticker + ".csv"));
          fetchStockDataToStorage(ticker);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void fetchStockDataToStorage(String ticker) {

    URL url = getValidURLStock(ticker);
    downloadCSVFile(url, ticker);
  }

  private URL getValidURLStock(String ticker) {

    String apiKey = "3C7FVRDGXUUJAQIO"; // high volume key for alphaVantage API
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    return url;
  }

  private URL getURLListedStocksLatest() {
    String apiKey = "3C7FVRDGXUUJAQIO"; // high volume key for alphaVantage API
    URL url = null;
    try {
      url = new URL("https://www.alphavantage.co/query?function=LISTING_STATUS&apikey="
              + apiKey);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    return url;
  }


  private void downloadCSVFile(URL url, String ticker) throws IllegalArgumentException {
    try {
      String outputFilePath = "src/stocksDataDump/" + ticker + ".csv";
      InputStream in = url.openStream();


      //
      //      InputStreamReader inR = new InputStreamReader(in);
      //      BufferedReader buf = new BufferedReader(inR);
      //
      //      String line = buf.readLine();
      //      line = buf.readLine();
      //      if ( line.contains("Error Message")) {
      //        throw new IllegalArgumentException("Entered stock symbol is not valid");
      //      }

      Files.copy(in, Paths.get(outputFilePath));
      new File(outputFilePath);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isStockInStorage(String ticker) {
    return new File("src/stocksDataDump/" + ticker + ".csv").exists();
  }

  @Override
  public boolean stockOnDateExists(String ticker, LocalDate date) {
    try {
      double stockPriceIgnore = valueOfStockOnDate(ticker, date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean stockExists(String ticker) {
    if (isStockInStorage(ticker) || new DAOFile().stockExists(ticker)) {
      return true;
    }
    try {
      URL url = getURLListedStocksLatest();
      Files.deleteIfExists(Paths.get("src/stocksDataDump/" + "allStockList" + ".csv"));
      downloadCSVFile(url, "allStockList");
      return new DAOFile().stockExists(ticker);
    } catch (IllegalArgumentException e) {
      return false;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
