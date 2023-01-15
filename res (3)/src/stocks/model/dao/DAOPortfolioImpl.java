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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
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

import stocks.model.Portfolio;

import static java.lang.System.out;

/**
 * It is an implementation of DAOPortfolio that represents all the available portfolios.
 */
public class DAOPortfolioImpl implements DAOPortfolio {

  DocumentBuilderFactory docFactory;
  DocumentBuilder docBuilder;
  Document doc;
  String portfolioName;
  String tickerName;
  String manager;
  String trans;
  String nameCount;
  String nameLocalDate;
  String name;
  String commission;
  private static final String FILENAME = "src/parser.xml";

  private static final String FORMAT_XSLT = "src/format.xslt";

  /**
   * Initializes the necessary items before loading the file.
   *
   * @throws ParserConfigurationException if parsing the data store file fails.
   */
  public DAOPortfolioImpl() throws ParserConfigurationException {
    manager = "PortfolioManager";
    portfolioName = "Portfolio";
    tickerName = "TickerSymbol";
    trans = "Transcation";
    nameCount = "Count";
    nameLocalDate = "Date";
    name = "name";
    commission = "Fee";
    docFactory = DocumentBuilderFactory.newInstance();
    docBuilder = docFactory.newDocumentBuilder();
    doc = docBuilder.newDocument();
  }

