import java.util.ArrayList;
import java.util.Collections;

public class Professor {
    private String name;
    private ArrayList<Course> courses;
    private CourseSlot[][] profTimeTable;
    private ArrayList<CourseSlot> satSLots ;

    public Professor(String name){
        this.name = name;
        courses = new ArrayList<Course>();
        profTimeTable = new CourseSlot[5][7];
        satSLots = new ArrayList<CourseSlot>();
    }

    public String getName() {
        return name;
    }

    public CourseSlot[][] getProfTimeTable() {
        return profTimeTable;
    }

    public ArrayList<CourseSlot> getSatSLots() {
        return satSLots;
    }

    public boolean canFillSlotAt(int index1, int index2){
        if(profTimeTable[index1][index2]==null){
            return true;
        }
        else{
            return false;
        }
    }

    public void fillCourseSlotAt(int index1,int index2,CourseSlot courseSlot){
        if(index1==5){
            satSLots.add(courseSlot);
            Collections.sort(satSLots);
        }
        else {
            profTimeTable[index1][index2] = courseSlot;
        }
    }
}
