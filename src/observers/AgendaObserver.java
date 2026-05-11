package src.observers;

import src.classes.reservation.Reservation;

public interface AgendaObserver {
    void update(Reservation res);
}
