package prr.app.terminal;

import prr.app.exception.FileOpenFailedException;
import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.TextCommunication;
import prr.core.exception.TerminalNotFoundException;
import prr.core.exception.TerminalOffException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("destTerminalKey", Message.terminalKey());
    addStringField("message", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException {
    // _receiver é o terminal de origem
    // _network é a network

    String destTerminalKey = stringField("destTerminalKey");
    String message = stringField("message");

    try {
      _network.sendTextCommunication(_receiver, destTerminalKey, message);
    } catch (TerminalOffException toe) {
      _display.add(Message.destinationIsOff(destTerminalKey));
      _display.display();
    }
    catch (TerminalNotFoundException tnfe) {
      throw new UnknownTerminalKeyException(destTerminalKey);
    }


  }
} 
