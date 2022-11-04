package prr.app.client;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.exception.ClientNotFoundException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver); 
    addStringField("key", Message.key());
}
  
  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException{
    String key = stringField("key");
    try{
      _display.add(Message.clientPaymentsAndDebts(key, _receiver.getPaymentsFromClient(key), _receiver.getDebtsFromClient(key)));
      _display.display();
    }
    catch(ClientNotFoundException cnfe){
      throw new UnknownClientKeyException(key);
    }
  }
}
