public class test {
    public static class integer{
        private int[] n ;

        public integer(int n){
            this.n = new int[n];
        }

        public void print(){
            System.out.print(n[3]);
        }
    }
    public static void main(String[] args){
        integer in = new integer(7);
        in.print();
    }
}
