package by.org.singleton.concurency.race;

import java.util.ArrayList;
import java.util.List;

public class RaceConditionReadModifyWrite {
    static class Counter {
        protected long count = 0;

        public void add(long value) {
            count = count + value;
        }
    }

    public static void main(String... args) {
        Counter counter = new Counter();
        Runnable runnable = () -> counter.add(1);
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 20_000; i++) {
            threadList.add(new Thread(runnable));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter.count);
    }
}
/**
 * this.count = 0;
 * <p>
 * A:  Reads this.count into a register (0)
 * B:  Reads this.count into a register (0)
 * B:  Adds value 2 to register
 * B:  Writes register value (2) back to memory. this.count now equals 2
 * A:  Adds value 3 to register
 * A:  Writes register value (3) back to memory. this.count now equals 3
 */
