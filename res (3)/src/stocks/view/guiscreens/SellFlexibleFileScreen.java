package stocks.view.guiscreens;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import stocks.controller.Features;

/**
 * A GUI screen to display the necessary inputs which the user has to be done for Selling stocks on
 * Flexible Portfolio via File and its corresponding validation message.
 */
public class SellFlexibleFileScreen extends JPanel implements ActionListener {
  private final Features features;
  private JLabel fileOpenDisplay;


  /**
   * Constructor for the Class to call the feature obj to display the correct message.
   *
   * @param features Object of Feature class to provide access to the features in the controller.
   */
  public SellFlexibleFileScreen(Features features) {
    this.features = features;

    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.add(new JLabel("Selling a stock for a flexible portfolio using a text file.\n" +
            "See documentation for right file format."));

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainPanel.setSize(3000, 3000);


    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);
    this.add(mainPanel);
    this.updateUI();

    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    JButton createPortfolioButton = new JButton("Sell Stock");
    createPortfolioButton.addActionListener(this);

    this.add(createPortfolioButton);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if ("Open file".equals(e.getActionCommand())) {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "txt files", "txt");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(SellFlexibleFileScreen.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        fileOpenDisplay.setText(f.getAbsolutePath());
        this.updateUI();
      }
    } else if ("Sell Stock".equals(e.getActionCommand())) {
      features.sellFlexibleStockFile(fileOpenDisplay.getText());
    }
  }
}
