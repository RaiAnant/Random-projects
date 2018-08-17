import java.util.Scanner;

public class assign_1_q1 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        String name,roll;
        for(int i = 0; i < t ; i++){
            name = s.next();
            roll = s.next();
            Students stu = new Students(name,roll);
            stu.print();
        }
    }
}
