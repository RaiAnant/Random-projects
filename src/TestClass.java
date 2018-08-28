/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;
*/

import java.util.*;
// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail

class TestClass {

    public static class Course {
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




    public static class CourseSlot extends Course{
        private int duration;
        private String dayPref;
        private String timePref;
        private int slotNo;

        public CourseSlot(Course course,int duration ,String dayPref ,String timePref,int slotNo) {
            super(course);
            this.duration = duration;
            this.dayPref = dayPref;
            this.timePref = timePref;
            this.slotNo = slotNo;
        }

        public int getDuration(){
            return duration;
        }

        public int getSLotNo(){
            return slotNo;
        }

        public int[] getDayPrefArray(){
            if(dayPref.compareTo("NIL")==0){
                return null;
            }
            int[] arr = new int[dayPref.length()];
            char[] str = dayPref.toCharArray();

            for(int i = 0; i < dayPref.length(); i++){
                arr[i] = Character.getNumericValue(str[i]);
            }

            return arr;
        }

        public String[] getTimePrefArray(){
            if(timePref.compareTo("NIL")==0){
                return null;
            }
            String[] str = new String[timePref.length()/4];
            for(int i=0 ; i<timePref.length()/4 ; i++){
                str[i] = timePref.substring(0+4*i,4+4*i);
            }
            return str;
        }
    }





    public static class TimeTable {
        private CourseSlot[][] timeTable;
        private ArrayList<CourseSlot> allSLots;
        private ArrayList<CourseSlot> satSlots;
        private int[] workLoad;
        private int index,satIndex ;
        private static final int LOAD_MAX = 100;

        private static final String[] timeSlots = {"0900","1000","1115","1215","0300","0400","0500"};
        private static final String[] daySlots = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

        public TimeTable() {
            this.timeTable = new CourseSlot[5][7];
            this.workLoad = new int[5];
            this.satSlots = new ArrayList<CourseSlot>();
            this.allSLots = new ArrayList<CourseSlot>();
        }

        public static int getIndexFromTime(String time){
            if(time.compareTo("0900")==0){
                return 0;
            }else if(time.compareTo("1000")==0){
                return 1;
            }else if(time.compareTo("1115")==0){
                return 2;
            }else if(time.compareTo("1215")==0){
                return 3;
            }else if(time.compareTo("0300")==0){
                return 4;
            }else if(time.compareTo("0400")==0){
                return 5;
            }else {
                return 6;
            }
        }

        public String getDaySlotFromIndex(int index){
            return daySlots[index];
        }

        public CourseSlot getClassAt(int i,int j){
            return timeTable[i][j];
        }

        public int getNoOfClassOnSat(){
            return satSlots.size();
        }

        public CourseSlot getSatClassAt(int i){
            return satSlots.get(i);
        }

        public static String getTimeFromIndex(int index,int duration){
            int startingHr = 9;
            switch (index){
                case 0:
                    startingHr=startingHr + index;
                    return String.format("%d:00-%d:00",startingHr,startingHr+duration);

                case 1:
                    startingHr=startingHr + index;
                    return String.format("%d:00-%d:00",startingHr,startingHr+duration);

                case 2:
                    startingHr=startingHr + index;
                    int finalHr = startingHr+duration;
                    if(finalHr>12){
                        finalHr = finalHr%12;
                    }
                    return String.format("%d:15-%d:15",startingHr,finalHr);

                case 3:
                    startingHr=startingHr + index;
                    return String.format("%d:15-%d:15",startingHr,(startingHr+duration)%12);

                case 4:
                    startingHr=(startingHr + index+2)%12;
                    return String.format("%d:00-%d:00",startingHr,startingHr+duration);

                case 5:
                    startingHr=(startingHr + index+2)%12;
                    return String.format("%d:00-%d:00",startingHr,startingHr+duration);

                case 6:
                    startingHr=(startingHr + index+2)%12;
                    return String.format("%d:00-%d:00",startingHr,startingHr+duration);
            }
            return null;
        }

        public void insertSlot(CourseSlot slot){
            allSLots.add(slot);
        }


        private void sortSLots(){
            int size = allSLots.size();
            for(int i = 0 ;i < size-1; i++){
                for(int j = 0 ; j < size -i -1; j++){
                    if(allSLots.get(j).getSLotNo() > allSLots.get(j+1).getSLotNo()){
                        CourseSlot temp = allSLots.get(j);
                        allSLots.set(j,allSLots.get(j+1)) ;
                        allSLots.set(j+1,temp);
                    }else if(allSLots.get(j).getSLotNo() == allSLots.get(j+1).getSLotNo()){
                        if(allSLots.get(j).getPriority() > allSLots.get(j+1).getPriority()){
                            CourseSlot temp = allSLots.get(j);
                            allSLots.set(j,allSLots.get(j+1));
                            allSLots.set(j+1,temp);
                        }
                    }
                }
            }
        }

        private void sortSatSlots(){
            int size = satIndex;
            for(int i = 0 ;i < size-1; i++){
                for(int j = 0 ; j < size -i -1; j++){
                    if(satSlots.get(j).getCourseCode().compareTo(satSlots.get(j+1).getCourseCode()) > 0){
                        CourseSlot temp = satSlots.get(j);
                        satSlots.set(j,satSlots.get(j+1));
                        satSlots.set(j+1,temp);
                    }
                }
            }
        }

        private boolean canFillSlotAt(int index1,int index2,CourseSlot s){
            if((index1==1||index1==3||index1==6)&&s.getDuration()>1){
                return false;
            }
            if((index1==0||index1==2||index1==5)&&s.getDuration()>2){
                return false;
            }
            if(s.getDuration()>3){
                return false;
            }
            for(int i=0;i<s.getDuration();i++){
                if(timeTable[index2][index1+i]!=null){
                    return false;
                }
            }

            return true;
        }

