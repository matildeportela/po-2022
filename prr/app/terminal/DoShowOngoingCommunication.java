package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

  DoShowOngoingCommunication(Network context, Terminal terminal) {
    super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(!_receiver.isBusy()) {
      _display.add(Message.noOngoingCommunication());
    } else {
      _display.add(_receiver.getOngoingCommunication());
    }
    _display.display();
  }
}
