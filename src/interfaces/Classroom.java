package src.interfaces;

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

	public void reserve() {
		agenda.addReservation();
	}
}
