package by.org.singleton.recursion;

public class RecursionTest {
    public static void main(String... dsf) throws Exception {
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();

    }

    static class MyThread extends Thread{
        public void run(){
            try {
                show(0);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void show(int i) {
        String s = "" + i;
        try {
            show(++i);
        } catch (StackOverflowError e) {
            throw new MyException(s, e);
        }
    }
}
class MyException extends Error{
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }
}
