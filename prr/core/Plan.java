package prr.core;

abstract public class Plan {
    
    private String _name;

    public abstract double computeTextCost(Client c, TextCommunication communication);

    public abstract double computeVoiceCost(Client c, VoiceCommunication communication);

    public abstract double computeVideoCost(Client c, VideoCommunication communication);

  
}
