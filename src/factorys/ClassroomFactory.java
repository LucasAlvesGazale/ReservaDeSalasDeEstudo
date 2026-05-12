package src.factorys;

import src.classes.classrooms.Classroom;
import src.classes.classrooms.LabClassroom;
import src.classes.classrooms.LectureClassroom;
import src.classes.classrooms.StudyClassroom;

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
}
