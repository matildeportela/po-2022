package prr.app.client;

import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import prr.core.exception.RegisterClientException;


/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("tax", Message.taxId());
  }
  
  @Override
  protected final void execute() throws DuplicateClientKeyException {

    String key = stringField("key");
    String name = stringField("name");
    int tax = integerField("tax");
    try{
      _receiver.registerClient(key, name, tax);
    }
    catch(RegisterClientException rce){
      throw new DuplicateClientKeyException(key);
    }
  }
}
