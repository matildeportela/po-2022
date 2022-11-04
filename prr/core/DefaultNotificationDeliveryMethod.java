package prr.core;

import java.io.Serializable;

public class DefaultNotificationDeliveryMethod implements NotificationDeliveryMethod, Serializable {

    Client _destinationClient;

    DefaultNotificationDeliveryMethod(Client destinationClient) {
        _destinationClient = destinationClient;
    }

    @Override
    public void deliver(Notification notification) {
        _destinationClient.addToNotificationList(notification);
    }
}