  @Override
  public void insertInflexiblePortfolio(Portfolio p) {
    try (InputStream is = new FileInputStream(FILENAME)) {
      try {
        doc = docBuilder.parse(is);
      } catch (SAXException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Element portfolio1 = doc.createElement(portfolioName);
    portfolio1.setAttribute("id", p.getId());
    portfolio1.setAttribute("Type", "InFlexible");
    NodeList portfolioManager = doc.getElementsByTagName(manager);
    Node port = portfolioManager.item(0);
    port.appendChild(portfolio1);
    for (Map.Entry<String, Double> entry : p.getMap().entrySet()) {
      Element test = doc.createElement(tickerName);
      test.setAttribute(name, entry.getKey());
      portfolio1.appendChild(test);
      Element transaction;
      transaction = doc.createElement(trans);
      transaction.setAttribute(nameCount, Double.toString(entry.getValue()));
      test.appendChild(transaction);
    }
    try (FileOutputStream output =
                 new FileOutputStream(FILENAME)) {
      writeXml(doc, output);
    } catch (TransformerException | FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    out.println("Insert Success:\n");

  }

  @Override
  public void modifyFlexiblePortfolio(String idOne, String tickerSymbol, Double count,
                                      String localDate, double fee)
          throws IOException, SAXException {
    try (InputStream is = new FileInputStream(FILENAME)) {
      doc = docBuilder.parse(is);
      NodeList portfolioList = doc.getElementsByTagName(portfolioName);
      boolean flagPortfolio = false;
      for (int i = 0; i < portfolioList.getLength(); ++i) {
        Node portfolio = portfolioList.item(i);
        if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
          String id = portfolio.getAttributes().getNamedItem("id").getTextContent();
          if (idOne.equals(id.trim())) {
            flagPortfolio = true;
            NodeList stkList = portfolio.getChildNodes();
            boolean flagStock = false;
            for (int j = 0; j < stkList.getLength(); ++j) {
              Node stk = stkList.item(j);
              String s = stk.getNodeName();
              if ((stk.getNodeType() == Node.ELEMENT_NODE) && (s.equals(tickerName))) {
                if (stk.getAttributes().getNamedItem(name).getTextContent().equals(tickerSymbol)) {
                  flagStock = true;
                  Element transcation1 = doc.createElement(trans);
                  transcation1.setAttribute(nameCount, Double.toString(count));
                  transcation1.setAttribute(nameLocalDate, localDate);
                  transcation1.setAttribute(commission, Double.toString(fee));
                  stk.appendChild(transcation1);
                }
              }
            }
            if (!flagStock) {
              Element newStk = doc.createElement(tickerName);
              newStk.setAttribute(name, tickerSymbol);
              portfolio.appendChild(newStk);
              Element transcation1 = doc.createElement(trans);
              transcation1.setAttribute(nameCount, Double.toString(count));
              transcation1.setAttribute(nameLocalDate, localDate);
              transcation1.setAttribute(commission, Double.toString(fee));
              newStk.appendChild(transcation1);
            }
          }
        }
      }
      if (!flagPortfolio) {
        helperCreateFlexibleStockAndPortfolio(idOne, tickerSymbol, count, localDate, fee);

      }
      try (FileOutputStream output =
                   new FileOutputStream(FILENAME)) {
        writeXml(doc, output);
      } catch (TransformerException ex) {
        throw new RuntimeException(ex);
      }

    }
  }


  private Element helperCreateFlexibleStockAndPortfolio(String id, String tickerSymbol,
                                                        Double count,
                                                        String localDate, double fee) {
    Element portfolio1 = doc.createElement(portfolioName);
    portfolio1.setAttribute("id", id);
    portfolio1.setAttribute("Type", "Flexible");
    NodeList portfolioManager = doc.getElementsByTagName(manager);
    Node port = portfolioManager.item(0);
    port.appendChild(portfolio1);
    Element test = doc.createElement(tickerName);
    test.setAttribute(name, tickerSymbol);
    portfolio1.appendChild(test);
    Element transaction;
    transaction = doc.createElement(trans);
    transaction.setAttribute(nameCount, Double.toString(count));
    transaction.setAttribute(nameLocalDate, localDate);
    transaction.setAttribute(commission, Double.toString(fee));
    test.appendChild(transaction);
    return portfolio1;
  }

  private Element helperCreateInflexibleStockAndPortfolio(String id,
                                                          String tickerSymbol, Double count) {
    Element portfolio1 = doc.createElement(portfolioName);
    portfolio1.setAttribute("id", id);
    portfolio1.setAttribute("Type", "InFlexible");
    NodeList portfolioManager = doc.getElementsByTagName(manager);
    Node port = portfolioManager.item(0);
    port.appendChild(portfolio1);
    Element test = doc.createElement(tickerName);
    test.setAttribute(name, tickerSymbol);
    portfolio1.appendChild(test);
    Element transaction;
    transaction = doc.createElement(trans);
    transaction.setAttribute(nameCount, Double.toString(count));
    test.appendChild(transaction);
    return portfolio1;
  }

  //  @Override
  //  public void modifyInflexiblePortfolio(String ID, String tickerSymbol, Double count) {
  //    try (InputStream is = new FileInputStream(FILENAME)) {
  //      doc = docBuilder.parse(is);
  //      Element portfolio1;
  //      portfolio1 = helperCreateInflexibleStockAndPortfolio(ID, tickerSymbol, count);
  //      portfolio1.setAttribute("Type", "Inflexible");
  //    } catch (FileNotFoundException e) {
  //      throw new RuntimeException(e);
  //    } catch (IOException e) {
  //      throw new RuntimeException(e);
  //    } catch (SAXException e) {
  //      throw new RuntimeException(e);
  //    }
  //
  //    try (FileOutputStream output =
  //                 new FileOutputStream(FILENAME)) {
  //      writeXml(doc, output);
  //    } catch (TransformerException ex) {
  //      throw new RuntimeException(ex);
  //    } catch (FileNotFoundException e) {
  //      throw new RuntimeException(e);
  //    } catch (IOException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }


  private static void writeXml(Document doc,
                               OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();

    Transformer transformer = transformerFactory.newTransformer(
            new StreamSource(new File(FORMAT_XSLT)));
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);
  }

  @Override
  public boolean checkPortfolio(String id)
          throws ParserConfigurationException, IOException, SAXException {
    docBuilder = docFactory.newDocumentBuilder();
    doc = docBuilder.parse(new File(FILENAME));
    doc.getDocumentElement().normalize();
    NodeList list = doc.getElementsByTagName(portfolioName);
    for (int temp = 0; temp < list.getLength(); ++temp) {
      Node node = list.item(temp);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        if (id.equals(((Element) node).getAttribute("id"))) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public List<List<String>> retrieveTransaction(String idOne)
          throws ParserConfigurationException, IOException, SAXException {
    List<List<String>> listOfList = new ArrayList<>();
    docFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    doc = docBuilder.parse(new File(FILENAME));
    doc.getDocumentElement().normalize();
    NodeList portfolioList = doc.getElementsByTagName(portfolioName);
    for (int portfolio_index = 0; portfolio_index < portfolioList.getLength(); ++portfolio_index) {
      Node portfolio = portfolioList.item(portfolio_index);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        String id = portfolio.getAttributes().getNamedItem("id").getTextContent();
        if (idOne.equals(id.trim())) {
          NodeList stkList = portfolio.getChildNodes();
          for (int j = 0; j < stkList.getLength(); ++j) {
            Node stk = stkList.item(j);
            if (stk.getNodeType() == Node.ELEMENT_NODE) {
              Element element = (Element) stk;
              NodeList transcationList = element.getElementsByTagName(trans);
              List<String> singleList = new ArrayList<>();
              for (int k = 0; k < transcationList.getLength(); ++k) {
                Node transcationNode = transcationList.item(k);
                singleList.add(element.getAttribute(name));
                singleList.add(transcationNode.getAttributes().
                        getNamedItem(nameCount).getTextContent());
                if (portfolio.getAttributes().getNamedItem("Type").
                        getTextContent().equals("Flexible")) {
                  singleList.add(transcationNode.getAttributes().
                          getNamedItem(nameLocalDate).getTextContent());
                  singleList.add(transcationNode.getAttributes().
                          getNamedItem(commission).getTextContent());
                }
                listOfList.add(singleList);
                singleList = new ArrayList<>();
              }
            }
          }
        }
      }
    }

    return listOfList;
  }

  @Override
  public List<String> getPotfolioList(String type) {

    List<String> list = new ArrayList<>();
    try {
      docFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    try {
      doc = docBuilder.parse(new File(FILENAME));
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    doc.getDocumentElement().normalize();
    NodeList portfolioList = doc.getElementsByTagName(portfolioName);
    for (int portfolio_index = 0; portfolio_index < portfolioList.getLength(); ++portfolio_index) {
      Node portfolio = portfolioList.item(portfolio_index);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        String id = portfolio.getAttributes().getNamedItem("id").getTextContent();
        if (portfolio.getAttributes().getNamedItem("Type").getTextContent().equals(type)) {
          list.add(id);
        }
      }
    }
    return list;
  }

  @Override
  public void addStrategyToPortfolio(String portfolioID, List<String> strategies)
          throws ParserConfigurationException, IOException, SAXException {
    doc = docBuilder.parse(new File(FILENAME));
    doc.getDocumentElement().normalize();
    NodeList portfolioList = doc.getElementsByTagName(portfolioName);
    for (int index = 0; index < portfolioList.getLength(); ++index) {
      Node portfolio = portfolioList.item(index);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        String id = portfolio.getAttributes().getNamedItem("id").getTextContent();
        if (portfolioID.equals(id.trim())) {
          for (String strategy : strategies) {
            Element strategyElement = doc.createElement("Strategy");
            strategyElement.setAttribute("Name", strategy);
            portfolio.appendChild(strategyElement);
          }
        }
      }
    }

    try (FileOutputStream output =
                 new FileOutputStream(FILENAME)) {
      writeXml(doc, output);
    } catch (TransformerException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public List<String> getStrategies(String portfolioID) throws IOException, SAXException {
    List<String> strategies = new ArrayList<>();
    doc = docBuilder.parse(new File(FILENAME));
    doc.getDocumentElement().normalize();
    NodeList portfolioList = doc.getElementsByTagName(portfolioName);
    for (int index = 0; index < portfolioList.getLength(); ++index) {
      Node portfolio = portfolioList.item(index);
      if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
        String id = portfolio.getAttributes().getNamedItem("id").getTextContent();
        if (portfolioID.equals(id.trim())) {
          Element ele = (Element) portfolio;
          NodeList strategiesList = ele.getElementsByTagName("Strategy");
          for (int i = 0; i < strategiesList.getLength(); ++i) {
            Node strategy = strategiesList.item(i);
            String name = strategy.getAttributes().getNamedItem("Name").getTextContent();
            strategies.add(name);
            Element strategyElement = (Element) strategy;
          }

        }
      }
    }
    try (FileOutputStream output =
                 new FileOutputStream(FILENAME)) {
      writeXml(doc, output);
    } catch (TransformerException | FileNotFoundException ex) {
      throw new RuntimeException(ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return strategies;
  }

}


