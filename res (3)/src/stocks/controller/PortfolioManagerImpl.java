package stocks.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import stocks.controller.commands.BuyFlexibleStock;
import stocks.controller.commands.BuyFlexibleStockFile;
import stocks.controller.commands.Command;
import stocks.controller.commands.CompositionOfPortfolio;
import stocks.controller.commands.CostBasisOfFlexiblePortfolio;
import stocks.controller.commands.CreateFlexiblePortfolio;
import stocks.controller.commands.CreateFlexiblePortfolioFile;
import stocks.controller.commands.CreateInFlexiblePortfolio;
import stocks.controller.commands.CreateInflexiblePortfolioFile;
import stocks.controller.commands.PlotPerformanceChart;
import stocks.controller.commands.SellFlexibleStock;
import stocks.controller.commands.SellFlexibleStockFile;
import stocks.controller.commands.ValueOfPortfolioOnDate;
import stocks.model.IModelMain;
import stocks.view.StockView;

/**
 * A controller for ModelMain. This controller works with a Readable object.
 * It has been designed to accept a sequence of multiple inputs.
 * It takes in instance of the view and the ModelMain Model.
 */
public class PortfolioManagerImpl implements PortfolioManager {
  private final StockView view;
  private final IModelMain model;
  Readable in;
  private final Scanner sc;

  /**
   * Constructor for PortfolioManagerImpl.
   *
   * @param model             represents the instance of the model this controller will work with
   * @param view              represents the instance of the view this controller will work with.
   * @param inputStreamReader represents inputstream for the controller.
   */
  public PortfolioManagerImpl(IModelMain model, StockView view, Readable inputStreamReader) {
    if (view == null) {
      throw new IllegalArgumentException("View Cannot be Null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model Cannot be Null");
    }
    this.view = view;
    this.model = model;
    this.in = inputStreamReader;
    this.sc = new Scanner(this.in);
  }

  private Map<String, Command> getCommandMap() {
    Map<String, Command> knownCommands = new HashMap<>();
    knownCommands.put("create_flexible_portfolio", new CreateFlexiblePortfolio(view, sc));
    knownCommands.put("add_flexible_stock", new BuyFlexibleStock(view, sc));
    knownCommands.put("sell_flexible_stock", new SellFlexibleStock(view, sc));
    knownCommands.put("cost_flexible_basis", new CostBasisOfFlexiblePortfolio(view, sc));

    knownCommands.put("create_inflexible_portfolio", new CreateInFlexiblePortfolio(view, sc));

    knownCommands.put("portfolio_value", new ValueOfPortfolioOnDate(view, sc));
    knownCommands.put("portfolio_composition", new CompositionOfPortfolio(view, sc));

    knownCommands.put("create_flexible_file", new CreateFlexiblePortfolioFile(view, sc));
    knownCommands.put("add_flexible_stock_file", new BuyFlexibleStockFile(view, sc));
    knownCommands.put("sell_flexible_stock_file", new SellFlexibleStockFile(view, sc));
    knownCommands.put("create_inflexible_file", new CreateInflexiblePortfolioFile(view, sc));
    knownCommands.put("performance_chart", new PlotPerformanceChart(view, sc));
    return knownCommands;
  }

  @Override
  public void execute() {
    Map<String, Command> commandMap = getCommandMap();
    String commandMessage = "\nEnter 'create_flexible_portfolio' to create a flexible portfolio. \n"
            + "Enter 'add_flexible_stock' to enter a stock to a flexible portfolio. \n"
            + "Enter 'sell_flexible_stock' to sell a stock from a flexible portfolio. \n"
            + "Enter 'cost_flexible_basis' to see the cost-basis of a flexible portfolio. \n"
            + "Enter 'create_inflexible_portfolio' to create an inflexible portfolio. \n"
            + "Enter 'portfolio_value' to find the value of a portfolio on a date. \n"
            + "Enter 'portfolio_composition' to find the composition of a portfolio. \n"
            + "Enter 'create_flexible_file' to create a flexible portfolio from file.\n"
            + "Enter 'add_flexible_stock_file' to add stock to an existing "
            + "flexible portfolio using file.\n"
            + "Enter 'sell_flexible_stock_file' to sell stock from an existing "
            + "flexible portfolio using file.\n"
            + "Enter 'create_inflexible_file' to create an  in-flexible portfolio using file.\n"
            + "Enter 'performance_chart' to view the performance of a "
            + "portfolio over a period of time.\n"
            + "\nSee documentation for right file format.\n"
            + "q/quit to exit the application";
    view.write("Hello! Welcome to the application. \n\n");

    while (true) {
      view.write(commandMessage);
      String in = sc.next();
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        return;
      }
      Command cmd = commandMap.getOrDefault(in, null);
      if (cmd == null) {
        view.write("\nInvalid command. Please refer the documentation for valid commands.\n");
      } else {
        try {
          cmd.execute(model);
        } catch (Exception e) {
          view.write(e.getMessage());
        }
      }

    }
  }

}
