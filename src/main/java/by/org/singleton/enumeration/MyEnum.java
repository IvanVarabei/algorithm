package by.org.singleton.enumeration;

public class MyEnum {
    public static final MyEnum BMW = new MyEnum() {
    };
    public static final MyEnum MERS = new MyEnum() {
    };
    Inner inner = new Inner();

    {
        System.out.println("non static block");
    }

    static {
        System.out.println("static block");
    }

    MyEnum() {
        System.out.println("Constructor");
    }

    static class Inner {
        Inner() {
            System.out.println("Inner constructor");
        }
    }
}

