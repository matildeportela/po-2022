package prr.core;

enum NotificationType{
    O2S,
    O2I,
    S2I,
    B2I
}

class Notification {
    private NotificationType _type;
    private String _terminalKey;
    public Notification(NotificationType type, String terminalKey){
        _type = type;
        _terminalKey = terminalKey;
    }
    public NotificationType getType(){
        return _type;
    }
    public String getTerminalKey(){
        return _terminalKey;
    }

    public String toString(){
        return   getType() + "|" + getTerminalKey() ;
    }
}
