package src.observers;

import src.classes.reservation.ReservationEvent;

public interface AgendaSubject {
    void addObserver(AgendaObserver observer);
    void removeObserver(AgendaObserver observer);
    void notifyObservers(ReservationEvent event);
}
