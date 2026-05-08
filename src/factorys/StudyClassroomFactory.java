package src.factorys;

import src.classes.classrooms.StudyClassroom;
import src.interfaces.ClassroomInterface;

public class StudyClassroomFactory {
    public static ClassroomInterface create(){
        return new StudyClassroom();
    }
}
