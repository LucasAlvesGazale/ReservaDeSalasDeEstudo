package src.classes.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.classes.reservation.Reservation;
import src.classes.reservation.ReservationEvent;
import src.observers.AgendaObserver;

public class ReportService implements AgendaObserver {
    private final List<Reservation> log = new ArrayList<>();

    @Override
    public void update(ReservationEvent event) {
        switch (event.getEventType()) {
            case CREATED:
                System.out.println("[ReportService] New reservation created: " + event.getReservation());
                log.add(event.getReservation()); // Adds created reservation to log
                break;
            case CANCELLED:
                System.out.println("[ReportService] Reservation cancelled: " + event.getReservation());
                log.add(event.getReservation()); // Adds cancelled reservation to log
                break;
            case MODIFIED:
                System.out.println("[ReportService] Reservation modified: " + event.getReservation());
                log.add(event.getReservation()); // Adds modified reservation to log
                break;
        }
    }

    public void printReport() {
        System.out.println("=== Reservation Report (" + log.size() + " total) ===");
        for (Reservation r : log) {
            System.out.println("  " + r);
        }
    }

    public List<Reservation> getLog() {
        return Collections.unmodifiableList(log);
    }
}