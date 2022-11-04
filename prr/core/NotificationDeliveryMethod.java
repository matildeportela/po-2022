package prr.core;

import java.io.Serializable;

interface NotificationDeliveryMethod extends Serializable {

    void deliver(Notification notification);

}
