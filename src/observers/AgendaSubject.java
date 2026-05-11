package src.observers;

import src.classes.reservation.Reservation;

public interface AgendaSubject {
    void addObserver(AgendaObserver observer);
    void removeObserver(AgendaObserver observer);
    void notifyObservers(Reservation reservation);
}
