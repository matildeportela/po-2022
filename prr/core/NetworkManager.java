package prr.core;

import prr.core.exception.ImportFileException;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;

import java.io.*;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

  /** The network itself. */
  private Network _network = new Network();
  private String _networkFilename = "";
  
  public Network getNetwork() {
    return _network;
  }
  
  /**
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   */
  public void load(String filename) throws UnavailableFileException {
    ObjectInputStream objectIn = null;

    try {
      objectIn = new ObjectInputStream(new FileInputStream(filename));
      _network = (Network) objectIn.readObject();
      _network.loadCommAutoincrement();
      setNetworkFilename(filename);
    } catch (Exception e) { 
      throw new UnavailableFileException(filename);
    } finally {
      try {
        if (objectIn != null)
          objectIn.close();
      } catch (Exception e) {
        throw new UnavailableFileException(filename);
      }
    }


  }
  
  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    saveAs(getNetworkFilename());
  }
  
  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
  
    ObjectOutputStream objectOut = null;

    try {
      objectOut = new ObjectOutputStream(new FileOutputStream(filename, false));
      _network.storeCommAutoincrement();
      objectOut.writeObject(_network);
      setNetworkFilename(filename);
    } finally {
      if (objectOut != null)
        objectOut.close();
    }

  }

  public boolean hasNetworkFilename() {
    return !getNetworkFilename().isEmpty();
  }

  protected void setNetworkFilename(String filename) {
    _networkFilename = filename;
  }

  protected String getNetworkFilename() {
    return _networkFilename;
  }

  /**
   * Read text input file and create domain entities..
   * 
   * @param filename name of the text input file
   * @throws ImportFileException
   */
  public void importFile(String filename) throws ImportFileException {
    try {
      _network.importFile(filename);
    } catch (IOException | UnrecognizedEntryException e) {
      throw new ImportFileException(filename, e);
    }
  }  
}
