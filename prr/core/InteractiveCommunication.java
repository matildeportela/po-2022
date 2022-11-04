package prr.core;

abstract class InteractiveCommunication extends Communication{
    /* Duração da comunicação em minutos */
    private int _communicationDuration;
    private boolean _isOngoing;

    protected InteractiveCommunication(int id, Terminal origin, Terminal destination, CommunicationType type){
        super(id, origin, destination, type);
    }
    
    int getSize(){
        return _communicationDuration;
    }
    protected void setDuration( int durationInMinutes ) {
        _communicationDuration = durationInMinutes;
    }

    @Override
    protected boolean isOngoingCommunication() {
        return _isOngoing;
    }

    void start() {
        getOriginTerminal().startOngoingCommunication( this );
        getDestinationTerminal().startOngoingCommunication( this );
        _isOngoing = true;
    }
    double end( Plan plan, int duration ) {
        getOriginTerminal().endOngoingCommunication();
        getDestinationTerminal().endOngoingCommunication();

        //calcula e actualiza o custo da comunicacao
        setDuration( duration );
        updateCost( plan );
        _isOngoing = false;

        return getCost();
    }
}
