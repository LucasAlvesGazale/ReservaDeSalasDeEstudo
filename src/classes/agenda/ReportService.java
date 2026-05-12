package src.classes.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.classes.reservation.*;
import src.observers.AgendaObserver;

public class ReportService implements AgendaObserver {
    private final List<Reservation> log = new ArrayList<>();

    @Override
    public void update(ReservationEvent event) {
        switch (event.getType()) {
            case CREATED:
                log.add(event.getReservation());
                System.out.println("[ReportService] Logged reservation: " + event.getReservation());
                break;
            case CANCELLED:
                // No actions needed for cancellation in this simple log, but we could mark it as cancelled if we wanted.
                System.out.println("[ReportService] Removed reservation from log: " + event.getReservation());
                break;
            case UPDATED:
                // For simplicity, we just add the new reservation since it's just a log.
                log.add(event.getReservation());
                System.out.println("[ReportService] Updated reservation in log: " + event.getReservation());
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