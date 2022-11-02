package prr.core;

public class TextCommunication extends Communication {
    private String _message;

    @Override
    public double computeCost(Plan plan){
        //todo
    }

    public int getSize(){
        return _message.length();
    }
    
}
