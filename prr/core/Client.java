package prr.core;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

enum ClientType{
    NORMAL,
    GOLD,
    PLATINUM
}

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private String _key;
    private String _name;
    private int _fiscalNumber;
    private double _payment;
    private double _debt;
    private String _notifications;
    private double _balance;
    private List<Terminal> _terminalList;
    private ClientType _type;
    
    
    public Client(String key, String name, int fiscalNumber){
        _key = key;
        _name = name;
        _fiscalNumber= fiscalNumber;
        _type = ClientType.NORMAL;
        _notifications = "YES";
        _terminalList = new ArrayList<Terminal>();
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

    public void addTerminal(Terminal t){
        _terminalList.add(t);
    }

    public List<Terminal> getTerminalList(){
        return _terminalList;
    }

    public int getActiveTerminalsCount(){
        int n = 0;

        for(Terminal i : getTerminalList()){
            if(!i.isOff()){
                n++;
            }
        }
        return n;
    }
    
    public String getNotification(){
        return _notifications;
    }

    

    public String toString(){
        
        String str =
            "CLIENT"+"|"+
            getKey()+"|"+
            getName()+"|"+ 
            getFiscalNumber()+ "|" + 
            getType() + "|" + 
            getNotification()+ "|"+
            getActiveTerminalsCount()+"|"+
            Math.round(getClientPayment())+"|"+
            Math.round(getClientDebt());

        return str;
    }
    
}
