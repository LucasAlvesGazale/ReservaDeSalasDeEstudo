package src.classes.classrooms;

import src.classes.reservation.Reservation;
import src.classes.user.User;
import src.classes.agenda.Agenda;
import src.policies.FirstComeFirstServedPolicy;
import src.policies.ReservationPolicy;

public abstract class Classroom {
	protected final int id;
	protected int capacity;
    protected Agenda agenda;

	public Classroom(int id, int capacity) {
		this.id = id;
		this.capacity = capacity;
        this.agenda = new Agenda(new FirstComeFirstServedPolicy());
	}

    public Classroom(int id, int capacity, ReservationPolicy policy) {
        this.id = id;
        this.capacity = capacity;
        this.agenda = new Agenda(policy);
    }

	public int getId() {
		return id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void reserve(Reservation reservation, User requester) {
		agenda.addReservation(reservation, requester);
	}

	public void cancelReservation(Reservation reservation) {
		agenda.removeReservation(reservation);
	}

	public void modifyReservation(Reservation oldRes, Reservation newRes, User requester) {
		agenda.updateReservation(oldRes, newRes, requester);
	}
}
