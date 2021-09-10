package by.org.singleton.gof.creational.singleton;


public class DoubleCheckSingleton {
    private static volatile DoubleCheckSingleton instance;

    /**
     * localInstance seems unnecessary. The effect of this is that in cases where singleton is already initialized
     * (i.e., most of the time), the volatile field is only accessed once,
     * which can improve the method's overall performance by as much as 25 percent.
     */
    public static DoubleCheckSingleton getInstance() {
        DoubleCheckSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (DoubleCheckSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DoubleCheckSingleton();
                }
            }
        }
        return localInstance;
    }
}