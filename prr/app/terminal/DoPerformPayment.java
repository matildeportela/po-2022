package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addIntegerField("commKey", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    int commKey = integerField("commKey");

    try {
      _network.makePayment(_receiver, commKey);
    } catch (Exception e) {
      _display.add(Message.invalidCommunication());
      _display.display();
    }



  }
}
