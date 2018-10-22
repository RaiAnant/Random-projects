public class Suggestions {
    private int noSuggestions ;
    private int[] priorities ;
    private String[] courseCodes ;
    private int[] slotNo ;
    private String[] dayPrf ;
    private String[] timePref ;
    private int index ;

    public String getCourseCodeAt(int i){
        return courseCodes[i];
    }

    public int getSlotNoAt(int i){
        return slotNo[i];
    }

    public String getDayPrefAt(int i){
        return dayPrf[i];
    }

    public String getTimePrefAt(int i){
        return timePref[i];
    }

    public Suggestions(int n){
        noSuggestions = n;
        priorities = new int[noSuggestions];
        courseCodes = new String[noSuggestions];
        slotNo = new int[noSuggestions];
        dayPrf = new String[noSuggestions];
        timePref = new String[noSuggestions];
    }

    public void addSuggestion(int priority, String courseCode, int slotNo, String dayPref, String timePref){
        priorities[index] = priority;
        courseCodes[index] = courseCode;
        this.slotNo[index] = slotNo;
        this.dayPrf[index] = dayPref;
        this.timePref[index] = timePref;
        for(int j = index; j>0 ;j--){
            if(priorities[j-1]>priorities[j]){
                String tempStr = courseCodes[j];
                courseCodes[j] = courseCodes[j-1];
                courseCodes[j-1] = tempStr;
                tempStr = dayPrf[j];
                dayPrf[j] = dayPrf[j-1];
                dayPrf[j-1] = tempStr;
                tempStr = this.timePref[j];
                this.timePref[j] = this.timePref[j-1];
                this.timePref[j-1] = tempStr;
                int temp = this.slotNo[j];
                this.slotNo[j] = this.slotNo[j-1];
                this.slotNo[j-1] = temp;
                temp = priorities[j];
                priorities[j] = priorities[j-1];
                priorities[j-1] = temp;
            }else{
                break;
            }
        }
        index++;
    }
}
