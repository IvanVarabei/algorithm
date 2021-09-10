package by.org.singleton.enumeration;

enum Car {
    BMW();
    Car.Inner inner = new Car.Inner();

    {
        System.out.println("non static block");
    }

    static {
        System.out.println("static block");
    }

    Car() {
        System.out.println("Constructor");
    }

    static class Inner {
        Inner() {
            System.out.println("Inner constructor");
        }
    }
}
