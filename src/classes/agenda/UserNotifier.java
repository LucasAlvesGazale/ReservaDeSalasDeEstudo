package src.classes.agenda;

import src.classes.reservation.Reservation;
import src.classes.user.User;
import src.observers.AgendaObserver;

public class UserNotifier implements AgendaObserver {
    private final User user;

    public UserNotifier(User user) {
        this.user = user;
    }

    @Override
    public void update(Reservation reservation) {
        System.out.println("Notification to " + user.getName() + 
        ": reservation confirmed for " + reservation.toString());
    }
}
