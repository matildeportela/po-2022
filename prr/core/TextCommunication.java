package prr.core;

class TextCommunication extends Communication {
    private String _message;

    TextCommunication(int id, Terminal origin, Terminal destination, String message){
        super(id, origin, destination, CommunicationType.TEXT);
        setMessage(message);
    }

    private void setMessage(String message){
        _message = message;
        
    }
    public String getMessage(){
        return _message;
    }

    @Override
    protected double computeCost(Plan plan) {
        return plan.computeTextCost(getClient(), this);
    }

    int getSize(){
        return _message.length();
    }

    @Override
    protected boolean isOngoingCommunication() {
        return false;
    }
}
