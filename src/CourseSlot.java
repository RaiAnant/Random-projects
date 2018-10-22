public class CourseSlot extends Course implements Comparable{
    private int duration;
    private String dayPref;
    private String timePref;
    private int batchNo;
    private int slotNo;

    public CourseSlot(Course course, int duration , String dayPref , String timePref, int slotNo,int batchNo) {
        super(course);
        this.duration = duration;
        this.dayPref = dayPref;
        this.timePref = timePref;
        this.slotNo = slotNo;
        this.batchNo = batchNo;
    }

    public int getDuration(){
        return duration;
    }

    public int getSLotNo(){
        return slotNo;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setDayPref(String dayPref) {
        this.dayPref = dayPref;
    }

    public void setTimePref(String timePref) {
        this.timePref = timePref;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
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


    @Override
    public int compareTo(Object o) {
        CourseSlot courseSlot = (CourseSlot)o;
        if(this.getCourseCode().compareTo(courseSlot.getCourseCode())>0){
            return 1;
        }else if(this.getCourseCode().compareTo(courseSlot.getCourseCode())<0){
            return -1;
        }
        return 0;
    }
}
