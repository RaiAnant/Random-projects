
public class Students {
    private String name;
    private String roll;
    private String[] assignName ;
    private int[] assignMarks ;
    private int index;
    private boolean doneAnyAsignment;

    public Students(String name, String roll,int noOfAssign){
        assignName = new String[noOfAssign];
        assignMarks = new int[noOfAssign];
        this.name = name;
        this.roll = roll;
        doneAnyAsignment = false;
    }

    public String getRoll() {
        return roll;
    }


    public void setAssign(String assignName,int assignMarks){
        this.assignName[index] = assignName;
        this.assignMarks[index] = assignMarks;
        index++;
    }

    public void print(){
        System.out.print(roll+" "+name+" ");
    }

    public int compare_roll(Students stu){
        return this.roll.compareTo(stu.roll);
    }

    public int totalMarks(){
        int sum = 0;
        for(int n:assignMarks){
            if(n!=-1)
            sum = sum+n;
        }
        return sum;
    }

    public void printResult(){
        print();
        for(int i = 0;i<assignMarks.length;i++){
            if(assignMarks[i]!=-1) {
                System.out.print(assignMarks[i]);
                System.out.print(" ");
                System.out.print(assignName[i]);
                System.out.print(" ");
                if (i != assignMarks.length - 1&&assignMarks[i+1]!=-1) {
                    System.out.print(" + ");
                }
            }
        }
        System.out.print("= ");
        System.out.print(totalMarks());

    }
}
