public class StudentList {
    Students[] stuList;
    String[] assignName;
    int index;

    public StudentList(int n, String[] name, String[] roll,int noOfAssign){
        assignName = new String[noOfAssign];
        this.stuList = new Students[n];
        for(int i = 0 ;i < n ;i ++){
            this.stuList[i] = new Students(name[i],roll[i],noOfAssign);
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

    public void allotMarks(String assignName,String[] roll,int[] marks){
        this.assignName[index] = assignName;
        index++;
        int flag;
        for(int i = 0;i<stuList.length;i++){
            flag=0;
            for(int j = 0;j<roll.length;j++){
                if(stuList[i].getRoll().compareTo(roll[j])==0){
                    stuList[i].setAssign(assignName,marks[j]);
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                stuList[i].setAssign(assignName,-1);
            }
        }
    }

    public void printFullResult(){
        for(int i = 0 ;i < stuList.length ; i++){
            stuList[i].printResult();
            System.out.print("\n");
        }
    }

    public void printAll(){
        for(int i = 0;i<stuList.length;i++){
            stuList[i].print();
            System.out.print("\n");
        }
    }

    public void printByIndex(int index){
        stuList[index].print();
    }

    public int getLength(){
        return stuList.length;
    }
}
