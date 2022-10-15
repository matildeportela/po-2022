package prr.core;

import java.util.HashSet;
import java.util.Set;

public class Network{
    private double _balance;
    private HashSet<Client> _clientList;
    private double _payment;
    private double _debt;
    
    public Network(){
        _clientList = new HashSet<Client> () ;
    }
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

    }
    


}
