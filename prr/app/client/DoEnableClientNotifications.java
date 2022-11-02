package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.ClientNotFoundException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String key = stringField("key");

    try{
      Client c = _receiver.getClient(key);
      if(!c.hasNotificationsEnabled()) {
        c.enableNotifications();
      } else {
        _display.add( Message.clientNotificationsAlreadyEnabled() );
        _display.display();
      }
    }
    catch(ClientNotFoundException cnfe){
      throw new UnknownClientKeyException(key);
    }
  }
}
