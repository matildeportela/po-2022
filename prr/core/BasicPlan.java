package prr.core;

public class BasicPlan extends Plan {
    
    
    public double computeTextCost(Client c, TextCommunication communication){
        int textLength = communication.getSize();
        double cost = 0;
        switch(c.getType()){
            case NORMAL:
            if(textLength < 50){
                cost = 10;
            }
            else if(textLength >= 50 && textLength < 100){
                cost = 16;
            }
            else{
                cost = 2 * textLength;
            }
            case GOLD:
            if(textLength < 50){
                cost = 10;
            }
            else if(textLength >= 50 && textLength < 100){
                cost = 10;
            }
            else{
                cost = 2 * textLength;
            }
            case PLATINUM:
            if(textLength < 50){
                cost = 0;
            }
            else if(textLength >= 50 && textLength < 100){
                cost = 4;
            }
            else{
                cost = 4;
            }

        }
        return cost;
    }

    public double computeVoiceCost(Client c, VoiceCommunication communication){
        double voiceDuration = communication.getVoiceDuration() / 60;
        double cost = 0;
        switch(c.getType()){
            case NORMAL:
            cost = 20 * voiceDuration;
            
            case GOLD:
            cost = 10 * voiceDuration;
            
            case PLATINUM:
            cost = 10 * voiceDuration;
            

        }
        return cost;
        
    }

    public double computeVideoCost(Client c, VideoCommunication communication){
        double videoDuration = communication.getDuration() / 60;
        double cost = 0;
        switch(c.getType()){
            case NORMAL:
            cost = 30 * videoDuration;
            
            case GOLD:
            cost = 20 * videoDuration;
            
            case PLATINUM:
            cost = 10 * videoDuration;
            

        }
        return cost;
    }
}
