package by.org.singleton.concurency;

public class DeadLock {
    public static void main(String[] sss) {
        A a = new A();
        B b = new B();
        Thread t1 = new Thread(() -> a.a1(b));
        Thread t2 = new Thread(() -> b.b1(a));
        t1.start();
        t2.start();
    }
}

class A {
    synchronized void a1(B b) {
        try {
            Thread.sleep(100);
            System.out.println("a1");
        } catch (InterruptedException e) {
        }
        b.b2();
    }

    synchronized void a2() {
        System.out.println("a2");
    }
}

class B {
    synchronized void b1(A a) {
        try {
            Thread.sleep(100);
            System.out.println("b1");
        } catch (InterruptedException e) {
        }
        a.a2();
    }

    synchronized void b2() {
        System.out.println("b2 never");
    }
}

