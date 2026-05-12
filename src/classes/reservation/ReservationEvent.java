package src.classes.reservation;

public class ReservationEvent {
    private final ReservationEventType type;
    private final Reservation reservation;

    public ReservationEvent(ReservationEventType type, Reservation reservation) {
        this.type = type;
        this.reservation = reservation;
    }

    public ReservationEventType getType() { return type; }
    public Reservation getReservation() { return reservation; }
}