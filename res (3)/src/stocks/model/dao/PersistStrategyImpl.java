package stocks.model.dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import stocks.model.DollarCostStatergy;
import stocks.model.IStatergy;

/**
 * Class to represent statergies , it has functonality to load save and query on a strategy. It
 * implements an IpersistStrategy Class.
 */
public class PersistStrategyImpl implements IPersistStatergy {

  private final String fileName;
  private static final String FORMAT_XSLT = "src/format.xslt";

  DocumentBuilderFactory docFactory;
  DocumentBuilder docBuilder;
  Document doc;

  /**
   * Constructor to initialize objects.
   */
  public PersistStrategyImpl() {
    fileName = "src/Strategy.xml";

    docFactory = DocumentBuilderFactory.newInstance();
    try {
      docBuilder = docFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    doc = docBuilder.newDocument();

  }

  @Override
  public void saveStrategy(IStatergy strategy1) {

    List<List<String>> strategyDetails = strategy1.getDetails();
    try (InputStream is = new FileInputStream(fileName)) {
      try {
        doc = docBuilder.parse(is);
      } catch (SAXException e) {
        throw new RuntimeException(e);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    NodeList portfolioList = doc.getElementsByTagName("StrategyManager");
    Node root = portfolioList.item(0);
    Element strategy = doc.createElement("Strategy");
    strategy.setAttribute("Name", strategyDetails.get(0).get(0));
    Double amount = Double.parseDouble(strategyDetails.get(0).get(1));
    strategy.setAttribute("Amount", String.valueOf(amount));
    strategy.setAttribute("CommissionFee", strategyDetails.get(0).get(2));
    root.appendChild(strategy);
    for (int index = 1; index < strategyDetails.size() - 1; ++index) {
      Element stockWeightage = doc.createElement("StockAndWeightage");
      stockWeightage.setAttribute("Stock", strategyDetails.get(index).get(0));
      stockWeightage.setAttribute("Percentage", strategyDetails.get(index).get(1));
      strategy.appendChild(stockWeightage);
    }
    Element stockTimeDetails = doc.createElement("StockTimeDetails");
    stockTimeDetails.setAttribute("StartDate",
            strategyDetails.get(strategyDetails.size() - 1).get(0));
    stockTimeDetails.setAttribute("EndDate",
            strategyDetails.get(strategyDetails.size() - 1).get(1));
    stockTimeDetails.setAttribute("Interval",
            strategyDetails.get(strategyDetails.size() - 1).get(2));
    stockTimeDetails.setAttribute("NextTranscationDate",
            strategyDetails.get(strategyDetails.size() - 1).get(0));
    strategy.appendChild(stockTimeDetails);
    try (FileOutputStream output = new FileOutputStream(fileName)) {
      writeXml(doc, output);

    } catch (TransformerException | FileNotFoundException ex) {
      throw new RuntimeException("File Not Found\n");
    } catch (IOException e) {
      throw new RuntimeException("IO Exception\n");
    }
  }

  private static void writeXml(Document doc, OutputStream output)
          throws TransformerException, UnsupportedEncodingException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();

    Transformer transformer =
            transformerFactory.newTransformer(new StreamSource(new File(FORMAT_XSLT)));

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);
    transformer.transform(source, result);

  }

  @Override
  public IStatergy loadStrategy(String strategyName) throws IOException, SAXException {
    List<List<String>> strategyDetails = new ArrayList<>();
    doc = docBuilder.parse(new File(fileName));
    doc.getDocumentElement().normalize();
    NodeList portfolioList = doc.getElementsByTagName("Strategy");
    for (int index = 0; index < portfolioList.getLength(); ++index) {
      Node strategy = portfolioList.item(index);
      String strategyNameFromList = strategy.getAttributes().getNamedItem("Name").getTextContent();
      if (strategyNameFromList.equals(strategyName)
              && (strategy.getNodeType() == Node.ELEMENT_NODE)) {
        Element ele = (Element) strategy;
        // Get the Stock Date Details//
        NodeList dateDetailsList = ele.getElementsByTagName("StockTimeDetails");
        Node detailsNode = dateDetailsList.item(0);
        String startDate = detailsNode.getAttributes().getNamedItem("NextTranscationDate").
                getTextContent();
        String endDate = detailsNode.getAttributes().getNamedItem("EndDate").getTextContent();
        String interval = detailsNode.getAttributes().getNamedItem("Interval").getTextContent();
        List<String> dates = getAllDates(startDate, endDate, interval);
        if (dates.size() >= 1) {
          String date = dates.get(dates.size() - 1);
          LocalDate end = LocalDate.parse(date);
          end = end.plusDays(Long.parseLong(interval));
          Element details = (Element) detailsNode;
          details.setAttribute("NextTranscationDate", String.valueOf(end));
          try (FileOutputStream output = new FileOutputStream(fileName)) {
            writeXml(doc, output);
          } catch (TransformerException | FileNotFoundException ex) {
            throw new RuntimeException(ex);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
        //Get the stock Value Details
        double amount = Double.parseDouble(strategy.getAttributes().getNamedItem("Amount").
                getTextContent());
        double commissionFee = Double.parseDouble(strategy.getAttributes().
                getNamedItem("CommissionFee").
                getTextContent());
        NodeList stockList = ele.getElementsByTagName("StockAndWeightage");
        strategyDetails = getStrategyDetails(stockList, amount, dates, commissionFee);
      }
    }
    IStatergy strategyEntry = new DollarCostStatergy(strategyDetails);
    return strategyEntry;
  }

  private List<List<String>> getStrategyDetails(NodeList stockList, Double amount,
                                                List<String> dates, double commissionFee) {
    List<List<String>> strategyDetails = new ArrayList<>();
    for (String date : dates) {
      for (int index = 0; index < stockList.getLength(); ++index) {
        List<String> temp = new ArrayList<>();
        Node details = stockList.item(index);
        double percentage = Double.parseDouble(details.getAttributes().getNamedItem("Percentage").
                getTextContent());
        String stock = details.getAttributes().getNamedItem("Stock").getTextContent();
        double value = percentage / 100 * amount;
        temp.add(String.valueOf(value));
        temp.add(stock);
        temp.add(date);
        Double commission = commissionFee / stockList.getLength();
        temp.add(String.valueOf(commission));
        strategyDetails.add(temp);
      }
    }
    return strategyDetails;
  }

  private List<String> getAllDates(String startDate, String endDate, String interval) {
    List<String> transactionDates = new ArrayList<>();
    LocalDate present = LocalDate.parse(startDate);
    LocalDate last;
    if (endDate.equalsIgnoreCase("c")) {
      last = LocalDate.now();
    } else {
      last = LocalDate.now();
      LocalDate end = LocalDate.parse(endDate);
      if (end.compareTo(last) <= 0) {
        last = end;
      }
    }
    while (present.compareTo(last) <= 0) {
      transactionDates.add(present.toString());
      present = present.plusDays(Integer.parseInt(interval));
    }
    return transactionDates;
  }

  @Override
  public List<String> allStrategies() throws IOException, SAXException {
    List<String> strategies = new ArrayList<>();
    doc = docBuilder.parse(new File(fileName));
    doc.getDocumentElement().normalize();
    NodeList portfolioList = doc.getElementsByTagName("Strategy");
    for (int index = 0; index < portfolioList.getLength(); ++index) {
      Node strategy = portfolioList.item(index);
      strategies.add(strategy.getAttributes().getNamedItem("Name").getTextContent());
    }
    return strategies;
  }

}
