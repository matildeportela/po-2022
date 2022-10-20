package prr.core;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
enum ClientType{
    NORMAL,
    GOLD,
    PLATINUM
}

public class Client {
    private String _key;
    private String _name;
    private int _fiscalNumber;
    private double _payment;
    private double _debt;
    private String _notifications;
    private double _balance;
    private List<Terminal> _terminalList;
    private ClientType _type;
    private String _client;
    private List<Terminal> _unactiveTerminals;
    
    
    public Client(String key, String name, int fiscalNumber){
        _key = key;
        _name = name;
        _fiscalNumber= fiscalNumber;
        _type = ClientType.NORMAL;
        _notifications = "Yes";
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
        _balance = _payment - _debt;
        return _balance;
    }

    public ClientType getType(){
        return _type;
    }

    public boolean addTerminal(Terminal t){
        _terminalList = new ArrayList<Terminal>();
        _terminalList.add(t);
        return true;
    }

    public List<Terminal> getTerminalList(){
        return _terminalList;
    }
    public List<Terminal> getUnactiveTerminals(){
        _unactiveTerminals = new ArrayList<Terminal> ();
        for(Terminal i : _terminalList){
            if(i.isOff()){
                _unactiveTerminals.add(i);
            }

        }
        return _unactiveTerminals;
    }
    public int getActiveTerminals(){
        int _activeTerminals;
        
        _activeTerminals = _terminalList.size() - _unactiveTerminals.size();
        return _activeTerminals;
    }
    public String getNotification(){
        return _notifications;
    }


    public String getClientString(){
        
        _client = ("CLIENT"+"|"+getKey()+"|"+getName()+"|"+ getFiscalNumber()+ "|" + getType() + "|" + getNotification()+ "|"+getActiveTerminals()+"|"+ getClientPayment()+"|"+ getClientDebt());
       

        return _client;
    }
    
}
