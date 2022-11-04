package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.exception.ClientNotFoundException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    addStringField("key", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException{
    String key = stringField("key");
    try{
      _display.addAll(_receiver.getCommunicationsToClient(key));
      _display.display();

    }
    catch(ClientNotFoundException cnfe){
      throw new UnknownClientKeyException(key);
    }
    
  }

}

