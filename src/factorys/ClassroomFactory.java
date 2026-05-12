package src.factorys;

import src.classes.classrooms.LabClassroom;
import src.classes.classrooms.LectureClassroom;
import src.classes.classrooms.StudyClassroom;
import src.classes.classroom.Classroom;
import src.policies.*;

public class ClassroomFactory {
    public enum ClassType{
        LAB, STUDY, LECTURE
    }

    public static Classroom create(ClassType type, int id, int capacity){
        switch (type){
            case LECTURE: return new LectureClassroom(id, capacity);
            case STUDY: return new StudyClassroom(id, capacity);
            case LAB: return new LabClassroom(id, capacity);
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public static Classroom create(ClassType type, int id, int capacity, ReservationPolicy policy){
        switch (type){
            case LECTURE: return new LectureClassroom(id, capacity, policy);
            case STUDY: return new StudyClassroom(id, capacity, policy);
            case LAB: return new LabClassroom(id, capacity, policy);
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
