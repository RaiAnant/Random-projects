public class studentTaMapping {
    private StudentList stu;
    private StudentList ta;
    private int noStudentsPerTa;
    int[] mapping ;

    public void setNoStudentsPerTa(int noStudentsPerTa) {
        this.noStudentsPerTa = noStudentsPerTa;
    }

    public void setStu(String[] name, String[] roll){
        if(name.length==roll.length) {
            this.stu = new StudentList(name.length, name, roll,0);
        }
        else{
            System.out.println("Wrong inputs!");
        }
    }

    public void setTa(String[] name, String[] roll){
        if(name.length==roll.length) {
            this.ta= new StudentList(name.length, name, roll,0);
        }
        else{
            System.out.println("Wrong inputs!");
        }
    }

    public void sortStudents(){
        stu.sort();
    }

    public void setMapping(){
        mapping = new int[stu.getLength()];
        int taIndex = 0;
        int stuIndex = 0;
        while(taIndex<ta.getLength()&&stuIndex<stu.getLength()){
            mapping[stuIndex] = taIndex;
            stuIndex ++;
            if(stuIndex%noStudentsPerTa==0){
                taIndex++;
            }
        }
        for(int i = stuIndex ; i <stu.getLength(); i++){
            mapping[i]=taIndex - 1;
        }
    }

    public void printMapping(){
        for(int i = 0;i<stu.getLength();i++){
            stu.printByIndex(i);
            System.out.print(" ");
            ta.printByIndex(mapping[i]);
            System.out.printf("%n");
        }
    }

    public void printTaWithStu(){
        int stuIndex = 0;
        for(int i=0;i<ta.getLength();i++){
            ta.printByIndex(i);
            System.out.printf("%n");
            while(mapping[stuIndex]==i){
                stu.printByIndex(stuIndex);
                System.out.printf("%n");
                stuIndex++;
                if(stuIndex>=stu.getLength()){
                    break;
                }
            }
        }
    }
}
