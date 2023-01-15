import java.io.InputStreamReader;

import stocks.controller.GUIPortfolioManagerImpl;
import stocks.controller.PortfolioManager;
import stocks.controller.PortfolioManagerImpl;
import stocks.view.GUIView;
import stocks.view.GUIViewImpl;
import stocks.view.StockView;
import stocks.view.StockViewImpl;
import stocks.model.ModelMain;

import static javafx.application.Platform.exit;

/**
 * Represents a runner class for our application.
 */
public class RunApplication {

  /**
   * This main function creates an object of our model and view.
   * The objects are passsed to the controller and the application is run.
   *
   * @param args represents the command line arguments given to the application.
   */
  public static void main(String[] args) {

    if ( args[0].equalsIgnoreCase("-v")) {

      ModelMain model = new ModelMain();

      if ( args[1].equalsIgnoreCase("cli")) {
        StockView view = new StockViewImpl(System.out);
        PortfolioManager controller =
                new PortfolioManagerImpl(model, view, new InputStreamReader(System.in));
        controller.execute();
        exit();
      }
      else if ( args[1].equalsIgnoreCase("gui")) {
        GUIView view = new GUIViewImpl("Welcome to the application!");
        PortfolioManager controller =
                new GUIPortfolioManagerImpl(model, view);
        controller.execute();
      }

    }
    else {
      System.out.println("Invalid command.");
      exit();
    }


  }

}
