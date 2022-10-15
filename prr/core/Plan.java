package prr.core;

public abstract class Plan{

    public PlanType getPlanType(){
        //todo
    }
    public void TextCost(String sms){
        //todo
    }
    public void getVideoCost(int duration){
        //todo
    }
    public void getVoiceCost(int duration){
        //todo
    }
}

enum PlanType{
    Normal
    Gold
    Platinum
}
