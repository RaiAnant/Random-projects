public class StudentList {
    Students[] stuList;
    String[] assignName;

    public StudentList(int n, String[] name, String[] roll){
        this.stuList = new Students[n];
        for(int i = 0 ;i < n ;i ++){
            this.stuList[i] = new Students(name[i],roll[i]);
        }
    }

    public void sort(){
        for(int i = 0 ;i < stuList.length-1 ; i++){
            for(int j=0;j < stuList.length -1-i ; j++){
                if(stuList[j].compare_roll(stuList[j+1])>0){
                    Students temp;
                    temp = stuList[j];
                    stuList[j] = stuList[j+1];
                    stuList[j+1] = temp;
                }
            }
        }
    }

    public void printAll(){
        for(int i = 0;i<stuList.length;i++){
            stuList[i].print();
        }
    }

    public void printByIndex(int index){
        stuList[index].print();
    }

    public int getLength(){
        return stuList.length;
    }
}
