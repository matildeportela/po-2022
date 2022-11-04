package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.TerminalNotFoundException;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

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
      //throw new UnknownTerminalKeyException(key); //todo: verificar se és suposto lancar alguma excepção
    }
  }
}
