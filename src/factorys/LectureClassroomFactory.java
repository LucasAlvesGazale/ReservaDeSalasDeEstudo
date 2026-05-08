package src.factorys;

import src.classes.classrooms.LectureClassroom;
import src.interfaces.ClassroomInterface;

public class LectureClassroomFactory {
    public static ClassroomInterface create(){
        return new LectureClassroom();
    }
}
