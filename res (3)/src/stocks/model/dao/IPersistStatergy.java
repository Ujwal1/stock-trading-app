package stocks.model.dao;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import stocks.model.IStatergy;

/**
 * Interface to persist the strategies in a file, it has functionality to load and store into file.
 */
public interface IPersistStatergy {

  /**
   * Save Strategies into the file as input given bt user.
   *
   * @param strategy store the strategies into file.
   */
  void saveStrategy(IStatergy strategy);

  /**
   * Load a strategy details into the list.
   *
   * @param strategy Name of the strategy of which we need details.
   * @return a List of data of that statergies.
   * @throws IOException  throws IO exception
   * @throws SAXException throws SAX exception
   */

  IStatergy loadStrategy(String strategy) throws IOException, SAXException;

  /**
   * Load all the strategies into the List.
   *
   * @return a list having all strategies
   * @throws IOException  throws IO exception
   * @throws SAXException throws SAX exception
   */
  List<String> allStrategies() throws IOException, SAXException;
}
