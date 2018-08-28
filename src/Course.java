public class Course {
    private String courseName;
    private String courseCode;
    private String instName;
    private int priority;
    private int noSlots;

    public Course(String courseName,String courseCode,String instName,int priority,int noSlots){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instName = instName;
        this.noSlots = noSlots;
        this.priority = priority;
    }

    public Course(Course course){
        this.courseCode = course.courseCode;
        this.courseName = course.courseName;
        this.instName = course.instName;
        this.noSlots = course.noSlots;
        this.priority = course.priority;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getInstName(){
        return instName;
    }

    public int getPriority(){
        return priority;
    }

    public int getNoSlots(){
        return noSlots;
    }
}
