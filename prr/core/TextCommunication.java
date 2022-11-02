package prr.core;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(int id, Terminal origin, Terminal destination, String message){
        super(id, origin, destination, CommunicationType.TEXT);
        setMessage(message);
    }
    public void setMessage(String message){
        _message = message;
        
    }
    public String getMessage(){
        return _message;
    }

    @Override
    public double computeCost(Plan plan) {
        //todo: falta ver se é amigo e aplicar os 50% de desconto
        return plan.computeTextCost(getClient(), this);
    }

    public int getSize(){
        return _message.length();
    }
    
}
