package prr.core;
import java.io.Serializable;
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
    private double _balance;
    private List<Terminal> _terminalList;
    private ClientType _type;
    private List<SubscriberInterface> _subscribersList;
    private List<Notification> _notificationsList;
    
    
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
    public boolean hasDebt(){
        return (_balance < 0);
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
        _balance = _payment - _debt;
        return _balance;
    }

    public ClientType getType(){
        return _type;
    }

    public void addTerminal(Terminal t){
        _terminalList.add(t);
    }

    public List<Terminal> getTerminalList(){
        return _terminalList;
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
        return _notificationsList;
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
