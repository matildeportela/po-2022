package prr.core;

public interface Subscribable {

        void subscribe(Subscriber receiver);

        void sendNotification(Notification notification);

}
