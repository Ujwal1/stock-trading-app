package stocks.view;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import stocks.controller.Features;
import stocks.view.guiscreens.AvailableDataScreen;
import stocks.view.guiscreens.BuyFlexibleFileScreen;
import stocks.view.guiscreens.BuyFlexibleStockScreen;
import stocks.view.guiscreens.CostBasisFlexibleScreen;
import stocks.view.guiscreens.CreateFlexibleFileScreen;
import stocks.view.guiscreens.CreateFlexiblePortfolioScreen;
import stocks.view.guiscreens.InvestInExistingByWeight;
import stocks.view.guiscreens.PerformanceGraphScreen;
import stocks.view.guiscreens.PortfolioCompositionScreen;
import stocks.view.guiscreens.SellFlexibleFileScreen;
import stocks.view.guiscreens.SellFlexibleStockScreen;
import stocks.view.guiscreens.ValueFlexiblePortfolioDateScreen;

/**
 * Represents implementation of the GUI, it has all the functionalies to build the panels and
 * interacts with the GUI in real time as the user is giving input.
 */
public class GUIViewImpl extends JFrame implements GUIView {

  private final JPanel usagePanel;
  private final JPanel directionsPanel;
  private JPanel optionsPanel;
  private JPanel existingDataPanel;
  private JButton createFlexiPortfolioButton;
  private JButton addFlexiStockButton;
  private JButton sellFlexiStockButton;
  private JButton costBasisButton;
  private JButton valueButton;
  private JButton createFlexibleFileButton;
  private JButton addStockFlexiFileButton;
  private JButton sellStockFlexiFileButton;
  private JButton investByWeightStockButton;
  private JButton performanceChartButton;
  private JButton compositionButton;

  /**
   * Constructor for GUIViewImpl class. Constricts the GUI application window.
   *
   * @param caption represents represents the title of the JFrame to be openend.
   */
  public GUIViewImpl(String caption) {

    super(caption);

    this.setSize(1000, 100);
    this.setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setResizable(true);
    this.setLayout(new GridLayout(1, 1));

    JSplitPane verticalSplitPane;

    verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    JSplitPane verticalLinePane0 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JLabel status = new JLabel("Trading Application!!");

    this.existingDataPanel = new JPanel();
    existingDataPanel.setLayout(new BoxLayout(existingDataPanel, BoxLayout.Y_AXIS));
    existingDataPanel.setSize(3000, 3000);
    JScrollPane mainScrollPane = new JScrollPane(existingDataPanel);
    status = new JLabel("You will view exisitng portfolios here.");
    this.existingDataPanel.add(status);
    existingDataPanel.add(new JLabel("\n"));
    existingDataPanel.add(new JLabel("\n"));
    existingDataPanel.add(new JLabel("\n"));
    existingDataPanel.add(new JLabel("\n"));
    existingDataPanel.add(new JLabel("\n"));
    existingDataPanel.add(new JLabel("\n"));
    existingDataPanel.add(new JLabel("\n"));


    verticalLinePane0.add(existingDataPanel);

    this.directionsPanel = new JPanel();
    directionsPanel.setLayout(new BoxLayout(directionsPanel, BoxLayout.PAGE_AXIS));
    directionsPanel.setSize(3000, 3000);
    directionsPanel.add(new JLabel(""));
    verticalLinePane0.add(directionsPanel);

    verticalSplitPane.add(verticalLinePane0);

    this.optionsPanel = new JPanel();
    buildOptions();


    this.usagePanel = new JPanel();
    usagePanel.setSize(3000, 3000);
    status = new JLabel("This is where you will be performing your tasks. " +
            "Please select some option to start. Hope you enjoy this application!!");
    this.usagePanel.add(status);

    JSplitPane verticalLinePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    verticalLinePane.add(optionsPanel);
    verticalLinePane.add(usagePanel);

    verticalSplitPane.add(verticalLinePane);

    this.add(verticalSplitPane);


    this.pack();
    setVisible(true);
  }


