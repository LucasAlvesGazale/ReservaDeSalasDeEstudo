package src.factorys;

import src.classes.classrooms.LabClassroom;
import src.classes.classrooms.LectureClassroom;
import src.classes.classrooms.StudyClassroom;
import src.interfaces.ClassroomInterface;

public class ClassroomFactory {
    public enum ClassType{
        LAB, STUDY, LECTURE
    }

    public static ClassroomInterface create(ClassType type){
        switch (type){
            case LECTURE: return new LectureClassroom();
            case STUDY: return new StudyClassroom();
            case LAB: return new LabClassroom();
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
