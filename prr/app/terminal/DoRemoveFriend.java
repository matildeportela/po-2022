package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("key", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    String key = stringField("key");
    
    try{
      _receiver.removeFriend(key);
    }
    catch(UnknownTerminalKeyException utke){
      //nao sabemos o que é suposto fazer nesta situação...
      //throw new UnknownTerminalKeyException(key);
    }
  }
}
