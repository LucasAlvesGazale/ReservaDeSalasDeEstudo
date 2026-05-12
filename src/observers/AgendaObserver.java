package src.observers;

import src.classes.reservation.Reservation;
import src.classes.reservation.ReservationEvent;

public interface AgendaObserver {
    void update(ReservationEvent event);
}