        private int getMinWorkLoadDay(int[] workLoad){
            int min = LOAD_MAX;
            int index = -1;
            for(int i =0 ; i < workLoad.length ; i++){
                if(min>workLoad[i]){
                    min = workLoad[i];
                    index = i;
                }
            }
            return index;
        }

        public void fillTimeTable(){
            this.sortSLots();

            for(int i = 0 ; i< allSLots.size();i++){

                boolean flag = false;
                int[] dayPrefArr = allSLots.get(i).getDayPrefArray();
                if(dayPrefArr==null){
                    //System.out.print("---No Day Pref Found---\n\n");
                    dayPrefArr = new int[]{-1};
                }
                String[] timePrefArr;
                timePrefArr = allSLots.get(i).getTimePrefArray();
                int len=0;
                if(timePrefArr!=null){
                    len = timePrefArr.length;
                }
                for (int j = 0; j < len; j++) {
                    int[] tempWorkLoad = workLoad.clone();
                    for (int k : dayPrefArr) {
                        if(dayPrefArr.length==1)
                            //System.out.print("Looking spot at "+ Integer.toString(k)+"\n\n");
                            if(k==-1){
                                break;
                            }
                        tempWorkLoad[k - 1] = LOAD_MAX;
                        if (canFillSlotAt(getIndexFromTime(timePrefArr[j]), k - 1, allSLots.get(i))) {
                            for(int m=0;m<allSLots.get(i).getDuration();m++){
                                //System.out.print("Filling - "+allSLots.get(i).getCourseCode()+" at "+Integer.toString(k - 1)+","+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                                //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " at "+Integer.toString(k - 1)+", "+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                                timeTable[k - 1][getIndexFromTime(timePrefArr[j])+m] = allSLots.get(i);
                                workLoad[k-1]++;
                            }
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        break;
                    }

                    int nextDay = getMinWorkLoadDay(tempWorkLoad);

                    while (nextDay != -1) {
                        tempWorkLoad[nextDay] = LOAD_MAX;
                        if (canFillSlotAt(getIndexFromTime(timePrefArr[j]), nextDay, allSLots.get(i))) {
                            for(int m=0;m<allSLots.get(i).getDuration();m++){
                                //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " at "+Integer.toString(nextDay        )+", "+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                                timeTable[nextDay][getIndexFromTime(timePrefArr[j])+m] = allSLots.get(i);
                                workLoad[nextDay]++;
                            }
                            flag = true;
                            break;
                        }
                        nextDay = getMinWorkLoadDay(tempWorkLoad);
                    }
                    if (flag) {
                        break;
                    }
                }


                int[] tempWorkLoad = workLoad.clone();
                for (int k : dayPrefArr) {
                    //if(dayPrefArr.length==1)
                    //System.out.print("Looking spot at "+ Integer.toString(k)+"\n\n");
                    if(k==-1||flag){
                        break;
                    }
                    tempWorkLoad[k - 1] = LOAD_MAX;
                    for(int j=0;j<7;j++) {
                        if (canFillSlotAt(j, k - 1, allSLots.get(i))) {
                            for (int m = 0; m < allSLots.get(i).getDuration(); m++) {
                                //System.out.print("Filling - "+allSLots.get(i).getCourseCode()+" at "+Integer.toString(k - 1)+","+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                                //System.out.print("Filling " + allSLots.get(i).getCourseCode() + " at " + Integer.toString(k - 1) + ", " + Integer.toString(j + m) + "\n");
                                timeTable[k - 1][j + m] = allSLots.get(i);
                                workLoad[k - 1]++;
                            }
                            flag = true;
                            break;
                        }
                    }
                }
                int nextDay = getMinWorkLoadDay(tempWorkLoad);

                while (nextDay != -1 && !flag) {
                    tempWorkLoad[nextDay] = LOAD_MAX;
                    for(int j=0;j<7;j++){

                        if (canFillSlotAt(j, nextDay, allSLots.get(i))) {
                            for(int m=0;m<allSLots.get(i).getDuration();m++){
                                //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " at "+Integer.toString(nextDay        )+", "+Integer.toString(j+m)+"\n");
                                timeTable[nextDay][j+m] = allSLots.get(i);
                                workLoad[nextDay]++;
                            }
                            flag = true;
                            break;
                        }
                    }

                    nextDay = getMinWorkLoadDay(tempWorkLoad);
                }

                if (!flag) {
                    //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " in sat"+"\n");
                    satSlots.add(allSLots.get(i));
                    satIndex++;
                }
            }
            sortSatSlots();
        }


    }





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

            for(int i=0;i<6;i++){
                System.out.print(timeTable.getDaySlotFromIndex(i)+"\n");
                for(int j=0;j<7;j++){
                    if(i==5){
                        break;
                    }
                    else if(timeTable.getClassAt(i,j)==null){
                        continue;
                    }
                    else {
                        System.out.print(timeTable.getTimeFromIndex(j,timeTable.getClassAt(i,j).getDuration())+" "+timeTable.getClassAt(i,j).getCourseCode()+" "+timeTable.getClassAt(i,j).getCourseName()+" "+timeTable.getClassAt(i,j).getInstName()+"\n");
                        j=j+timeTable.getClassAt(i,j).getDuration()-1;
                    }
                }
                if(i==5) {
                    for (int j = 0; j < timeTable.getNoOfClassOnSat(); j++) {
                        System.out.print(timeTable.getSatClassAt(j).getCourseCode() + " " + timeTable.getSatClassAt(j).getCourseName() + " " + timeTable.getSatClassAt(j).getInstName() + "\n");
                    }
                }
            }
        }
    }
}
