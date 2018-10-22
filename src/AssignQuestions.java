import java.util.*;

import static java.lang.Math.abs;

public class AssignQuestions {
    private Questions[] questions ;
    private int index;
    private Map<String,StudentsInTest> studentList;
    private String[] rollNos;
    private int rollIndex;
    private int maxQid;
    private int minQid;
    private Questions maxQuestion = new Questions(0,null);
    private int noAllotedQues = 0;


    public void insertStudent(StudentsInTest student){
        studentList.put(student.getRollNo(),student);
        rollNos[rollIndex] = student.getRollNo();
        rollIndex++;
        Arrays.sort(rollNos);
    }

    public void assignQuestions(){
        for (int i = 0; i < rollNos.length; i++) {
            assignQuestionToStudent(studentList.get(rollNos[i]));
        }
    }

    public void assignQuestionToStudent(StudentsInTest student){
        maxQuestion.setqId(maxQid+1);
        int intRoll = student.getIntFromRoll();
        for (int i = 0; i < questions.length -1 - noAllotedQues ; i--) {
            if(abs(intRoll-questions[i].getqId())>abs(intRoll-questions[i+1].getqId())){
                continue;
            }else {
                student.setQuestion(questions[i]);
                questions[i] = maxQuestion;
                Arrays.sort(questions);
                i=i-2;
                if(i<0){
                    i=0;
                }
                noAllotedQues++;
            }
            if(student.allQuestionsAlloted()){
                break;
            }
        }
    }

    public void removeStudent(String rollNo){
        studentList.remove(rollNo);
    }

    public int getIntFromRoll(String rollNo){
        return Integer.valueOf(rollNo.substring(3));
    }

    public AssignQuestions(int q,int n){
        studentList = new HashMap<String, StudentsInTest>();
        questions = new Questions[q];
        rollNos = new String[n];
    }

    public void insertQuestion(Questions question){
        questions[index]=question;
        index++;
        int qid = question.getqId();
        if(questions.length==1){
            maxQid = qid;
            minQid = qid;
        }else if(qid>maxQid){
            maxQid = qid;
        }else if(qid<minQid){
            minQid = qid;
        }

        Arrays.sort(questions);
    }

    public StudentsInTest getStudent(String roll){
        return studentList.get(roll);
    }

}
