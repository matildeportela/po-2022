package prr.core;

class NotificationSubscriber implements SubscriberInterface {
    private Client _notifyClient;
    private String _terminalKey;
    NotificationSubscriber(Client notifyClient, String terminalKey){
        _notifyClient = notifyClient;
        _terminalKey = terminalKey;
    }
    public void createNotification(String terminalKey, NotificationType type){
        //só adiciona notificacao no cliente se o terminal que gerou o evento coincidir com o terminalkey do subscriber
        if(terminalKey.equals(_terminalKey)) {
            _notifyClient.addNotification(_terminalKey, type);
        }
    }
    
}
