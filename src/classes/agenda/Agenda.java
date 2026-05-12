package src.classes.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.classes.reservation.Reservation;
import src.classes.reservation.ReservationEvent;
import src.classes.reservation.ReservationEventType;
import src.classes.user.User;
import src.observers.AgendaObserver;
import src.observers.AgendaSubject;
import src.policies.ReservationPolicy;

public class Agenda implements AgendaSubject {
    private final List<AgendaObserver> observers = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private ReservationPolicy reservationPolicy;

    public Agenda(ReservationPolicy reservationPolicy) {
        this.reservationPolicy = reservationPolicy;
    }

    public void setReservationPolicy(ReservationPolicy reservationPolicy) {
        this.reservationPolicy = reservationPolicy;
    }

    public void addObserver(AgendaObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AgendaObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(ReservationEvent event) {
        for (AgendaObserver observer : observers) {
            observer.update(event);
        }
    }

    public void addReservation(Reservation reservation, User requester) {
        if (!reservationPolicy.validate(reservation, reservations, requester)) {
            throw new IllegalStateException("Reservation rejected by policy: " + reservation);
        }
        reservations.add(reservation);
        notifyObservers(new ReservationEvent(ReservationEventType.CREATED, reservation));
    }

    public void removeReservation(Reservation reservation) {
        if (reservations.remove(reservation)) {
            notifyObservers(new ReservationEvent(ReservationEventType.CANCELLED, reservation));
        }
    }

    public void updateReservation(Reservation oldRes, Reservation newRes, User requester) {
        if (!reservations.contains(oldRes)) {
            throw new IllegalArgumentException("Original reservation not found: " + oldRes);
        }
        if (!reservationPolicy.validate(newRes, reservations, requester)) {
            throw new IllegalStateException("Updated reservation rejected by policy: " + newRes);
        }
        reservations.remove(oldRes);
        reservations.add(newRes);
        notifyObservers(new ReservationEvent(ReservationEventType.UPDATED, newRes));
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
