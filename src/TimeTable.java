import java.util.ArrayList;

public class TimeTable {
    private CourseSlot[][] timeTable;
    private ArrayList<CourseSlot> allSLots;
    private CourseSlot[] satSlots;
    private int[] workLoad;
    private int index,satIndex ;
    private static final int LOAD_MAX = 100;

    private static final String[] timeSlots = {"0900","1000","1115","1215","0300","0400","0500"};
    private static final String[] daySlots = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public TimeTable() {
        this.timeTable = new CourseSlot[5][7];
        this.workLoad = new int[5];
        this.satSlots = new CourseSlot[7];
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
                return String.format("%d:15-%d:15",startingHr,startingHr+duration);

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
        int size = satSlots.length;
        for(int i = 0 ;i < size-1; i++){
            for(int j = 0 ; j < size -i -1; j++){
                if(satSlots[j].getCourseCode().compareTo(satSlots[j+1].getCourseCode()) > 0){
                    CourseSlot temp = satSlots[j];
                    satSlots[j] = satSlots[j+1] ;
                    satSlots[j+1] = temp;
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
                dayPrefArr = new int[]{-1};
            }
            for(int l=0;l<2;l++) {
                String[] timePrefArr;
                if(l==0) {
                    timePrefArr = allSLots.get(i).getTimePrefArray();
                    if(timePrefArr==null){
                        if(dayPrefArr[0]==-1){

                            break;
                        }
                        continue;
                    }
                }else{
                    timePrefArr = timeSlots.clone();
                }

                for (int j = 0; j < timePrefArr.length; j++) {
                    int[] tempWorkLoad = workLoad.clone();
                    for (int k : dayPrefArr) {
                        if(k==-1){
                            break;
                        }
                        tempWorkLoad[k - 1] = LOAD_MAX;
                        if (canFillSlotAt(getIndexFromTime(timePrefArr[j]), k - 1, allSLots.get(i))) {
                            for(int m=0;m<allSLots.get(i).getDuration();m++){
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
                if (flag) {
                    break;
                }
            }

            int[] tempWorkLoad = workLoad.clone();
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
                satSlots[satIndex] = allSLots.get(i);
                satIndex++;
            }
        }
        sortSatSlots();
    }


    public void printTimeTable(){
        for(int i=0;i<6;i++){
            System.out.print(daySlots[i]+"\n");
            for(int j=0;j<7;j++){
                if(i==5){
                    System.out.print(satSlots[j].getCourseCode()+" "+satSlots[j].getCourseName()+" "+satSlots[j].getInstName()+"\n");
                }
                else if(timeTable[i][j]==null){
                    continue;
                }
                else {
                    System.out.print(getTimeFromIndex(j,timeTable[i][j].getDuration())+" "+timeTable[i][j].getCourseCode()+" "+timeTable[i][j].getCourseName()+" "+timeTable[i][j].getInstName()+"\n");
                    j=j+timeTable[i][j].getDuration()-1;
                }
            }
            System.out.print("\n");
        }
    }

}
