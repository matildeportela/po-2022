package prr.core;

public class VideoCommunication extends InteractiveCommunication{
    private int _videoDuration;

    public VideoCommunication(int id, Terminal origin, Terminal destination){
        super(id, origin, destination, CommunicationType.VIDEO);
    }
    public void setVoiceDuration(int videoDuration){
        _videoDuration = videoDuration;
    }
    
    @Override
    public double computeCost(Plan plan){
        return plan.computeVideoCost(getClient(), this);
    }

    public double getDuration(){
        return _videoDuration ;
    }
    
}

