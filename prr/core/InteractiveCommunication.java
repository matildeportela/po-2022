package prr.core;

abstract public class InteractiveCommunication extends Communication{
    private int _communicationDuration;
    
    public InteractiveCommunication(int id, Terminal origin, Terminal destination, CommunicationType type){
        super(id, origin, destination, type);
    }
    
    public int getSize(){
        return _communicationDuration;
    }
}
