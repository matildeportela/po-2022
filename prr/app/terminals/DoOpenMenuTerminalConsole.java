package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.TerminalNotFoundException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.app.terminal.Menu;
import pt.tecnico.uilib.menus.DoOpenMenu;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {
  

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("key",Message.terminalKey());
  }

  @Override
  protected final void execute() throws UnknownTerminalKeyException {
      String key = stringField("key");
      Menu main;

      try{
        main = new Menu(_receiver, _receiver.getTerminal(key));
        main.open();
      }
      catch(TerminalNotFoundException e){
        throw new UnknownTerminalKeyException(key);

      }  
    
      
    
  }
}
