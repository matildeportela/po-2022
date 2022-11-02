package prr.core;

abstract public class InteractiveCommunication extends Communication{
    /* Duração da comunicação em minutos */
    private int _communicationDuration;
    
    public InteractiveCommunication(int id, Terminal origin, Terminal destination, CommunicationType type){
        super(id, origin, destination, type);
    }
    
    public int getSize(){
        return _communicationDuration;
    }
    public void setDuration( int durationInMinutes ) {
        _communicationDuration = durationInMinutes;
    }

}
