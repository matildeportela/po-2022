package prr.core;

enum CommunicationType{
    VIDEO,
    TEXT,
    VOICE
}

abstract class Communication {
    private int _id;
    private Terminal _origin;
    private Terminal _destination;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private CommunicationType _type;
          
    public Communication(int id, Terminal origin, Terminal destination, boolean isOngoing, CommunicationType type){
        _id = id;
        _origin = origin;
        _destination = destination;
        _isOngoing = isOngoing;
        _type = type;
    }

    public Communication(int id, Terminal origin, Terminal destination, CommunicationType type){
        _id = id;
        _origin = origin;
        _destination = destination;
        _isOngoing = false;
        _type = type;
    }

    public int getId(){
        return _id;
    }

    public Terminal getOriginTerminal(){
        return _origin;
    }

    public Terminal getDestinationTerminal(){
        return _destination;
    }

    public CommunicationType getType() {
        return _type;
    }

    public boolean isInteractive() {
        return (getType() == CommunicationType.VOICE || getType() == CommunicationType.VIDEO);
    }

    public boolean isText() {
        return (getType() == CommunicationType.TEXT);
    }


    public String getStatus() {
        if(isOngoingCommunication()) return "ONGOING";
        return "FINISHED";
    }

    public double getCost() {
        return _cost;
    }

    public void setCost( double cost ) {
        _cost = cost;
    }

    public void updateCost( Plan plan ) {
        setCost( computeCost(plan) );
    }
    public boolean isBetweenFriends(){
        return getOriginTerminal().isFriend(getDestinationTerminal());

    }


    abstract public double computeCost(Plan plan);

    abstract public int getSize();

    abstract public boolean isOngoingCommunication();

    public String toString(){
        String str =
                getType()+"|"+
                getId()+"|"+
                getOriginTerminal().getId()+ "|" +
                getDestinationTerminal().getId()+ "|" +
                getSize() + "|" +
                Math.round(getCost()) + "|"+
                getStatus();

        return str;
    }

     public Client getClient() {
        return getOriginTerminal().getOwner();
    }

}