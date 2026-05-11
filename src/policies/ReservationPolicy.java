package src.policies;

import java.util.List;

import src.classes.reservation.Reservation;
import src.classes.user.User;

public interface ReservationPolicy {
    boolean validate(Reservation newReservation, List<Reservation> existingReservations, User requester);
}
