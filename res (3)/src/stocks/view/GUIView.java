package stocks.view;

import stocks.controller.Features;

/**
 * An GUI view Implementation to show user the corresponding.
 */
public interface GUIView extends StockView {

  /**
   * Call the feature call to add a corresponding feature as inputted by the user.
   * @param features objto add a corresponding feature to the object.
   */
  void addFeatures(Features features);

  /**
   * A welcome message to be displayed in home screen.
   * @param message Message to displayed in home screen.
   */
  void home(String message);
}
