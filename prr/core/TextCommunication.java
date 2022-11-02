package prr.core;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.TEXT);
    }
    public void setMessage(String message){
        _message = message;
        
    }
    public String getMessage(){
        return _message;
    }

    @Override
    public double computeCost(Plan plan){
        return plan.computeTextCost(getClient(), this);
    }

    public int getSize(){
        return _message.length();
    }
    
}
