package prr.core;

public class VideoCommunication extends InteractiveCommunication{

    public VideoCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.VIDEO);
    }
    
    @Override
    public double computeCost(Plan plan){
        //plan.compute...
        return 0;  //todo
    }
}
