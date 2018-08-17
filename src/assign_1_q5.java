import java.util.Scanner;

public class assign_1_q5 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t =s.nextInt();
        while(t>0){
            t--;
            int n = s.nextInt();
            Students[] students = new Students[n];

            int assNo = s.nextInt();
            String[] assignName = new String[assNo];
            int marks;
            for(int i = 0 ;i<assNo;i++){
                assignName[i] = s.next();
                int q=s.nextInt();
                for(int j =0;j<q;j++){
                    String roll = s.next();
                    marks = s.nextInt();
                    for(int k = 0;k<n;k++){
                        if(roll.compareTo(students[k].getRoll())==0){
                            students[k].setNextAssign(assignName[i]);
                            students[k].setAssignMarks(marks);
                        }
                    }
                }
            }
            for(Students stu:students){
                stu.printResult();
            }
        }
    }
}
