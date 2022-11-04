package prr.core;

class VoiceCommunication extends InteractiveCommunication{

    VoiceCommunication(int id, Terminal origin, Terminal destination) {
        super(id, origin, destination, CommunicationType.VOICE);
    }

    @Override
    protected double computeCost(Plan plan) {
        return plan.computeVoiceCost(getClient(), this);
    }

    
    
}
