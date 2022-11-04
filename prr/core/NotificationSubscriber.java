package prr.core;

import java.io.Serializable;

class NotificationSubscriber implements Subscriber, Serializable {
    private Client _notifyClient;
    private String _terminalKey;
    private NotificationDeliveryMethod _notificationDeliveryMethod;


    NotificationSubscriber(Client notifyClient, String terminalKey, NotificationDeliveryMethod deliveryMethod){
        _notifyClient = notifyClient;
        _terminalKey = terminalKey;
        setDeliveryMethod(deliveryMethod);
    }

    @Override
    public void notify(Notification notification){
        //só entrega a notificacao no cliente se o terminal que gerou o evento coincidir com o terminalkey do subscriber
        if(notification.getTerminalKey().equals(_terminalKey)) {
            _notificationDeliveryMethod.deliver( notification );
        }
    }

    @Override
    public void setDeliveryMethod(NotificationDeliveryMethod deliveryMethod) {
        _notificationDeliveryMethod = deliveryMethod;
    }

}
