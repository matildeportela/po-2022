package prr.core;

public abstract class Plan
{
    protected PlanType _planType;

    public PlanType getPlanType(){
        return _planType;
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
    Normal,
    Gold,
    Platinum
}
