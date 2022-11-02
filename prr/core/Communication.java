package prr.core;

enum CommunicationType{
    VIDEO,
    TEXT,
    VOICE
}

abstract public class Communication {
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

    public boolean isOngoingCommunication(){
        return _isOngoing;
    }

    public double computeCost(Plan plan){
        return 0; //todo
    }


    public int getSize(){
        return 0; //todo
    }


    public String toString(){
        
        return ""; //todo
    }

     public Client getClient() {
        return _origin.getOwner();
    } 
}