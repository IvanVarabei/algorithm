package by.org.singleton.concurency.race;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceConditionCheckThenAct {
    public void checkThenAct(Map<String, String> sharedMap) {
        if (sharedMap.containsKey("key")) {
            String val = sharedMap.remove("key");
            if (val == null) {
                System.out.println("Value for 'key' was null");
            }
        } else {
            sharedMap.put("key", "value");
        }
    }

    public static void main(String... args) {
        RaceConditionCheckThenAct rc = new RaceConditionCheckThenAct();
        Map<String, String> sharedMap = new HashMap<>();
        Runnable runnable = () -> rc.checkThenAct(sharedMap);
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
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
    }
}

