package by.org.singleton.concurency.latch;

import java.util.concurrent.CountDownLatch;

public class Rocket implements Runnable{
    private CountDownLatch countDownLatch;

    public Rocket(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("The rocket is preparing to the start");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("START!");
    }
}
