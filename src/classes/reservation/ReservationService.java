package src.classes.reservation;

import java.time.DayOfWeek;

import src.classes.user.User;
import src.classes.classrooms.Classroom;
import src.classes.reservation.Reservation;
import java.time.DayOfWeek;
import java.time.LocalTime;


public class ReservationService {

    public void makeReservation(User user, Classroom classroom, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        Reservation newRes = new Reservation(day, startTime, endTime);
        classroom.reserve(newRes, user);
    }

    public void cancelReservation(User user, Classroom classroom, Reservation reservation) {
        classroom.cancelReservation(reservation);
    }

    public void modifyReservation(User user, Classroom classroom, Reservation oldRes, DayOfWeek newDay, LocalTime newStart, LocalTime newEnd) {
        Reservation newRes = new Reservation(newDay, newStart, newEnd);
        classroom.modifyReservation(oldRes, newRes, user);
    }
}
