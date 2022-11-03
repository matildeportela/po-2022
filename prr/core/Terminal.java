package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.*;
enum TerminalState{
  IDLE,
  OFF,
  SILENCE,
  OCCUPIED,
  BUSY,
}

enum TerminalType{
  BASIC,
  FANCY
}

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable, Comparable<Terminal>  {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private String _id;
  private long _payment;
  private long _debt;
  private Client _owner;
  private List<Terminal> _friends;
  private TerminalState _state;
  private TerminalType _type;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;

  private InteractiveCommunication _ongoingCommunication; //todo??


  public Terminal(String id, Client owner, TerminalType type){
    _id = id;
    _owner = owner;
    _payment = 0;
    _debt = 0;
    _friends = new ArrayList<>();
    _state = TerminalState.IDLE;
    _type = type;
    _receivedCommunications = new ArrayList<Communication>();
    _madeCommunications = new ArrayList<Communication>();
  }
  
  public long getTerminalPayments(){
    return _payment;
    
  }

  public void triggerStateChangeEvent( TerminalState fromState, TerminalState toState ) {
      
    if(fromState == TerminalState.BUSY && toState == TerminalState.IDLE) {
      getOwner().notifySubscribers(this, NotificationType.B2I);
    }

    else if(fromState == TerminalState.SILENCE && toState == TerminalState.IDLE) {
      getOwner().notifySubscribers(this, NotificationType.S2I);
    }

    else if(fromState == TerminalState.OFF && toState == TerminalState.IDLE) {
      getOwner().notifySubscribers(this, NotificationType.O2I);

    }
    else if(fromState == TerminalState.OFF && toState == TerminalState.SILENCE) {
      getOwner().notifySubscribers(this, NotificationType.O2S);

    }
  }
  

  public void setOnSilent() {
    if (_state != TerminalState.OFF) {//todo verificar a regra de mudança de estado do terminal
      TerminalState fromState = _state;
      _state = TerminalState.SILENCE;
      triggerStateChangeEvent(fromState, _state);
    }
  } 
  public void turnOffSilent() {
    if (_state == TerminalState.SILENCE) {
      TerminalState fromState = _state;
      _state = TerminalState.IDLE;
      triggerStateChangeEvent(fromState, _state);
    }
  } 
  public void turnOff() {
    if (_state != TerminalState.OCCUPIED){
      _state = TerminalState.OFF;
    }
  }
  public void turnOn(){
    if (_state == TerminalState.OFF){
      TerminalState fromState = _state;
      _state = TerminalState.IDLE;
      triggerStateChangeEvent(fromState, _state);
      
    }
  }
  public void endOngoingCommunication(){
      TerminalState fromState = _state;
      resetOngoingCommunication();
      _state = TerminalState.IDLE;
      triggerStateChangeEvent(fromState, _state);
  }

  public void startOngoingCommunication(InteractiveCommunication comm ){
    
      TerminalState fromState = _state;
      setOngoingCommunication(comm);
      _state = TerminalState.BUSY;
      triggerStateChangeEvent(fromState, _state);
  }

  public long getTerminalDebts(){
    return _debt;
  }

  public long getTerminalBalance(){
    return _payment - _debt;
  }

  public InteractiveCommunication getOngoingCommunication() {
    return _ongoingCommunication;
  }
  public void setOngoingCommunication(InteractiveCommunication comm ) {
    _ongoingCommunication = comm;
  }

  public void resetOngoingCommunication() {
    _ongoingCommunication = null;   //todo: usar padrão de null_object?!?!
  }

  public boolean hasOngoingCommunication() {
    return _ongoingCommunication != null;
  }


  public boolean isOff(){
    return (_state == TerminalState.OFF);
  }
  public boolean isOn(){
    return (_state != TerminalState.OFF);
  }
  public boolean isSilent(){
    return (_state == TerminalState.SILENCE);
  }

  public boolean isBusy() {
    return hasOngoingCommunication();
  }

  public boolean isActive(){
    return (_madeCommunications.size() > 0 || _receivedCommunications.size() >0);
  }
  public boolean isFriend(Terminal t1){
    for(Terminal t2 : _friends){
      if(t2.getId() == t1.getId()){
        return true;
      }


    }
    return false;
  }
  public String getId(){
    return _id;
  }

  public Client getOwner(){
    return _owner;
  }

  public List<Terminal> getFriends(){
    return _friends;
  }

  public TerminalState getTerminalState() {
    if(isBusy()) return TerminalState.BUSY;
    return _state;
  }

  public TerminalType getType(){
    return _type;
  }

  public void addFriend(Terminal f) throws DuplicateTerminalException{
    
    if(_id.equals(f.getId())) {
      //não pode ser friend dele próprio
      throw new DuplicateTerminalException(f.getId());
    }

    for(Terminal t : _friends){
      //não pode ser friend de um friend já existente na lista de friends
      if (t.getId().equals(f.getId()) ){
        throw new DuplicateTerminalException(f.getId());
      }
    }

    _friends.add(f);
  }

  public void removeFriendById(String friendId ) throws UnknownTerminalKeyException {
    boolean didRemove = false;
    for (Terminal t: _friends){
      
      if (t.getId().equals(friendId)){
        _friends.remove(t);
        didRemove = true;
        break;
      }
    }

    if (!didRemove) {
      throw new UnknownTerminalKeyException(friendId);
    }
  }

  public void removeFriend(Terminal f) throws UnknownTerminalKeyException {
    boolean didRemove = false;
    for (Terminal t: _friends){
      if (t.getId().equals(f.getId())){
        _friends.remove(f);
        didRemove = true;
        break;
      }
    }
    if (!didRemove) {
      throw new UnknownTerminalKeyException(f.getId());
    }
  }
  
  public List<String> getSortedFriendsListIds(){
    ArrayList<String> orderedList = new ArrayList<String>();
    Collections.sort(_friends);
    for (Terminal t : _friends){
      String order = t.getId();
      orderedList.add(order);
    }
    return orderedList;
  }

  public void registerMadeCommunication( Communication comm ) {

    //acrescenta a comm à lista de madeCommunications
    addMadeCommunication(comm);

    //atualiza os saldos
    addDebt( comm.getCost() ); //todo: confirmar se é só isto
  }

  public void addMadeCommunication( Communication comm ) {
    _madeCommunications.add(comm);
  }

  public void addReceivedCommunication( Communication comm ) {
    _receivedCommunications.add(comm);
  }

  public void addDebt( double value ) {
    _debt += value;
  }

  public int compareTo(Terminal t) {
    return Integer.parseInt(_id) - Integer.parseInt(t.getId());
  }
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return isBusy() && _ongoingCommunication.getOriginTerminal().getId().equals(this.getId());
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return !isOff() && !isBusy();
  }


  public String toString() {

    String terminalString = 
    getType() +"|"+
    getId() +"|"+
    getOwner().getKey() +"|"+
    getTerminalState() +"|"+
    Math.round(getTerminalPayments()) +"|"+
    Math.round(getTerminalDebts());


    if(_friends.size() > 0) {
      terminalString += "|" + String.join(",", getSortedFriendsListIds());
    }

    return terminalString;
  }


}
