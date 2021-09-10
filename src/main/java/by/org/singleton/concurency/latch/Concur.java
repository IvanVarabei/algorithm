package by.org.singleton.concurency.latch;

import java.util.Arrays;
import java.util.concurrent.*;

public class Concur {
    public static void main(String... sdf) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(RocketDetail.values().length);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(RocketDetail.values().length, () -> System.out.println("START"));
        ExecutorService executor = Executors.newFixedThreadPool(20);

//        executor.execute(new Rocket(countDownLatch));
//        Arrays.stream(RocketDetail.values())
//                .map(rocketDetail -> new RocketDetailRunnable(rocketDetail, countDownLatch))
//                .forEach(executor::execute);

        //CyclicBarrier
        Arrays.stream(RocketDetail.values())
                .map(rocketDetail -> new RocketDetailRunnable(rocketDetail, cyclicBarrier))
                .forEach(executor::execute);

        executor.shutdown();
        //System.out.println(executor.awaitTermination(1L, TimeUnit.SECONDS));
        //System.out.println("after");
    }
}






















/*
 static final class FieldHelper {

        private static final VarHandle MODIFIERS;

        static {
            try {
                var lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
                MODIFIERS = lookup.findVarHandle(Field.class, "modifiers", int.class);
            } catch (IllegalAccessException | NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
        }

        public static void makeNonFinal(Field field) {
            int mods = field.getModifiers();
            if (Modifier.isFinal(mods)) {
                MODIFIERS.set(field, mods & ~Modifier.FINAL);
            }
        }

    }
 */