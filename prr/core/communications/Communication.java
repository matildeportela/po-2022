package prr.core.communications;

import prr.core.Terminal;

enum CommunicationType{
    Video,
    Text,
    Voice
}

public abstract class Communication{
    private int _id;
    private Terminal _origin;
    private Terminal _destination;
    private boolean _ongoing;
    private CommunicationType _type;
    private float _communicationCost;

    public Communication (Terminal origin, Terminal destination, 
    boolean ongoing, CommunicationType type){
        _origin = origin;
        _destination = destination;
        _ongoing = ongoing;
        _type = type;
    }

    public void write (String sms){
        //todo
    }
    
    public float getCommunicationCost(){
        return _communicationCost;
    }
    
    public int getId(){
        return _id;
    }
    
    public Terminal getOrigin(){
        return _origin;
    }
    
    public Terminal getDestination(){
        return _destination;
    }

    public void setType(CommunicationType communicationType){
        _type = communicationType;
    }

    public CommunicationType getType(){
        return _type;
    }

    public String toString(){
        return ""; //TODO: falta fazer   
    }
}



