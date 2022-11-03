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
        getOriginTerminal().startOngoingCommunication( this );
        getDestinationTerminal().startOngoingCommunication( this );
        _isOngoing = true;
    }
    public double end( Plan plan, int duration ) {
        getOriginTerminal().endOngoingCommunication();
        getDestinationTerminal().endOngoingCommunication();

        //calcula e actualiza o custo da comunicacao
        setDuration( duration );
        updateCost( plan );
        _isOngoing = false;

        return getCost();
    }
}
