package src.policies;

import java.util.List;

import src.classes.reservation.Reservation;
import src.classes.user.User;

public class FirstComeFirstServedPolicy implements ReservationPolicy {

    @Override
    public boolean validate(Reservation newReservation, List<Reservation> existingReservations, User requester) {
        return existingReservations.stream().noneMatch(r -> r.overlaps(newReservation));
    }
}