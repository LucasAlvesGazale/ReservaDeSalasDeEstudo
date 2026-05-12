package src.classes.classrooms;

import src.classes.classroom.Classroom;
import src.policies.ReservationPolicy;

public class LectureClassroom extends Classroom{

	public LectureClassroom(int id, int capacity) {
		super(id, capacity);
	}

	public LectureClassroom(int id, int capacity, ReservationPolicy policy) {
		super(id, capacity, policy);
	}
}
