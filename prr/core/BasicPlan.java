package prr.core;

public class BasicPlan extends Plan {

    public double computeTextCost(Client c, TextCommunication communication){
        int textLength = communication.getSize();
        double cost = 0;
        switch(c.getType()){
            case NORMAL:
                if(textLength < 50) {
                    cost = 10;
                } else if(textLength >= 50 && textLength < 100) {
                    cost = 16;
                } else {
                    cost = 2 * textLength;
                }
                break;
            case GOLD:
                if(textLength < 50) {
                    cost = 10;
                } else if(textLength >= 50 && textLength < 100) {
                    cost = 10;
                } else {
                    cost = 2 * textLength;
                }
                break;
            case PLATINUM:
                if(textLength < 50) {
                    cost = 0;
                } else if(textLength >= 50 && textLength < 100) {
                    cost = 4;
                } else {
                    cost = 4;
                }
                break;
            default:
                //todo??? o que acontece se não for nenhum destes?!?!?
        }
        return cost;
    }

    public double computeVoiceCost(Client c, VoiceCommunication communication){
        int duration = communication.getSize();
        double cost = 0;
        switch(c.getType()){
            case NORMAL:
                cost = 20 * duration;
                break;
            case GOLD:
                cost = 10 * duration;
                break;
            case PLATINUM:
                cost = 10 * duration;
                break;
            default:
                //todo??? o que acontece se não for nenhum destes?!?!?
        }
        return cost;
        
    }

    public double computeVideoCost(Client c, VideoCommunication communication){
        int duration = communication.getSize();
        double cost = 0;
        switch(c.getType()){
            case NORMAL:
                cost = 30 * duration;
                break;
            case GOLD:
                cost = 20 * duration;
                break;
            case PLATINUM:
                cost = 10 * duration;
                break;
            default:
                //todo??? o que acontece se não for nenhum destes?!?!?
        }
        return cost;
    }
}
