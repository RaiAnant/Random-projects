
public class StudentsInTest {
    private String rollNo;
    private Questions[] questions;
    private int index,maxlen;

    public StudentsInTest(int k, String rollNo){
        this.rollNo = rollNo ;
        questions = new Questions[k];
        maxlen = k;
    }

    public Questions[] getQuestions() {
        return questions;
    }

    public String getRollNo() {
        return rollNo;
    }

    public int getIntFromRoll(){
        return Integer.valueOf(rollNo.substring(3));
    }

    public void setQuestion(Questions question){
        questions[index] = question;
        index++;
    }

    public boolean allQuestionsAlloted(){
        return maxlen==questions.length;
    }

}
