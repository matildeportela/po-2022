package prr.core;

class VoiceCommunication extends InteractiveCommunication{

    public VoiceCommunication(int id, Terminal origin, Terminal destination) {
        super(id, origin, destination, CommunicationType.VOICE);
    }

    @Override
    public double computeCost(Plan plan) {
        //todo: falta ver se é amigo e aplicar os 50% de desconto
        return plan.computeVoiceCost(getClient(), this);
    }

    
    
}
