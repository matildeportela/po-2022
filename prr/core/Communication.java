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

    Communication(int id, Terminal origin, Terminal destination, CommunicationType type){
        _id = id;
        _origin = origin;
        _destination = destination;
        _isOngoing = false;
        _isPaid = false;
        _type = type;
    }

    protected int getId(){
        return _id;
    }

    Terminal getOriginTerminal(){
        return _origin;
    }

    Terminal getDestinationTerminal(){
        return _destination;
    }

    protected CommunicationType getType() {
        return _type;
    }

    public boolean isPaid(){
        return _isPaid;
    }

    public boolean isOngoing(){
        return _isOngoing;
    }

    public boolean isInteractive() {
        return (getType() == CommunicationType.VOICE || getType() == CommunicationType.VIDEO);
    }

    public boolean isText() {
        return (getType() == CommunicationType.TEXT);
    }


    protected String getStatus() {
        if(isOngoingCommunication()) return "ONGOING";
        return "FINISHED";
    }

    void markAsPaid() {
        _isPaid = true;
    }

    double getCost() {
        return _cost;
    }

    protected void setCost( double cost ) {
        _cost = cost;
    }

    void updateCost( Plan plan ) {
        setCost( computeCost(plan) );
    }

    boolean isBetweenFriends(){
        return getOriginTerminal().isFriend(getDestinationTerminal());

    }


    abstract protected double computeCost(Plan plan);

    abstract int getSize();

    abstract protected boolean isOngoingCommunication();

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

     protected Client getClient() {
        return getOriginTerminal().getOwner();
    }

}