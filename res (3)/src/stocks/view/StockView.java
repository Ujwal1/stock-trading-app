package stocks.view;

/**
 * This interface provides all the functionalities that our view provides.
 */
public interface StockView {

  /**
   * Function to write data into the GUI screen, it has all the necessary message
   * after validation to be shown to user.
   * @param message Message to be written on the screen.
   */
  void write(String message);
}
