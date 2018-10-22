import java.util.Scanner;

public class Assign_3_q1 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        while(t>0) {
            t--;
            int q = s.nextInt();
            int n = s.nextInt();
            int k = s.nextInt();
            AssignQuestions assignQuestions = new AssignQuestions(q,n);
            for (int i = 0; i < q; i++) {
                int qId = s.nextInt();
                String qName = s.next();
                assignQuestions.insertQuestion(new Questions(qId,qName));
            }
            for (int i = 0; i < n; i++) {
                String rollNo = s.next();
                assignQuestions.insertStudent(new StudentsInTest(k,rollNo));
            }
            assignQuestions.assignQuestions();
            for (int i = 0; i < n; i++) {
                String rollNo = s.next();
                System.out.print(rollNo+"\n");
                for (int j = 0; j < k; j++) {
                    System.out.print(assignQuestions.getStudent(rollNo).getQuestions()[j].getqName()+"\n");
                }
            }
        }
    }

}
