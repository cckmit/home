package test;

public class son extends father {

    static int counter = 10;

    public void count2() {

        System.out.println("2");
        super.count();
    }

    public static void main(String[] args){
        son p = new son();
        p.count2();
    }
}
