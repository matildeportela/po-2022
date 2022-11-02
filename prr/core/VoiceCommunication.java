package prr.core;

public class VoiceCommunication extends InteractiveCommunication{

    public VoiceCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.VOICE);
    }

    @Override
    public double computeCost(Plan plan){
        return 0;//todo
    }
    
}
