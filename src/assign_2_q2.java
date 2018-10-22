import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class assign_2_q2 {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t =s.nextInt();
        while(t>0){
            t--;

            int noBatches = s.nextInt();
            TimeTable timeTable =new TimeTable(noBatches);
            String[] batchNames = new String[noBatches];
            Map<String,Integer> batchMap = new HashMap<String, Integer>();
            for(int i = 0; i<noBatches;i++){
                batchNames[i] = s.next();
                batchMap.put(batchNames[i],i);
            }

            int noProf = s.nextInt();
            Professor[] professors = new Professor[noProf];
            for(int i = 0 ; i < noProf;i++){
                professors[i] = new Professor(s.next());
            }
            TimeTable.setProfessors(professors);

            int noCourses = s.nextInt();

            Course[] courses = new Course[noCourses];
            for(int j = 0; j<noCourses; j++){
                String courseCode = s.next();
                String courseName = s.next();
                String instName = s.next();
                int priority = s.nextInt();
                int batchNo = batchMap.get(s.next());
                int noSlots = s.nextInt();
                courses[j] = new Course(courseName,courseCode,instName,priority,noSlots);
                CourseSlot[] courseSlots = new CourseSlot[noSlots];
                for(int k = 0 ;k <noSlots ; k++){
                    int duration = s.nextInt();
                    String dayPref = s.next();
                    String timePref = s.next();
                    courseSlots[k] = new CourseSlot(courses[j],duration,dayPref,timePref,k+1,batchNo);
                    timeTable.insertSlot(courseSlots[k]);
                }
            }


            timeTable.fillTimeTable();



            for(int b = 0 ; b  < noBatches ; b++) {
                System.out.print(batchNames[b]+"\n");
                for (int i = 0; i < 6; i++) {
                    System.out.print(timeTable.getDaySlotFromIndex(i) + "\n");
                    for (int j = 0; j < 7; j++) {
                        if (i == 5) {
                            break;
                        } else if (timeTable.getClassAt(i, j,b) == null) {
                            continue;
                        } else {
                            System.out.print(timeTable.getTimeFromIndex(j, timeTable.getClassAt(i, j,b).getDuration()) + " " + timeTable.getClassAt(i, j,b).getCourseCode() + " " + timeTable.getClassAt(i, j,b).getCourseName() + " " + timeTable.getClassAt(i, j,b).getInstName() + "\n");
                            j = j + timeTable.getClassAt(i, j,b).getDuration() - 1;
                        }
                    }
                    if (i == 5) {
                        for (int j = 0; j < timeTable.getNoOfClassOnSat(b); j++) {
                            System.out.print(timeTable.getSatClassAt(j,b).getCourseCode() + " " + timeTable.getSatClassAt(j,b).getCourseName() + " " + timeTable.getSatClassAt(j,b).getInstName() + "\n");
                        }
                    }
                }
            }


        }
    }
}
