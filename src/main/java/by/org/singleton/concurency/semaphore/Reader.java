package by.org.singleton.concurency.semaphore;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Reader implements Runnable {
    static Semaphore semaphore = new Semaphore(3);

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread() + " entered library");
            System.out.println(Thread.currentThread() + " in the library");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread() + " leaved library");
            semaphore.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... jsdlf) throws SQLException {
        String text = "hello word, hello word";
        Map<String, Integer> wordAmount = findFacingAmount(text);
        Set<String> keys = wordAmount.keySet();
        for (String key : keys) {
            System.out.printf("%s : %s%n", key, wordAmount.get(key));
        }
    }

    public static Map<String, Integer> findFacingAmount(String text){
        if(text == null || text.isEmpty()){
            return Collections.emptyMap();
        }
        String [] words = text.split("(\\s)|(,\\s)|(\\.\\s)|(!\\s)");
        Map<String ,Integer> wordAmount = new HashMap<>();
        Arrays.stream(words).forEach(w-> wordAmount.put(w, 0));
        Arrays.stream(words).forEach(w -> {
            int amount = wordAmount.get(w);
            wordAmount.put(w, ++amount);
        });
        return wordAmount;
    }
}
