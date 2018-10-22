import java.util.Scanner;

public class assign_2 {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t =s.nextInt();
        while(t>0){
            t--;
            Init init = new Init();
            int noCourses = s.nextInt();
            TimeTable timeTable =new TimeTable(0);
            init.initCourses(noCourses);
            for(int j = 0; j<noCourses; j++){
                String courseCode = s.next();
                String courseName = s.next();
                String instName = s.next();
                int priority = s.nextInt();
                int noSlots = s.nextInt();
                init.addCourses(new Course(courseName,courseCode,instName,priority,noSlots));
                init.initCourseSlots(noSlots);
                for(int k = 0 ;k <noSlots ; k++){
                    int duration = s.nextInt();
                    String dayPref = s.next();
                    String timePref = s.next();
                    init.addCourseSlot(new CourseSlot(init.getCourseAt(j),duration,dayPref,timePref,k+1,0));
                    timeTable.insertSlot(init.getCourseSlotAt(k));
                }
            }
            timeTable.fillTimeTable();

            for(int i=0;i<6;i++){
                System.out.print(timeTable.getDaySlotFromIndex(i)+"\n");
                for(int j=0;j<7;j++){
                    if(i==5){
                        break;
                    }
                    else if(timeTable.getClassAt(i,j,0)==null){
                        continue;
                    }
                    else {
                        System.out.print(timeTable.getTimeFromIndex(j,timeTable.getClassAt(i,j,0).getDuration())+" "+timeTable.getClassAt(i,j,0).getCourseCode()+" "+timeTable.getClassAt(i,j,0).getCourseName()+" "+timeTable.getClassAt(i,j,0).getInstName()+"\n");
                        j=j+timeTable.getClassAt(i,j,0).getDuration()-1;
                    }
                }
                if(i==5) {
                    for (int j = 0; j < timeTable.getNoOfClassOnSat(0); j++) {
                        System.out.print(timeTable.getSatClassAt(j,0).getCourseCode() + " " + timeTable.getSatClassAt(j,0).getCourseName() + " " + timeTable.getSatClassAt(j,0).getInstName() + "\n");
                    }
                }
            }
        }
    }
}
