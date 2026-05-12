enum EventType {
    CREATED, MODIFIED, CANCELED
}

public class ReservationEvent {
    private Reservation reservation;
    private EventType eventType; // "created", "modified", "canceled"
    
    public ReservationEvent(Reservation reservation, EventType eventType) {
        this.reservation = reservation;
        this.eventType = eventType;
    }
    
    public Reservation getReservation() {
        return reservation;
    }
    
    public EventType getEventType() {
        return eventType;
    }
}