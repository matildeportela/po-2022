package prr.core;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.RegisterClientException;
import prr.core.exception.RegisterTerminalException;
import prr.core.exception.ClientNotFoundException;
import prr.core.exception.TerminalNotFoundException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  private double _balance;
  private List<Client> _clientList;
  private double _payment;
  private double _debt;
  private List<Terminal> _allTerminals;



  // FIXME define contructor(s)
  public Network(){
      _clientList = new ArrayList<Client> ();
      _allTerminals = new ArrayList<Terminal>();
  }

  // FIXME define methods

  public double getBalance(){
    for(Client c : _clientList){
      _payment += c.getClientPayment();
      _debt += c.getClientDebt();
      
      
    }
    _balance = _payment - _debt;
    return _balance;
  }

  public void addClient(Client c){
    _clientList.add(c);
  }

  public List<Client> getClients(){
    return _clientList;
  }

  public Client getClient(String key) throws ClientNotFoundException {
    for(Client c : _clientList){
      if(key == c.getKey()){
        return c;
      }
    }
    throw new ClientNotFoundException(key);
  }

  public Boolean hasClient(String key) {
    for(Client c : _clientList){
      if(key == c.getKey()){
        return true;
      }
    }
    return false;
  }

  public void registerClient( String clientKey, String clientName, int clientFiscalNumber ) throws RegisterClientException  {
    Client client;

    if(hasClient(clientKey)) {
        throw new RegisterClientException();
    }

    client = new Client(clientKey, clientName, clientFiscalNumber);
    addClient(client);
  
  }

  public List<Terminal> getTerminals(){
    return _allTerminals;
  }

  public void addTerminal(Terminal t){
    _allTerminals.add(t);
  }

  public boolean hasTerminal(String key) {
    for(Terminal t : _allTerminals){
      if(key == t.getId()) {
        return true;
      }
    }
    return false;
  }

  public Terminal getTerminal(String key) throws TerminalNotFoundException {
    for(Terminal t : _allTerminals){
      if(key == t.getId()) {
        return t;
      }
    }
    throw new TerminalNotFoundException(key);
  }
  
  public Terminal registerTerminal( String terminalType, String terminalId, String clientId) throws RegisterTerminalException, UnrecognizedEntryException {

    Terminal terminal;
    
    //procurar cliente com clientId ... se falhar throw exception
    if(hasClient(clientId)) {
      throw new RegisterTerminalException();
    }

    //todo: verificar se é necessário confirmar a existencia desse terminalId e se já existir trow exception
    if(hasTerminal(terminalId)) {
      throw new RegisterTerminalException();
    }

    switch (terminalType) {
        case "BASIC" -> terminal = new BasicTerminal(terminalId, clientId);
        case "FANCY" -> terminal = new FancyTerminal(terminalId, clientId);
        default -> throw new UnrecognizedEntryException("terminalType");
    } 

    return terminal;

  }

  public void addFriend(String terminal, String friend){
    //if (terminal._friends.contains(friend))
    //todo...
  }


  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    Parser parser;

    //FIXME implement method
    System.out.println("IMPORT FILE: " + filename);

    parser = new Parser(this);
    parser.parseFile(filename);

  }
}

