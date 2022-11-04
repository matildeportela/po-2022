package prr.core;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;

import prr.core.exception.*;
import prr.app.exception.InvalidTerminalKeyException;

import java.util.Collections;
import java.util.Comparator;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  
  private List<Client> _clientList;
  private List<Terminal> _allTerminals;
  private List<Communication> _communicationList;
  private static int _communicationAutoIncrement;
  private int _storedCommAutoIncrement;

  private static Plan _pricingPlan;



  Network(){
      _clientList = new ArrayList<Client> ();
      _allTerminals = new ArrayList<Terminal>();
      _communicationList = new ArrayList<Communication>();
      _communicationAutoIncrement = 0;
      _storedCommAutoIncrement = 0;
      _pricingPlan = new BasicPlan();

  }
  
  /**
   * gets overall clients balance i.e. network balance
   */
  public long getBalance(){
    long totalPayments = 0;
    long totalDebts = 0;
    for(Client c : _clientList){
      totalPayments += c.getClientPayment();
      totalDebts += c.getClientDebt();
    }
    return totalPayments - totalDebts;
  }
     
      
      
   

  public long getPaymentsFromClient(String key) throws ClientNotFoundException{
    
    for(Client c : _clientList){
      if(c.getKey().equals(key)){
        return c.getClientPayment();
      }
    }
    throw new ClientNotFoundException(key);

  }
  public long getDebtsFromClient(String key) throws ClientNotFoundException{
    
    for(Client c : _clientList){
      if(c.getKey().equals(key)){
        return c.getClientDebt();
      }
    }
    throw new ClientNotFoundException(key);

  }
  

  protected void addClient(Client c){
    _clientList.add(c);
  }



  public List<Client> getSortedClients(){
    Collections.sort(_clientList);    
    return new ArrayList<>( _clientList );
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
    return new ArrayList<>( sortTerminalList(positiveTerminalList) );
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
  public boolean hasClient(String key) {
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
  public void registerClient( String clientKey, String clientName, int clientFiscalNumber ) throws RegisterClientException {
    Client client;

    if(hasClient(clientKey)) {
        throw new RegisterClientException(clientKey);
    }

    client = new Client(clientKey, clientName, clientFiscalNumber);
    addClient(client);
  
  }

  public List<Terminal> getTerminals(){
    return new ArrayList<>( sortTerminalList(_allTerminals) );
  }

  protected static List<Terminal> sortTerminalList(List<Terminal> listaTerminais){
    Collections.sort(listaTerminais);
    return listaTerminais;
  }

  protected void addTerminal(Terminal t){
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


  protected static int getNextCommId(){
    _communicationAutoIncrement += 1;
    return _communicationAutoIncrement;
  }

  void loadCommAutoincrement() {
    _communicationAutoIncrement = _storedCommAutoIncrement;
  }

  void storeCommAutoincrement() {
    _storedCommAutoIncrement = _communicationAutoIncrement;
  }

  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException  {
    Parser parser;

    parser = new Parser(this);
    parser.parseFile(filename);

  }

  public List<Communication> getCommunications() {
      return new ArrayList<>(_communicationList);
  }
  
  public List<Communication> getCommunicationsFromClient(String key) throws ClientNotFoundException{
    ArrayList<Communication> communicationsFromClient = new ArrayList<Communication>();
    for(Client C: _clientList){
      if(C.getKey().equals(key)){
        for(Terminal t : C.getTerminalList()){
          for(Communication c: t.getMadeCommunication()){
            communicationsFromClient.add(c);
            
          }
        }
        return communicationsFromClient;
      }
    }
    throw new ClientNotFoundException(key);
  }
  public List<Communication> getCommunicationsToClient(String key) throws ClientNotFoundException{
    ArrayList<Communication> communicationsToClient = new ArrayList<Communication>();
    for(Client C: _clientList){
      if(C.getKey().equals(key)){
        for(Terminal t : C.getTerminalList()){
          for(Communication c: t.getReceivedCommunication()){
            communicationsToClient.add(c);
            
          }
        }
        return communicationsToClient;
      }
    }
    throw new ClientNotFoundException(key);
  }
  public void sendTextCommunication(Terminal from, String toKey, String msg) throws TerminalNotFoundException, TerminalOffException
  {
    Terminal destination = getTerminal(toKey);

    if(destination.isOff()) {
      subscribeFailedContact(from.getOwner(), destination.getOwner(), toKey);
      throw new TerminalOffException( toKey );
    }

    TextCommunication comm = new TextCommunication( getNextCommId(), from, destination,  msg);

    //calcula e actualiza o custo da comunicacao
    comm.updateCost( _pricingPlan );

    //regista a comunicação na network e nos terminais intervenientes
    registerCommunication( comm );

  }

  public void startInteractiveCommunication(Terminal from, String toKey, String type)
          throws TerminalNotFoundException, TerminalOffException, TerminalBusyException, TerminalIsSilentException, UnknownCommunicationType, UnsuportedCommunication, UnsuportedCommunicationAtOrigin, UnsuportedCommunicationAtDestination
  {

    if(!from.supportsCommunicationType(type)) {
      //se o terminal de origem não suportar interactive .. unsupportedAtOrigin
      throw new UnsuportedCommunicationAtOrigin( from.getId() );
    }

    Terminal destination = getTerminal(toKey);

    if(!destination.supportsCommunicationType(type)) {
      //se o terminal de destino não suportar interactive .. unsupportedAtDestination
      throw new UnsuportedCommunicationAtDestination( toKey );
    }
    if(destination.isOff()) {
      subscribeFailedContact(from.getOwner(), destination.getOwner(), toKey);
      throw new TerminalOffException( toKey );
    }
    if(destination.isBusy()) {
      subscribeFailedContact(from.getOwner(), destination.getOwner(), toKey);
      throw new TerminalBusyException( toKey );
    }
    if(destination.isSilent()) {
      subscribeFailedContact(from.getOwner(), destination.getOwner(), toKey);
      throw new TerminalIsSilentException( toKey );
    }
    if(from.getId().equals( toKey )) {
      //todo: o que fazer se o from e o to forem o mesmo?!?!
      throw new UnsuportedCommunication( toKey );
    }

    InteractiveCommunication comm = InteractiveCommunicationFactory.make(type, getNextCommId(), from, destination );

    //iniciar a comunicação interativa
    comm.start();

  }

  public double endInteractiveCommunication(Terminal terminal, int duration) {
    if(terminal.canEndCurrentCommunication()) {
      InteractiveCommunication comm = terminal.getOngoingCommunication();

      //terminar a comunicação interativa ativa e actualiza o custo
      double cost = comm.end( _pricingPlan , duration );

      //regista a comunicação na network e nos terminais intervenientes
      registerCommunication( comm );

      return cost;

    }
    return 0;
  }

  protected void subscribeFailedContact( Client from, Client to, String toTerminalKey) {
    to.subscribe(
       new NotificationSubscriber(from, toTerminalKey)
    );
  }

  protected void registerCommunication( Communication comm )
  {

    //adiciona à lista de comunicacoes da network
    _communicationList.add( comm );

    //adiciona à lista de comunicacoes efetuadas do terminal de origem
    comm.getOriginTerminal().registerMadeCommunication( comm );

    //adiciona à lista de comunicacoes recebidas do terminal de destino
    comm.getDestinationTerminal().addReceivedCommunication( comm );

  }
}