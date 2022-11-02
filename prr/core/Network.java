package prr.core;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.RegisterClientException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.core.exception.ClientNotFoundException;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.TerminalNotFoundException;
import java.util.Collections;
import java.util.Comparator;


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
  private List<Communication> _communicationList;



  // FIXME define contructor(s)
  public Network(){
      _clientList = new ArrayList<Client> ();
      _allTerminals = new ArrayList<Terminal>();
  }

  // FIXME define methods
  
  /**
   * gets overall clients balance i.e. network balance
   */
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



  public List<Client> getSortedClients(){
    Collections.sort(_clientList);    
    return _clientList;
  }
  public List<Client> getInDebtClients(){
    List<Client> inDebtClientList = new ArrayList<Client> ();
    for(Client c : _clientList){
      if(c.hasDebt()){
        inDebtClientList.add(c);
      }
    }
    Collections.sort(inDebtClientList, new Comparator<Client> (){
      public int compare(Client c1, Client c2){
        if(c1.getClientDebt() > c2.getClientDebt()){
          return 1;
        }
        else if(c1.getClientDebt() < c2.getClientDebt()){
          return -1;
        }
        else{
        
        return c1.getKey().toLowerCase().compareTo(c2.getKey().toLowerCase());
        }
      }
    });
        
      
    
    return inDebtClientList;
  

  }

  public List<Client> getClientsWithoutDebt(){
    ArrayList<Client> clientsWithoutDebt = new ArrayList<Client> ();
    for ( Client c : getSortedClients()){
      if(c.getBalance() >= 0){
        clientsWithoutDebt.add(c);
      }
    }
    return clientsWithoutDebt;
  }
  public List<Terminal> getPositiveTerminals(){
    ArrayList<Terminal> positiveTerminalList = new ArrayList<Terminal> ();
    for( Terminal t : _allTerminals){
      if(t.getTerminalBalance() > 0){
        positiveTerminalList.add(t);
      }
    }
    return sortTerminalList(positiveTerminalList);
  }

  /**
   * gets specific client from his unique key and throws exeption if it does not exist
   */
  public Client getClient(String key) throws ClientNotFoundException {
    for(Client c : _clientList){
      if(key.equals(c.getKey())){
        return c;
      }
    }
    throw new ClientNotFoundException(key);
  }

  /**
   * checks if a client exists in the network
   */
  public Boolean hasClient(String key) {
    for(Client c : _clientList){

      if(key.equals(c.getKey())){
        return true;
      }
    }
    return false;
  }

  /**
   * registers a new client in the network in case it does not already exist 
   */
  public void registerClient( String clientKey, String clientName, int clientFiscalNumber ) throws RegisterClientException  {
    Client client;

    if(hasClient(clientKey)) {
        throw new RegisterClientException(clientKey);
    }

    client = new Client(clientKey, clientName, clientFiscalNumber);
    addClient(client);
  
  }

  public List<Terminal> getTerminals(){
    return _allTerminals;
  }
  public List<Terminal> getSortedTerminals(){
    
    
    return sortTerminalList(_allTerminals);
  }
  public static List<Terminal> sortTerminalList(List<Terminal> listaTerminais){
    Collections.sort(listaTerminais);
    return listaTerminais;
  }

   
  

  public void addTerminal(Terminal t){
    _allTerminals.add(t);
  }

  /**
   * checks if a terminal exists in the network
   */
  public boolean hasTerminal(String key) {
    for(Terminal t : _allTerminals){
      if(key.equals(t.getId())) {
        return true;
      }
    }
    return false;
  }

  /**
   * gets specific terminal from his unique key and throws exeption if it does not exist
   */
  public Terminal getTerminal(String key) throws TerminalNotFoundException {
    for(Terminal t : _allTerminals){
      if(key.equals(t.getId())) {
        return t;
      }
    }
    throw new TerminalNotFoundException(key);
  }

  /**
   * gets unactive terminals in the network i.e. terminals that have not been used yet
   */
  public List<Terminal> getUnactiveTerminals(){
    ArrayList<Terminal> unactiveTerminals =  new ArrayList<Terminal>();
    for(Terminal t : _allTerminals){
      if(!t.isActive()){
        unactiveTerminals.add(t);
        
      }
    }
    return sortTerminalList(unactiveTerminals);
}
  /**
   * registers a new termional in the network in case it does not already exist and assigns it to a client
   * * makes distinction between BASIC and FANCY terminals
   */
  public Terminal registerTerminal( String terminalType, String terminalId, String clientId) throws UnrecognizedEntryException, ClientNotFoundException, DuplicateTerminalException, InvalidTerminalKeyException {

    Terminal terminal;
    Client client;
    
    client = getClient(clientId);
      
    
    if(hasTerminal(terminalId)) {
      throw new DuplicateTerminalException(terminalId);
    }
    char[] chars = terminalId.toCharArray();
    for(char c : chars){
      if (!Character.isDigit(c) || chars.length != 6){
        throw new InvalidTerminalKeyException(terminalId);

      }
      
    }
    
    //terminal = TerminalFactory.create(terminalType);

    switch (terminalType) {
        case "BASIC" -> terminal = new BasicTerminal(terminalId, client);
        case "FANCY" -> terminal = new FancyTerminal(terminalId, client);
        default -> throw new UnrecognizedEntryException("terminalType");
    } 

    //adds terminal to networks list of terminals
    addTerminal(terminal);

    //adds terminal to client list of terminals
    client.addTerminal(terminal);

    return terminal;

  }

  public void addFriend(String terminal, String friend){

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

    parser = new Parser(this);
    parser.parseFile(filename);

  }



}