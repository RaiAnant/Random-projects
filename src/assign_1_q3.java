import java.util.Scanner;

public class assign_1_q3 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int testCase = s.nextInt();
        while (testCase>0) {
            testCase--;
            int t = s.nextInt();
            int n = s.nextInt();
            int k = s.nextInt();
            studentTaMapping stuTaMap = new studentTaMapping();
            stuTaMap.setNoStudentsPerTa(k);

            String[] taName = new String[t];
            String[] taRoll = new String[t];
            for(int i=0;i<t;i++){
                taRoll[i] = s.next();
                taName[i] = s.next();
            }
            stuTaMap.setTa(taName,taRoll);

            String[] stuName = new String[n];
            String[] stuRoll = new String[n];
            for(int i=0;i<n;i++){
                stuRoll[i] = s.next();
                stuName[i] = s.next();
            }
            stuTaMap.setStu(stuName,stuRoll);

            stuTaMap.sortStudents();
            stuTaMap.setMapping();
            stuTaMap.printMapping();
            stuTaMap.printTaWithStu();
        }
    }
}
