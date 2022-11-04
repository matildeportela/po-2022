package prr.core;

interface Subscriber {
    public void notify(Notification notification);

    void setDeliveryMethod(NotificationDeliveryMethod deliveryMethod);
    
}
