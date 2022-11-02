package prr.core;

public class BasicPlan extends Plan {
    
    
    public double computeTextCost(Client c, TextCommunication communication){
        //que tipo de cliente é (PREMIUM, ...)
        
        //consoante o tipo.... retornar o preço da TextComm
        return 0; //todo
    }

    public double computeVoiceCost(Client c, VoiceCommunication communication){
        //que tipo de cliente é (PREMIUM, ...)
        
        //consoante o tipo.... retornar o preço da VoiceComm
        return 0; //todo
    }

    public double computeVideoCost(Client c, VideoCommunication communication){
        //que tipo de cliente é (PREMIUM, ...)
        
        //consoante o tipo.... retornar o preço da VideoComm
        return 0; //todo
    }
}
