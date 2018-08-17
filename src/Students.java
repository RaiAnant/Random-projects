import java.util.ArrayList;

public class Students {
    private String name;
    private String roll;
    private ArrayList<String> assignName = new ArrayList<String>();
    private ArrayList<Integer> assignMarks = new ArrayList<Integer>();
    private int index;
    private boolean doneAnyAsignment;

    public Students(String name, String roll){
        this.name = name;
        this.roll = roll;
        doneAnyAsignment = false;
    }

    public String getRoll() {
        return roll;
    }


    public void setNextAssign(String assignName){
        this.assignName.add(assignName);
        index++;
    }

    public void setAssignMarks(int marks){
        this.assignMarks.add(marks);
    }

    public void print(){
        System.out.print(roll+" "+name);
    }

    public int compare_roll(Students stu){
        return this.roll.compareTo(stu.roll);
    }

    public int totalMarks(){
        int sum = 0;
        for(int n:assignMarks){
            sum = sum+n;
        }
        return sum;
    }

    public void printResult(){
        print();
        for(int i = 0;i<assignMarks.size();i++){
            System.out.print(assignMarks.get(i));
            System.out.print(" ");
            System.out.print(assignName.get(i));
            System.out.print(" ");
            if(i!=assignMarks.size()-1){
                System.out.print(" + ");
            }
        }
        System.out.print("= ");
        System.out.print(totalMarks());

    }
}
