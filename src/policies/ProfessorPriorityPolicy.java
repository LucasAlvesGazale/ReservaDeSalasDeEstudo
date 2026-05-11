package src.policies;

import java.util.List;

import src.classes.reservation.Reservation;
import src.classes.user.User;

public class ProfessorPriorityPolicy implements ReservationPolicy {

    @Override
    public boolean validate(Reservation newReservation, List<Reservation> existingReservations, User requester) {
        if ("professor".equalsIgnoreCase(requester.getRole())) {
            return true;
        }
        return existingReservations.stream().noneMatch(r -> r.overlaps(newReservation));
    }
}