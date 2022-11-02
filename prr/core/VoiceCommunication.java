package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
    private int _voiceDuration;
    public VoiceCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.VOICE);
    }
    public void setVoiceDuration(int voiceDuration){
        _voiceDuration = voiceDuration;
    }
    public int getVoiceDuration(){
        return _voiceDuration;
    }

    @Override
    public double computeCost(Plan plan){
        return plan.computeVoiceCost(getClient(), this);
    }

    
    
}
