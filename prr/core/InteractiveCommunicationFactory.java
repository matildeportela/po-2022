package prr.core;

import prr.core.exception.UnknownCommunicationType;

class InteractiveCommunicationFactory {
    public static InteractiveCommunication make(String type, int id, Terminal from, Terminal to) throws UnknownCommunicationType {
        InteractiveCommunication comm;
        switch (type) {
            case "VOICE":
                comm = new VoiceCommunication(id, from, to);
                break;
            case "VIDEO":
                comm = new VideoCommunication(id, from, to);
                break;
            default:
                throw new UnknownCommunicationType(type);
                //todo: trow exception unknown type
        }
        return comm;
    }
}
