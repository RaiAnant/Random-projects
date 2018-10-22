public class Init {
    private Professor[] professors;
    private int profIndex;
    private Course[] courses;
    private int courseIndex;
    private CourseSlot[] courseSlots;
    private int courseSlotIndex;

    public void initProfs(int n){
        professors = new Professor[n];
        profIndex = 0;
    }

    public void addProf(String name){
        professors[profIndex] = new Professor(name);
        profIndex++;
    }

    public Professor[] getProfessors() {
        return professors;
    }

    public void initCourses(int n){
        courses = new Course[n];
        courseIndex = 0;
    }

    public void addCourses(Course course){
        courses[courseIndex] = course;
        courseIndex++;
    }

    public Course getCourseAt(int i){
        return courses[i];
    }

    public void initCourseSlots(int n){
        courseSlots = new CourseSlot[n];
        courseSlotIndex = 0;
    }

    public void addCourseSlot(CourseSlot courseSlot){
        courseSlots[courseSlotIndex] = courseSlot;
        courseSlotIndex++;
    }

    public CourseSlot getCourseSlotAt(int i){
        return courseSlots[i];
    }
}
