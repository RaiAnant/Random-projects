import java.util.ArrayList;
import java.util.Collections;

public class TimeTable {
    private int noOfBatch;
    private CourseSlot[][][] timeTable;
    private ArrayList<CourseSlot> allSLots;
    private ArrayList<CourseSlot>[] satSlots ;
    private static Professor[] professors;
    private int[][] workLoad;
    private static final int LOAD_MAX = 100;

    private static final String[] timeSlots = {"0900","1000","1115","1215","0300","0400","0500"};
    private static final String[] daySlots = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public TimeTable(int noBatch) {
        noOfBatch = noBatch;
        this.timeTable = new CourseSlot[noBatch][5][7];
        this.workLoad = new int[noBatch][5];
        satSlots = new ArrayList[noBatch];
        for(int i = 0; i<noBatch;i++){
            this.satSlots[i] = new ArrayList<>();
        }
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

    public static String getDayFromIndex(int index){
        return daySlots[index];
    }

    public static void setProfessors(Professor[] proffs) {
        professors = proffs;
    }

    public Professor getProfByName(String name){
        for(int i = 0; i < professors.length;i++){
            if(professors[i].getName().compareTo(name)==0){
                return professors[i];
            }
        }
        return null;
    }

    public String getDaySlotFromIndex(int index){
        return daySlots[index];
    }

    public CourseSlot getClassAt(int i, int j,int noBatch){
        return timeTable[noBatch][i][j];
    }

    public int getNoOfClassOnSat(int batchNo){
        return satSlots[batchNo].size();
    }

    public CourseSlot getSatClassAt(int i,int batchNo){
        return satSlots[batchNo].get(i);
    }

    public CourseSlot removeSlotFromTimeTable(int slotNo, String courseCode){
        for(int i = 0 ; i<noOfBatch;i++){
            for(int j = 0 ; j <6;j++){
                for(int k = 0 ; k < 7 ;k++){
                    if(j==5){
                        break;
                    }
                    if(timeTable[i][j][k]==null){
                        continue;
                    }else if(timeTable[i][j][k].getSLotNo() == slotNo && timeTable[i][j][k].getCourseCode().compareTo(courseCode)==0){
                        CourseSlot courseSlot =  timeTable[i][j][k];
                        int dur = timeTable[i][j][k].getDuration();
                        for(int l=0;l<dur;l++){
                            timeTable[i][j][k+l]=null;
                            workLoad[i][j]--;
                        }
                        return courseSlot;
                    }
                }
                if(j==5){
                    for(int k=0;k<satSlots[i].size();k++){
                        if(satSlots[i].get(k).getSLotNo() == slotNo && satSlots[i].get(k).getCourseCode().compareTo(courseCode)==0){
                            CourseSlot courseSlot =  satSlots[i].get(k);
                            satSlots[i].remove(courseSlot);
                            return courseSlot;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void resetAllSLots(){
        allSLots.clear();
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

//    private void sortSatSlots(){
//        int size = satIndex;
//        for(int i = 0 ;i < size-1; i++){
//            for(int j = 0 ; j < size -i -1; j++){
//                if(satSlots.get(j).getCourseCode().compareTo(satSlots.get(j+1).getCourseCode()) > 0){
//                    CourseSlot temp = satSlots.get(j);
//                    satSlots.set(j,satSlots.get(j+1));
//                    satSlots.set(j+1,temp);
//                }
//            }
//        }
//    }

    private boolean canFillSlotAt(int index1, int index2, int batchNo,CourseSlot s){
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
            if(timeTable[batchNo][index2][index1+i]!=null){
                return false;
            }
            else if(!getProfByName(s.getInstName()).canFillSlotAt(index2,index1+i)){
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
                int[] tempWorkLoad = workLoad[allSLots.get(i).getBatchNo()].clone();
                for (int k : dayPrefArr) {
                    if(dayPrefArr.length==1)
                        //System.out.print("Looking spot at "+ Integer.toString(k)+"\n\n");
                        if(k==-1){
                            break;
                        }
                    tempWorkLoad[k - 1] = LOAD_MAX;
                    if (canFillSlotAt(getIndexFromTime(timePrefArr[j]), k - 1, allSLots.get(i).getBatchNo(),allSLots.get(i))) {
                        for(int m=0;m<allSLots.get(i).getDuration();m++){
                            //System.out.print("Filling - "+allSLots.get(i).getCourseCode()+" at "+Integer.toString(k - 1)+","+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                            //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " at "+Integer.toString(k - 1)+", "+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                            timeTable[allSLots.get(i).getBatchNo()][k - 1][getIndexFromTime(timePrefArr[j])+m] = allSLots.get(i);
                            getProfByName(allSLots.get(i).getInstName()).fillCourseSlotAt(k - 1,getIndexFromTime(timePrefArr[j])+m,allSLots.get(i));
                            workLoad[allSLots.get(i).getBatchNo()][k-1]++;
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
                    if (canFillSlotAt(getIndexFromTime(timePrefArr[j]), nextDay, allSLots.get(i).getBatchNo(),allSLots.get(i))) {
                        for(int m=0;m<allSLots.get(i).getDuration();m++){
                            //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " at "+Integer.toString(nextDay        )+", "+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                            timeTable[allSLots.get(i).getBatchNo()][nextDay][getIndexFromTime(timePrefArr[j])+m] = allSLots.get(i);
                            getProfByName(allSLots.get(i).getInstName()).fillCourseSlotAt(nextDay,getIndexFromTime(timePrefArr[j])+m,allSLots.get(i));
                            workLoad[allSLots.get(i).getBatchNo()][nextDay]++;
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


            int[] tempWorkLoad = workLoad[allSLots.get(i).getBatchNo()].clone();
            for (int k : dayPrefArr) {
                if(k==-1||flag){
                    break;
                }
                tempWorkLoad[k - 1] = LOAD_MAX;
                for(int j=0;j<7;j++) {
                    if (canFillSlotAt(j, k - 1,allSLots.get(i).getBatchNo(), allSLots.get(i))) {
                        for (int m = 0; m < allSLots.get(i).getDuration(); m++) {
                            //System.out.print("Filling - "+allSLots.get(i).getCourseCode()+" at "+Integer.toString(k - 1)+","+Integer.toString(getIndexFromTime(timePrefArr[j])+m)+"\n");
                            //System.out.print("Filling " + allSLots.get(i).getCourseCode() + " at " + Integer.toString(k - 1) + ", " + Integer.toString(j + m) + "\n");
                            timeTable[allSLots.get(i).getBatchNo()][k - 1][j + m] = allSLots.get(i);
                            getProfByName(allSLots.get(i).getInstName()).fillCourseSlotAt(k - 1,j+m,allSLots.get(i));
                            workLoad[allSLots.get(i).getBatchNo()][k - 1]++;
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

                    if (canFillSlotAt(j, nextDay,allSLots.get(i).getBatchNo(), allSLots.get(i))) {
                        for(int m=0;m<allSLots.get(i).getDuration();m++){
                            //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " at "+Integer.toString(nextDay        )+", "+Integer.toString(j+m)+"\n");
                            timeTable[allSLots.get(i).getBatchNo()][nextDay][j+m] = allSLots.get(i);
                            getProfByName(allSLots.get(i).getInstName()).fillCourseSlotAt(nextDay,j+m,allSLots.get(i));
                            workLoad[allSLots.get(i).getBatchNo()][nextDay]++;
                        }
                        flag = true;
                        break;
                    }
                }

                nextDay = getMinWorkLoadDay(tempWorkLoad);
            }

            if (!flag) {
                //System.out.print("Filling "+allSLots.get(i).getCourseCode()+ " in sat"+"\n");
                satSlots[allSLots.get(i).getBatchNo()].add(allSLots.get(i));
                getProfByName(allSLots.get(i).getInstName()).fillCourseSlotAt(5,-1,allSLots.get(i));
            }
        }
        for(int i = 0 ;i<satSlots.length;i++) {
            Collections.sort(satSlots[i]);
        }
    }

}