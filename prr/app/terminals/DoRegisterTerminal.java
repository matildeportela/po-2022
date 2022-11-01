package prr.app.terminals;

import prr.core.Network;

import java.util.concurrent.ExecutionException;

import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.Terminal;
import prr.core.exception.ClientNotFoundException;
import prr.core.exception.DuplicateTerminalException;
//FIXME add more imports if needed
import prr.core.exception.RegisterTerminalException;
import prr.core.exception.UnrecognizedEntryException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);

    addStringField("key", Message.terminalKey());
    addOptionField("type", Message.terminalType(),"BASIC", "FANCY");
    addStringField("owner", Message.clientKey());
  }

  @Override
  protected final void execute() throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException{
    //FIXME implement command
    String key = stringField("key");
    String type = optionField("type");
    String owner = stringField("owner");
    
    try{
      _receiver.registerTerminal(type, key, owner);
    }
    catch(DuplicateTerminalException rte){
      throw new DuplicateTerminalKeyException(key);
    }
    
    catch(ClientNotFoundException cnf) {
      throw new UnknownClientKeyException(owner);
    }

    catch(UnrecognizedEntryException uee){
      throw new InvalidTerminalKeyException(key);
    }
  }
}
