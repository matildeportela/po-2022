package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.TerminalOffException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
    addIntegerField("duration", Message.duration());
  }
  
  @Override
  protected final void execute() throws CommandException {
    int duration = integerField("duration");
    try {
      double cost = _network.endInteractiveCommunication(_receiver, duration);
      _display.add(Message.communicationCost((long) cost)); //todo: será que o cost devia ser um long em vez de um double?!?!
      _display.display();
    } catch (Exception e) {
      //todo...
    }
  }
}
