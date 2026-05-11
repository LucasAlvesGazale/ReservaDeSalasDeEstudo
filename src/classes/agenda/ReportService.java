package src.classes.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.classes.reservation.Reservation;
import src.observers.AgendaObserver;

public class ReportService implements AgendaObserver {
    private final List<Reservation> log = new ArrayList<>();

    @Override
    public void update(Reservation reservation) {
        log.add(reservation);
        System.out.println("[ReportService] Logged reservation: " + reservation);
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