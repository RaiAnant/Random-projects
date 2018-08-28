import java.util.Scanner;

public class assign_2 {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t =s.nextInt();
        while(t>0){
            t--;
            int noCourses = s.nextInt();
            TimeTable timeTable =new TimeTable();
            Course[] courses = new Course[noCourses];
            for(int j = 0; j<noCourses; j++){
                String courseCode = s.next();
                String courseName = s.next();
                String instName = s.next();
                int priority = s.nextInt();
                int noSlots = s.nextInt();
                courses[j] = new Course(courseName,courseCode,instName,priority,noSlots);
                CourseSlot[] courseSlots = new CourseSlot[noSlots];
                for(int k = 0 ;k <noSlots ; k++){
                    int duration = s.nextInt();
                    String dayPref = s.next();
                    String timePref = s.next();
                    courseSlots[k] = new CourseSlot(courses[j],duration,dayPref,timePref,k+1);
                    timeTable.insertSlot(courseSlots[k]);
                }
            }
            timeTable.fillTimeTable();
            timeTable.printTimeTable();
        }
    }
}
