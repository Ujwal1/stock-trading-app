package stocks.controller.commands;

import java.io.IOException;

import stocks.model.IModelMain;

/**
 * This interface represents commands available to the user in this application.
 */
public interface Command {

  /**
   * A method to execute the command.
   *
   * @param model represents the model where the command will take place.
   * @throws IOException if controller cannot read or write to sources correctly.
   */
  void execute(IModelMain model) throws Exception;

}
