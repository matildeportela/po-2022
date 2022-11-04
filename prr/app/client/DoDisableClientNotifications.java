package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.ClientNotFoundException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String key = stringField("key");

    try{
      Client c = _receiver.getClient(key);
      if(c.hasNotificationsEnabled()) {
        c.disableNotifications();
      } else {
        _display.add( Message.clientNotificationsAlreadyDisabled() );
        _display.display();
      }
    }
    catch(ClientNotFoundException cnfe){
      throw new UnknownClientKeyException(key);
    }
  }
}
