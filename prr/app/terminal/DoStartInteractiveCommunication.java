package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.*;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("destTerminalKey", Message.terminalKey());
    addOptionField("commType", Message.commType(), "VIDEO", "VOICE");
  }
  
  @Override
  protected final void execute() throws CommandException {
    //nota:  _receiver é o terminal de origem, _network é a network
    String destTerminalKey = stringField("destTerminalKey");
    String commType = stringField("commType");

    try {
      _network.startInteractiveCommunication(_receiver, destTerminalKey, commType);
    }
    catch (TerminalNotFoundException tnfe) {
      throw new UnknownTerminalKeyException(destTerminalKey);
    }
    catch (UnsuportedCommunicationAtOrigin uco) {
      _display.add(Message.unsupportedAtOrigin(_receiver.getId(), commType));
      _display.display();
    }
    catch (UnsuportedCommunicationAtDestination ucd) {
      _display.add(Message.unsupportedAtDestination(destTerminalKey, commType));
      _display.display();
    }
    catch (TerminalOffException toe) {
      //se terminal de destino isOff ... mostra mensagem destinationIsOff
      _display.add(Message.destinationIsOff(destTerminalKey));
      _display.display();
    }
    catch (TerminalBusyException e) {
      //se terminal de destino esta ocupado ... mostra mensagem destinationIsBusy
      _display.add(Message.destinationIsBusy(destTerminalKey));
      _display.display();
    }
    catch (TerminalIsSilentException e ) {
      // se terminal de destino esta no silencio ... mostra mensagem destinationIsSilent
      _display.add(Message.destinationIsSilent(destTerminalKey));
      _display.display();
    }
    catch (UnsuportedCommunication e) {
      //todo: o que fazer quando o terminal tentar ligar para si proprio????
    }
    catch (UnknownCommunicationType e){
      //todo: ????
    }

  }
}
