package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  private double _balance;
  private HashSet<Client> _clientList;
  private double _payment;
  private double _debt;


  // FIXME define contructor(s)
  public Network(){
      _clientList = new HashSet<Client> () ;
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

  public Set<Client> getClients(){
    return _clientList;
  }

  public Client getClient(String key){
    for(Client c : _clientList){
      if(key == c.getKey()){
        return c;
      }
    }
    return new Client("", "", 0); //TODO: o que é que retorna se não encontra cliente? lança
  }
  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
  }
}

