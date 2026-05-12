package src.classes.agenda;

import src.classes.reservation.*;
import src.classes.user.User;
import src.observers.AgendaObserver;

public class UserNotifier implements AgendaObserver {
    private final User user;

    public UserNotifier(User user) {
        this.user = user;
    }

    @Override
    public void update(ReservationEvent event) {
        switch (event.getType()) {
            case CREATED:
                notifyCreation(event);
                break;
            case UPDATED:
                notifyUpdate(event);
                break;
            case CANCELLED:
                notifyCancellation(event);
                break;
        }
    }

    public void notifyCreation(ReservationEvent event) {
        System.out.println("Notification to " + user.getName() + 
        ": reservation created for " + event.getReservation());
    }

    public void notifyUpdate(ReservationEvent event) {
        System.out.println("Notification to " + user.getName() + 
        ": reservation updated for " + event.getReservation());
    }

    public void notifyCancellation(ReservationEvent event) {
        System.out.println("Notification to " + user.getName() + 
        ": reservation cancelled for " + event.getReservation());
    }
}
