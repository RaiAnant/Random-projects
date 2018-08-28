import java.util.Scanner;

public class assign_1_q2 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        for(int i = 0 ;i <t ;i++){
            int n = s.nextInt();
            String[] name = new String[n];
            String[] roll = new String[n];
            for(int j = 0; j < n ; j++){
                roll[j] = s.next();
                name[j] = s.next();
            }
            StudentList stuLi = new StudentList(n,name,roll,0);
            stuLi.sort();
            stuLi.printAll();
        }

    }
}
