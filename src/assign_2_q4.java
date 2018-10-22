import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class assign_2_q4 {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t =s.nextInt();
        while(t>0){
            t--;
            Init init = new Init();
            int noBatches = s.nextInt();
            TimeTable timeTable =new TimeTable(noBatches);
            String[] batchNames = new String[noBatches];
            Map<String,Integer> batchMap = new HashMap<String, Integer>();
            for(int i = 0; i<noBatches;i++){
                batchNames[i] = s.next();
                batchMap.put(batchNames[i],i);
            }

            int noProf = s.nextInt();
            init.initProfs(noProf);

            for(int i = 0 ; i < noProf;i++){
                init.addProf(s.next());
            }

            TimeTable.setProfessors(init.getProfessors());

            int noCourses = s.nextInt();
            init.initCourses(noCourses);
            for(int j = 0; j<noCourses; j++){
                String courseCode = s.next();
                String courseName = s.next();
                String instName = s.next();
                int priority = s.nextInt();
                int batchNo = batchMap.get(s.next());
                int noSlots = s.nextInt();
                init.addCourses(new Course(courseName,courseCode,instName,priority,noSlots));
                init.initCourseSlots(noSlots);
                for(int k = 0 ;k <noSlots ; k++){
                    int duration = s.nextInt();
                    String dayPref = s.next();
                    String timePref = s.next();
                    init.addCourseSlot(new CourseSlot(init.getCourseAt(j),duration,dayPref,timePref,k+1,batchNo));
                    timeTable.insertSlot(init.getCourseSlotAt(k));
                }
            }


            timeTable.fillTimeTable();


            int noSuggestions = s.nextInt();
            Suggestions suggestions = new Suggestions(noSuggestions);
            String courseCode;
            int slotNo;
            int priority;
            String dayPrf;
            String timePref;
            for(int i = 0 ; i<noSuggestions; i ++){
                courseCode = s.next();
                slotNo = s.nextInt();
                priority = s.nextInt();
                dayPrf = s.next();
                timePref = s.next();
                suggestions.addSuggestion(priority,courseCode,slotNo,dayPrf,timePref);
            }

            for(int i = 0 ; i<noSuggestions; i ++){
                //System.out.print(courseCodes[i]+"\n");
                timeTable.resetAllSLots();
                CourseSlot removedSlot= timeTable.removeSlotFromTimeTable(suggestions.getSlotNoAt(i),suggestions.getCourseCodeAt(i));
                removedSlot.setDayPref(suggestions.getDayPrefAt(i));
                removedSlot.setTimePref(suggestions.getTimePrefAt(i));
                timeTable.insertSlot(removedSlot);
                timeTable.fillTimeTable();
            }


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
