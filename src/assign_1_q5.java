import java.util.Scanner;

public class assign_1_q5 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t =s.nextInt();
        while(t>0){
            t--;
            int n = s.nextInt();

            int noAssign = s.nextInt();
            String[] rolls = new String[n];
            String[] names = new String[n];
            for(int i = 0 ; i < n ; i++){
                rolls[i] = s.next();
                names[i] = s.next();
            }
            StudentList studentList = new StudentList(n,names,rolls,noAssign);
            studentList.sort();
            for(int i = 0 ;i < noAssign;i++){
                String assignName = s.next();
                int q = s.nextInt();
                String[] attendance = new String[q];
                int[] marks = new int[q];
                for(int j = 0; j < q ;j++){
                    attendance[j] = s.next();
                    marks[j] = s.nextInt();
                }
                studentList.allotMarks(assignName,attendance,marks);
            }

            studentList.printFullResult();
        }
    }
}
