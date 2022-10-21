package prr.app.main;

import prr.core.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);

    if(!_receiver.hasNetworkFilename()) {
      addStringField("filename", Message.newSaveAs());
    } else {
      addStringField("filename", Message.saveAs());
    }

  }
  
  @Override
  protected final void execute() {
    String filename = stringField("filename");

    try {
      _receiver.saveAs(filename);
    } catch (Exception e) { //todo...
      e.printStackTrace();
    }
  }
}
