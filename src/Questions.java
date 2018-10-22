public class Questions implements Comparable{
    private int qId;
    private String qName;

    public Questions(int qId, String qName){
        this.qId = qId;
        this.qName = qName;
    }

    public int getqId() {
        return qId;
    }

    public String getqName() {
        return qName;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    @Override
    public int compareTo(Object o) {
        Questions q = (Questions)o;
        return this.qId - q.qId;
    }
}