  private void buildOptions() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setSize(new Dimension(500, 20000));

    JLabel status = new JLabel("Here's list of available options!!");
    buttonPanel.add(status);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    createFlexiPortfolioButton = new JButton("Create Flexible Portfolio");
    buttonPanel.add(createFlexiPortfolioButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    investByWeightStockButton = new JButton("Invest by specifying weight for stocks");
    buttonPanel.add(investByWeightStockButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    addFlexiStockButton = new JButton("Add Stock to Flexible Portfolio");
    buttonPanel.add(addFlexiStockButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    sellFlexiStockButton = new JButton("Sell Stock from Flexible Portfolio");
    buttonPanel.add(sellFlexiStockButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    costBasisButton = new JButton("Cost Basis of Flexible Portfolio");
    buttonPanel.add(costBasisButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    valueButton = new JButton("Value of Flexible Portfolio");
    buttonPanel.add(valueButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    compositionButton = new JButton("Composition of Flexible Portfolio");
    buttonPanel.add(compositionButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    //    dollarCostButton = new JButton("Dollar Cost Strategy");
    //    buttonPanel.add(dollarCostButton);
    //    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    createFlexibleFileButton = new JButton("Create Flexi Portfolio by file");
    buttonPanel.add(createFlexibleFileButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    addStockFlexiFileButton = new JButton("Add stock to Flexi-Portfolio by file");
    buttonPanel.add(addStockFlexiFileButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    sellStockFlexiFileButton = new JButton("Sell stock from Flexi-Portfolio by file");
    buttonPanel.add(sellStockFlexiFileButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    performanceChartButton = new JButton("View performance chart of existing portfolio");
    buttonPanel.add(performanceChartButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    buttonPanel.add(new JLabel("\n"));
    buttonPanel.add(new JLabel("\n"));

    buttonPanel.add(new JLabel("\n"));
    buttonPanel.add(new JLabel("\n"));
    buttonPanel.add(new JLabel("\n"));
    buttonPanel.add(new JLabel("\n"));

    buttonPanel.add(new JLabel("\n"));
    buttonPanel.add(new JLabel("\n"));
    optionsPanel.add(buttonPanel);
  }

  @Override
  public void addFeatures(Features features) {

    createFlexiPortfolioButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new CreateFlexiblePortfolioScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
      usagePanel.updateUI();
    });

    investByWeightStockButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new InvestInExistingByWeight(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
      usagePanel.updateUI();
    });

    addFlexiStockButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new BuyFlexibleStockScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    sellFlexiStockButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new SellFlexibleStockScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    costBasisButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new CostBasisFlexibleScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    valueButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new ValueFlexiblePortfolioDateScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    compositionButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new PortfolioCompositionScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    createFlexibleFileButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new CreateFlexibleFileScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    addStockFlexiFileButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new BuyFlexibleFileScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    sellStockFlexiFileButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new SellFlexibleFileScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });

    performanceChartButton.addActionListener(l -> {
      usagePanel.removeAll();
      usagePanel.add(new JLabel(""));
      usagePanel.add(new PerformanceGraphScreen(features));
      usagePanel.updateUI();

      updateExistingDataPanel(features);
    });
  }

  void updateExistingDataPanel(Features features) {
    existingDataPanel.removeAll();
    existingDataPanel.add(new JLabel("Available portfolio list:"));
    existingDataPanel.add(new AvailableDataScreen(features));
    existingDataPanel.updateUI();

    write("");
  }


  @Override
  public void home(String message) {
    usagePanel.removeAll();
    usagePanel.updateUI();
    usagePanel.add(new JLabel(message));
  }

  @Override
  public void write(String s) {
    directionsPanel.remove(0);
    JLabel message = new JLabel("Status:" + s);
    message.setForeground(Color.RED);
    directionsPanel.add(message, 0);
    //    JOptionPane.showMessageDialog(usagePanel, s);
    directionsPanel.updateUI();
  }
}
