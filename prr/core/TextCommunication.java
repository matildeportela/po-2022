package prr.core;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.TEXT);
    }

    @Override
    public double computeCost(Plan plan){
        //plan.computeTextCost(_origin._owner, this);
        return 0; //todo
    }

    public int getSize(){
        return _message.length();
    }
    
}
