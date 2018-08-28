public class CourseSlot extends Course{
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
