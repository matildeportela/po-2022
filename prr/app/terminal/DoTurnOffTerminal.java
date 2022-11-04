package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
    
  }
  
  @Override
  protected final void execute() throws CommandException {
    
    if(_receiver.isOff()){
      _display.add(Message.alreadyOff());
      _display.display();
    }
    else{
      
      _receiver.turnOff();
      

    }
    
  }
}
