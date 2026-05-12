package src.classes.classrooms;

import src.classes.classroom.Classroom;
import src.policies.ReservationPolicy;

public class StudyClassroom extends Classroom{

	public StudyClassroom(int id, int capacity) {
		super(id, capacity);
	}

	public StudyClassroom(int id, int capacity, ReservationPolicy policy) {
		super(id, capacity, policy);
	}
}
