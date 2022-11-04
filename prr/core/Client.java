package prr.core;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

enum ClientType{
    NORMAL,
    GOLD,
    PLATINUM
}

public class Client implements Serializable, Comparable<Client>, Subscribable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private String _key;
    private String _name;
    private int _fiscalNumber;
    private boolean _notifications;
    private List<Terminal> _terminalList;
    private ClientType _type;
    private List<Subscriber> _subscribersList;
    private List<Notification> _notificationsList;
    private int _consecutiveVideoCalls;
    private int _consecutiveTextMessages;
    private CommunicationType _lastCommType;



    public Client(String key, String name, int fiscalNumber){
        _key = key;
        _name = name;
        _fiscalNumber= fiscalNumber;
        _type = ClientType.NORMAL;
        _notifications = true;
        _terminalList = new ArrayList<Terminal>();
        _notificationsList = new ArrayList<Notification>();
        _subscribersList = new ArrayList<Subscriber>();
        _lastCommType = null;
    }

    public long getClientPayment(){
        long clientPayments = 0;
        for(Terminal t: _terminalList){
            clientPayments += t.getTerminalPayments();

        }
        return clientPayments;
    }

    public long getClientDebt(){
        long clientDebts = 0;
        for(Terminal t: _terminalList){
            clientDebts += t.getTerminalDebts();

        }
        return clientDebts;
    }
        
    public String getKey(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    public int getFiscalNumber(){
        return _fiscalNumber;
    }

    public long getBalance(){
        return getClientPayment() - getClientDebt();
        
    }

    public ClientType getType(){
        return _type;
    }

    public boolean hasDebt(){
        return (getBalance() < 0);
    }

    public int getNumOfConsecutiveVideoCalls(){
        return _consecutiveVideoCalls + 1;
    }

    public int getNumOfConsecutiveTextMessages(){
        return _consecutiveTextMessages + 1;
    }


    public void updateConsecutiveCalls( CommunicationType commType ) {

        if( commType == _lastCommType && commType == CommunicationType.VIDEO ) {
            _consecutiveVideoCalls++;
        } else {
            _consecutiveVideoCalls = 0;
        }

        if( commType == _lastCommType && commType == CommunicationType.TEXT ) {
            _consecutiveTextMessages++;
        } else {
            _consecutiveTextMessages = 0;
        }

        _lastCommType = commType;

    }

    public void reEvaluateClientPlan(){
        long balance = getBalance();

        if (_type == ClientType.NORMAL){
            //se o saldo do cliente apos o pagamento eh maior que 500 creditos
            if (balance > 500){
                _type = ClientType.GOLD;
            }
        } else if (_type == ClientType.GOLD){
            //se o saldo do cliente apos realizar uma comunicacao eh negativo
            if (balance < 0){
                _type = ClientType.NORMAL;
            }
            // se o cliente realizou 5 comm de video consec e nao tem saldo negativo
            else if (getNumOfConsecutiveVideoCalls() >= 5) {
                _type = ClientType.PLATINUM;
            }
        } else if (_type == ClientType.PLATINUM){
            //se o saldo apos a comunicacao eh negativo
            if (balance < 0){
                _type = ClientType.NORMAL;
            }
            //se o cliente realizou 2 comm de texto consec e nao tem saldo negativo
            else if (getNumOfConsecutiveTextMessages() >= 2){
                _type = ClientType.GOLD;
            }
        }

    }

    
    public void addTerminal(Terminal t){
        _terminalList.add(t);
    }

    public List<Terminal> getTerminalList(){
        return new ArrayList<>( _terminalList );
    }

    public int getActiveTerminalsCount(){
        int n = 0;

        for(Terminal i : getTerminalList()){
            if(i.isActive()){
                n++;
            }
        }
        return n;
    }

    public int getTerminalsCount(){
        return getTerminalList().size();
    }
    
    public boolean hasNotificationsEnabled(){
        return _notifications;
    }

    public void enableNotifications() {
        _notifications = true;
    }

    public void disableNotifications() {
        _notifications = false;
    }

    public String getNotificationStatus() {
        if(hasNotificationsEnabled()) return "YES";
        return "NO";
    }


    // implements Subscribable
    @Override
    public void subscribe(Subscriber s){
        if( hasNotificationsEnabled() ) {
            _subscribersList.add(s);
        }
    }

    @Override
    public void sendNotification(Notification notification) {
        for(Subscriber s : _subscribersList){
            s.notify( notification );
        }
    }


    public void addToNotificationList(Notification notification) {
        for(Notification n : _notificationsList) {
            if(n.toString().equals(notification.toString())) {
                //se já existir uma notificação igual... sai sem adicionar
                return;
            }
        }
        _notificationsList.add(notification);
    }

    public void deleteNotifications(){
        _notificationsList = new ArrayList<Notification>();
    }

    public List<Notification> getNotificationsList() {
        return new ArrayList<>( _notificationsList );
    }



    public int compareTo(Client c) {
        return _key.toLowerCase().compareTo(c.getKey().toLowerCase());
    }

    public String toString(){

        String str =
            "CLIENT"+"|"+
            getKey()+"|"+
            getName()+"|"+ 
            getFiscalNumber()+ "|" + 
            getType() + "|" + 
            getNotificationStatus()+ "|"+
            getTerminalsCount()+"|"+
            Math.round(getClientPayment())+"|"+
            Math.round(getClientDebt());

        return str;
    }



}
