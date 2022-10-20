package prr.core;

import java.io.Serializable;
import java.util.HashSet;
enum TerminalState{
  IDLE,
  OFF,
  SILENT,
  OCCUPIED
}
// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  private String _id;
  private double _payment;
  private double _debt;
  private HashSet<Terminal> _friends;
  private TerminalState _state;

  // FIXME define contructor(s)
  public Terminal(String id){
    _id = id;
    _payment = 0;
    _debt = 0;
    _friends = new HashSet<Terminal>();
    _state = TerminalState.IDLE;
  }
  
  // FIXME define methods
  public double getTerminalPayments(){
    return _payment;
    
  }

  public void setOnSilent() {
    //todo...
  }
  
  public void turnOff() {
    //todo...
  }

  public double getTerminalDebs(){
    return _debt;
  }

  public double getTerminalBalance(){
    return _payment - _debt;
  }

  public boolean isOff(){
    return (_state == TerminalState.OFF);
  }

  public String getId(){
    return _id;
  }

  public boolean addFriend(Terminal f){
    for(Terminal t : _friends){
      if (t.getId() != f.getId()){
        _friends.add(f);
        return true;
      }
      
    }
    return false;
  }
  
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
    return true;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
    return true;
  }
}
