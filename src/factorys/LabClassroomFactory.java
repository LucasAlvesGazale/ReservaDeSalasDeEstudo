package src.factorys;

import src.classes.classrooms.LabClassroom;
import src.interfaces.ClassroomInterface;

public class LabClassroomFactory {
    public static ClassroomInterface create(){
        return new LabClassroom();
    }
}
