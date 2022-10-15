package prr.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Client {
    private String _key;
    private String _name;
    private int _fiscalNumber;
    private double _payment;
    private double _debt;
    private boolean _enableNotification;
    private double _balance;
    private Set<Terminal> _terminalList;
    
    public Client(String key, String name, int fiscalNumber){
        _key = key;
        _name = name;
        _fiscalNumber= fiscalNumber;
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

    public boolean addTerminal(Terminal t){
        _terminalList = new HashSet<Terminal>();
        _terminalList.add(t);

    }

    public Set<Terminal> getTerminalList(){
        return _terminalList;
    }

    
    
}
