package src.classes.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.classes.reservation.Reservation;
import src.observers.AgendaObserver;
import src.observers.AgendaSubject;

public class Agenda implements AgendaSubject {
    private final List<AgendaObserver> observers = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public void addObserver(AgendaObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AgendaObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Reservation reservation) {
        for (AgendaObserver observer : observers) {
            observer.update(reservation);
        }
    }

    public void addReservation(Reservation reservation) {
        if (checkConflict(reservation)) {
            throw new IllegalStateException("Reservation conflicts with an existing one: " + reservation);
        }
        reservations.add(reservation);
        notifyObservers(reservation);
    }

    public boolean checkConflict(Reservation reservation) {
        for (Reservation existing : reservations) {
            if (existing.overlaps(reservation)) return true;
        }
        return false;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
