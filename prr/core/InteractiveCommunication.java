package prr.core;

abstract public class InteractiveCommunication extends Communication{
    /* Duração da comunicação em minutos */
    private int _communicationDuration;
    private boolean _isOngoing;
    
    public InteractiveCommunication(int id, Terminal origin, Terminal destination, CommunicationType type){
        super(id, origin, destination, type);
    }
    
    public int getSize(){
        return _communicationDuration;
    }
    public void setDuration( int durationInMinutes ) {
        _communicationDuration = durationInMinutes;
    }

    @Override
    public boolean isOngoingCommunication() {
        return _isOngoing;
    }

    public void start() {
        getOriginTerminal().setOngoingCommunication( this );
        getDestinationTerminal().setOngoingCommunication( this );
        _isOngoing = true;
    }
    public void end() {
        getOriginTerminal().resetOngoingCommunication();
        getDestinationTerminal().resetOngoingCommunication();
        _isOngoing = false;
    }
}
