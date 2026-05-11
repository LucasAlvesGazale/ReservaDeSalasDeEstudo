package src.classes.reservation;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Reservation {
    private final DayOfWeek day;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Reservation(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    public boolean overlaps(Reservation other) {
        if (this.day != other.day) return false;
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        return day + " " + startTime + "-" + endTime;
    }
}
