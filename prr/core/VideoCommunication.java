package prr.core;

class VideoCommunication extends InteractiveCommunication{

    VideoCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.VIDEO);
    }
    
    @Override
    protected double computeCost(Plan plan) {
        return plan.computeVideoCost(getClient(), this);
    }

    
}

