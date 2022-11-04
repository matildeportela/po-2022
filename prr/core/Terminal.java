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
  private TerminalState _previousState;

  private InteractiveCommunication _ongoingCommunication; //todo??


  protected Terminal(String id, Client owner, TerminalType type){
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

  

  protected void triggerStateChangeEvent( TerminalState fromState, TerminalState toState ) {
      
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

  protected void changeStateTo(TerminalState toState) {
    TerminalState fromState = getState();
    _state = toState;
    triggerStateChangeEvent(fromState, toState);
  }
  

  public void setOnSilent() {
    changeStateTo(TerminalState.SILENCE);
  }

  public void turnOffSilent() {
    if (getState() == TerminalState.SILENCE) {
      changeStateTo(TerminalState.IDLE);
    }
  } 
  public void turnOff() {
    if (getState() != TerminalState.OCCUPIED){
      changeStateTo(TerminalState.OFF);
    }
  }
  public void turnOn(){
    changeStateTo(TerminalState.IDLE);
  }

  public void startOngoingCommunication(InteractiveCommunication comm ){
    storePreviousState( getState() );
    setOngoingCommunication(comm);
    changeStateTo( TerminalState.BUSY );
  }

  public void endOngoingCommunication(){
    resetOngoingCommunication();
    changeStateTo( getPreviousState() );
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
  protected void setOngoingCommunication(InteractiveCommunication comm ) {
    _ongoingCommunication = comm;
  }

  protected void resetOngoingCommunication() {
    _ongoingCommunication = null;   //todo: usar padrão de null_object?!?!
  }

  public boolean hasOngoingCommunication() {
    return _ongoingCommunication != null;
  }

  protected TerminalState getPreviousState(){
    return _previousState;
  }

  protected void storePreviousState(TerminalState previousState){
    _previousState = previousState;
  }

  public boolean isOff(){
    return (getState() == TerminalState.OFF);
  }
  public boolean isOn(){
    return (getState() != TerminalState.OFF);
  }
  public boolean isSilent(){
    return (getState() == TerminalState.SILENCE);
  }

  public boolean isIdle(){
    return (getState() == TerminalState.IDLE);
  }

  public boolean isBusy() {
    return (getState() == TerminalState.BUSY);
  }

  public boolean isActive(){
    return (_madeCommunications.size() > 0 || _receivedCommunications.size() > 0);
  }
  public boolean isFriend(Terminal t1){
    for(Terminal t2 : _friends){
      if(t2.getId() == t1.getId()){
        return true;
      }
    }
    return false;
  }

  public boolean supportsCommunicationType(String commType) {

    if(getType()==TerminalType.BASIC && commType.equals( "VIDEO" ) ) {
      return false;
    }

    return true;

  }

  public String getId(){
    return _id;
  }

  public Client getOwner(){
    return _owner;
  }

  public List<Terminal> getFriends(){
    return new ArrayList<>(_friends);
  }

  public TerminalState getState() {
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

  public void removeFriend( String friendId ) throws UnknownTerminalKeyException {
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
  
  protected List<String> getSortedFriendsListIds(){
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

    //reavaliar se o cliente que fez a comunicação muda de tipo
    getOwner().reEvaluateClientPlan();
  }

  protected void addMadeCommunication( Communication comm ) {
    _madeCommunications.add(comm);
  }
  public List<Communication> getMadeCommunication(){//todo verificar se é public;
    return _madeCommunications;
  }
  public List<Communication> getReceivedCommunication(){//todo verificar se é public;
    return _receivedCommunications;
  }  
  protected void addReceivedCommunication( Communication comm ) {
    
    _receivedCommunications.add(comm);
  }

  protected void addDebt( double value ) {
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
    getState() +"|"+
    Math.round(getTerminalPayments()) +"|"+
    Math.round(getTerminalDebts());


    if(_friends.size() > 0) {
      terminalString += "|" + String.join(",", getSortedFriendsListIds());
    }

    return terminalString;
  }


}
