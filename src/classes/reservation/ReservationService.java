package src.classes.reservation;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.interfaces.Classroom;
import src.classes.user.User;

public class ReservationService {

    public void makeReservation(Classroom classroom, User user, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        Reservation newRes = new Reservation(day, startTime, endTime);
        classroom.reserve(newRes, user);
    }

    public void cancelReservation(Classroom classroom, Reservation reservation) {
        classroom.cancel(reservation);
    }

    public void modifyReservation(Classroom classroom, Reservation oldRes, Reservation newRes, User user) {
        classroom.modify(oldRes, newRes, user);
    }

}