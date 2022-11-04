package prr.core;

class VideoCommunication extends InteractiveCommunication{

    public VideoCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.VIDEO);
    }
    
    @Override
    public double computeCost(Plan plan) {
        //todo: falta ver se é amigo e aplicar os 50% de desconto
        return plan.computeVideoCost(getClient(), this);
    }

    
}

