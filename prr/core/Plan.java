package prr.core;

abstract class Plan {

    protected abstract double computeTextCost(Client c, TextCommunication communication);

    protected abstract double computeVoiceCost(Client c, VoiceCommunication communication);

    protected abstract double computeVideoCost(Client c, VideoCommunication communication);

  
}
