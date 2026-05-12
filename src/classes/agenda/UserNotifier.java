package src.classes.agenda;

import src.classes.reservation.Reservation;
import src.classes.reservation.ReservationEvent;
import src.classes.user.User;
import src.observers.AgendaObserver;

public class UserNotifier implements AgendaObserver {
    private final User user;

    public UserNotifier(User user) {
        this.user = user;
    }

    @Override
    public void update(ReservationEvent event) {
        switch (event.getEventType()) {
            case CREATED:
                System.out.println("[UserNotifier] Hello " + user.getName() + ", your reservation has been created: " + event.getReservation());
                break;
            case CANCELLED:
                System.out.println("[UserNotifier] Hello " + user.getName() + ", your reservation has been cancelled: " + event.getReservation());
                break;
            case MODIFIED:
                System.out.println("[UserNotifier] Hello " + user.getName() + ", your reservation has been modified: " + event.getReservation());
                break;
        }
    }
}
