package stocks.view.guiscreens;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import stocks.controller.Features;


/**
 * An interface to show users available data in the view screen. It shows all the required data
 * as the user is interacting with view.
 */
public class AvailableDataScreen extends JPanel {

  /**
   * Constructor for the Class to call the feature obj to display the correct message.
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public AvailableDataScreen(Features features) {

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    mainScrollPane.setPreferredSize(new Dimension(200,100));

    String [] portfolios = features.getFlexiblePortfolioList().split("\n");
    for ( int i = 0; i < portfolios.length; i++) {
      mainPanel.add(new JLabel(portfolios[i]));
    }
    add(mainScrollPane);
  }
}
