package projeto;
import java.util.HashSet;


public class Terminal{
    private String _id;
    private double _payment;
    private double _debt;
    private HashSet<Terminal> _friends;
    private 


    public Terminal(String id){
        _id = id;
        _payment = 0;
        _debt = 0;
        _friends = new HashSet<Terminal>();

    }
    public double getTerminalPayments(){
        return _payment;

    }
    public double getTerminalDebs(){
        return _debt;
    }
    public double getTerminalBalance(){
        return _payment - _debt;
    }
    public boolean isOff(){

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
}
