package prr.core;
import java.io.Serializable;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.List;
import java.util.ArrayList;

enum ClientType{
    NORMAL,
    GOLD,
    PLATINUM
}

public class Client implements Serializable, Comparable<Client> {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private String _key;
    private String _name;
    private int _fiscalNumber;
    private double _payment;
    private double _debt;
    private boolean _notifications;
    private List<Terminal> _terminalList;
    private ClientType _type;
    private List<SubscriberInterface> _subscribersList;
    private List<Notification> _notificationsList;
    private int _consecutiveVideoCalls;
    private int _consectiveTextMessages;
    
    
    public Client(String key, String name, int fiscalNumber){
        _key = key;
        _name = name;
        _fiscalNumber= fiscalNumber;
        _type = ClientType.NORMAL;
        _notifications = true;
        _terminalList = new ArrayList<Terminal>();
        _notificationsList = new ArrayList<Notification>();
        _subscribersList = new ArrayList<SubscriberInterface>();
    }

    public double getClientPayment(){
        return _payment;
    }

    public double getClientDebt(){
        return _debt;
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

    public double getBalance(){
        return _payment - _debt;
    }

    public ClientType getType(){
        return _type;
    }

    public boolean hasDebt(){
        return (getBalance() < 0);
    }

    public int getNumOfConsecutiveVideoCalls(){
        return _consecutiveVideoCalls;
    }

    public int getNumOfConsecutiveTextMessages(){
        return _consectiveTextMessages;
    }

    public int incrementVideoCallsCount(){
        return _consecutiveVideoCalls++;
    }

    public int incrementTextMessagesCount(){
        return _consectiveTextMessages++;
    }

    public int resetVideoCallsCount(){
        return _consecutiveVideoCalls = 0;
    }

    public int resetTextMessagesCount(){
        return _consectiveTextMessages = 0;
    } 

    public void reEvaluateClientPlan(){
        if (_type == ClientType.NORMAL){
            //se o saldo do cliente apos o pagamento eh maior que 500 creditos
            if (getBalance() > 500){ 
                _type = ClientType.GOLD;
            }
        } else if (_type == ClientType.GOLD){
            //se o saldo do cliente apos realizar uma comunicacao eh negativo
            if (getBalance() < 0){
                _type = ClientType.NORMAL;
            }
            // se o cliente realizou 5 comm de video consec e nao tem saldo negativo
            if ((getNumOfConsecutiveVideoCalls() > 5) && getBalance() > 0){
                _type = ClientType.PLATINUM;
            }
        } else if (_type == ClientType.PLATINUM){
            //se o saldo apos a comunicacao eh negativo
            if (getBalance() < 0){
                _type = ClientType.NORMAL;
            }
            //se o cliente realizou 2 comm de texto consec e nao tem saldo negativo
            if ((getNumOfConsecutiveTextMessages() > 2) && getBalance() > 0){
                _type = ClientType.GOLD;
            }
        } else{
            //todo laca Exeption?
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
    public void subscribe(SubscriberInterface s){
        if(hasNotificationsEnabled()) {
            _subscribersList.add(s);//todo verificar se s ja é subscritor;
        }
       
    }
    public void notifySubscribers(Terminal eventTerminal, NotificationType event){
        for(SubscriberInterface s : _subscribersList){
            s.createNotification(eventTerminal.getId(), event);
        }
    }
    public void addNotification(String terminalKey, NotificationType type){
        Notification newNotification = new Notification(type, terminalKey);

        for(Notification n : _notificationsList) {
            if(n.toString().equals(newNotification.toString())) {
                //se já existir uma notificação igual... sai sem adicionar
                return;
            }
        }
        _notificationsList.add(newNotification);
    }


    public int compareTo(Client c) {
        return _key.toLowerCase().compareTo(c.getKey().toLowerCase());
    }

    public List<Notification> getNotificationsList() {
        return new ArrayList<>( _notificationsList );
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
    public void deleteNotifications(){
        _notificationsList = new ArrayList<Notification>();
    }

}
