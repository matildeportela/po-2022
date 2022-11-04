package prr.core;

abstract class Plan {

    public abstract double computeTextCost(Client c, TextCommunication communication);

    public abstract double computeVoiceCost(Client c, VoiceCommunication communication);

    public abstract double computeVideoCost(Client c, VideoCommunication communication);

  
}
