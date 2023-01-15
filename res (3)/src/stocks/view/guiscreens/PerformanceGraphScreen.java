package stocks.view.guiscreens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import stocks.controller.Features;

/**
 * This class represents the view that takes in the inputs to view performance of a portfolio.
 * It makes use of JFXPanel to display the chart in a separate window.
 */
public class PerformanceGraphScreen extends JPanel {

  Features features;
  private JPanel mainPanel = new JPanel();
  private JTextField oldPortfolioName;
  private JTextField startDate;
  private JTextField endDate;
  private JButton showGraphButton = new JButton("Show Graph");
  private Boolean isPortfolioNameValid = false;
  private Boolean isStartDateValid = false;
  private Boolean isEndDateValid = false;
  private JFXPanel fxPanel = new JFXPanel();

  /**
   * Constructor for this screen that takes in a features object and builds the GUI.
   *
   * @param features is used to interact with the controller to implement action listeners.
   */
  public PerformanceGraphScreen(Features features) {
    this.features = features;
    mainPanel.add(new JLabel("Viewing performance chart of an existing flexible portfolio:"));
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setSize(3000, 3000);

    oldPortfolioName = new JTextField(10);
    mainPanel.add(new JLabel("Enter existing portfolio name"));
    mainPanel.add(oldPortfolioName);

    startDate = new JTextField(10);
    mainPanel.add(new JLabel("Enter the starting date from when you wish to" +
            " view the performance in the format yyyy-MM-dd: \n"));
    mainPanel.add(startDate);

    endDate = new JTextField(10);
    mainPanel.add(new JLabel("Enter the ending date till when you wish to " +
            "view the performance in the format yyyy-MM-dd: \n"));
    mainPanel.add(new JLabel("End date should be at-least five days ahead " +
            "of entered start date"));
    mainPanel.add(endDate);

    showGraphButton.setEnabled(false);
    mainPanel.add(showGraphButton);

    createActionListeners(features);
    this.add(mainPanel);

  }

  private void createActionListeners(Features features) {

    oldPortfolioName.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputOldPortfolioName(oldPortfolioName.getText());
        refreshShowGraphButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isPortfolioNameValid = features.inputOldPortfolioName(oldPortfolioName.getText());
        refreshShowGraphButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        // Not needed for plain-text fields
      }

    });

    startDate.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isStartDateValid = features.inputRightDateFormat(startDate.getText());
        refreshShowGraphButtonStatus();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isStartDateValid = features.inputRightDateFormat(startDate.getText());
        refreshShowGraphButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        //ignore
      }
    });

    endDate.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        isEndDateValid = features.inputEndDateForGraph(endDate.getText(), startDate.getText());
        refreshShowGraphButtonStatus();

      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        isEndDateValid = features.inputEndDateForGraph(endDate.getText(), startDate.getText());
        refreshShowGraphButtonStatus();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        // ignore
      }
    });

    showGraphButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame("Performance Chart");
        frame.add(fxPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String graph = features.getPerformanceChart(oldPortfolioName.getText(),
                startDate.getText(), endDate.getText());
        System.out.println(graph + "GRAPH");
        String[] graphData = graph.split("\n");
        String graphTitle = graphData[0] + "\n" + graphData[graphData.length - 1];
        List<String> yAxisArray = new ArrayList<>();
        List<Integer> xAxisArray = new ArrayList<>();

        for (int i = 1; i < graphData.length - 1; i++) {
          yAxisArray.add(graphData[i].split(":")[0]);
          xAxisArray.add(graphData[i].split(":")[1].length() - 1);
        }
        String low = graphData[graphData.length - 1].split(" ")[4].
                replaceAll("[^0-9.]", "");

        String astrik = graphData[graphData.length - 1].split(" ")[9].
                replaceAll("[^0-9.]", "");
        double lowerBound = Double.parseDouble(low);
        double astrikNumber = Double.parseDouble(astrik);
        double highBound = lowerBound + astrikNumber * 50;
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            createGraphPanel(graphTitle, xAxisArray, yAxisArray, lowerBound, highBound,
                    astrikNumber);
          }
        });
      }
    });

  }

  private void createGraphPanel(String graphTitle,
                                List<Integer> xAxisArray, List<String> yAxisArray,
                                Double lowerBound, Double higherBound, Double astrikNumber) {
    Scene scene = createScene(graphTitle, xAxisArray, yAxisArray, lowerBound, higherBound,
            astrikNumber);
    fxPanel.setScene(scene);
  }

  private Scene createScene(String graphTitle, List<Integer> xAxisArray, List<String> yAxisArray,
                            Double lowerBond, Double higherBound, Double astrikNumber) {
    Group root = new Group();
    Text text = new Text();

    text.setX(40);
    text.setY(100);
    text.setText("Welcome JavaFX!");

    //defining the axes
    final NumberAxis yAxis = new NumberAxis(lowerBond, higherBound, 5);
    final CategoryAxis xAxis = new CategoryAxis();

    final LineChart lineChart = new LineChart(xAxis, yAxis);
    graphTitle = graphTitle.replaceAll("asterisk", "Unit");
    lineChart.setTitle(graphTitle);
    //defining a series
    XYChart.Series series = new XYChart.Series();
    series.setName("Portfolio value over time");
    for (int i = 0; i < xAxisArray.size(); i++) {
      Integer low = Integer.valueOf((int) (lowerBond + astrikNumber * xAxisArray.get(i)));
      series.getData().add(new XYChart.Data(yAxisArray.get(i), low));
    }

    Scene scene = new Scene(lineChart, 1000, 800);
    lineChart.getData().add(series);

    root.getChildren().add(text);

    return (scene);
  }

  private void refreshShowGraphButtonStatus() {
    showGraphButton.setEnabled(isEndDateValid && isStartDateValid && isPortfolioNameValid);
  }

}
