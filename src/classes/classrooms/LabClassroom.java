package src.classes.classrooms;

import src.classes.classroom.Classroom;
import src.policies.ReservationPolicy;

public class LabClassroom extends Classroom{

	public LabClassroom(int id, int capacity) {
		super(id, capacity);
	}

	public LabClassroom(int id, int capacity, ReservationPolicy policy) {
		super(id, capacity, policy);
	}
}
