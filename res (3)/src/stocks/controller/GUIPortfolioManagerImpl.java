package stocks.controller;

import stocks.model.IModelMain;
import stocks.view.GUIView;

/**
 * Class to take input from the user in GUI and create a portfolio and do regular transactions on
 * the portfolio such as buy/sell and perform operations such as cost basis, total values and
 * strategies.
 */
public class GUIPortfolioManagerImpl implements PortfolioManager {

  protected final IModelMain model;
  protected final GUIView view;

  /**
   * Constructor to take implement GUI portfolio manger Impl.
   *
   * @param model take Imodelmain as input to interact with model.
   * @param view  take View as input to display in view.
   */
  public GUIPortfolioManagerImpl(IModelMain model, GUIView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Execute the corresponding feature.
   */
  @Override
  public void execute() {
    view.addFeatures(new FeaturesImpl(model, view));
  }

}
