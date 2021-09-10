package by.org.singleton.concurency.latch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class RocketDetailRunnable implements Runnable {
    private RocketDetail rocketDetail;
    private CountDownLatch countDownLatch;
    private CyclicBarrier cyclicBarrier;

    public RocketDetailRunnable(RocketDetail rocketDetail, CountDownLatch countDownLatch) {
        this.rocketDetail = rocketDetail;
        this.countDownLatch = countDownLatch;
    }

    public RocketDetailRunnable(RocketDetail rocketDetail, CyclicBarrier cyclicBarrier) {
        this.rocketDetail = rocketDetail;
        this.cyclicBarrier = cyclicBarrier;
    }

    // CyclicBarrier
    @Override
    public void run() {
        System.out.println("The detail is being prepared : " + rocketDetail);
        try {
            Thread.sleep(1000);
            System.out.println(rocketDetail + " is ready");
            cyclicBarrier.await();
            System.out.println("after await");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("The detail " + rocketDetail + " was used");
    }

//    @Override
//    public void run() {
//        System.out.println("The detail is being prepared : " + rocketDetail);
//        try {
//            Thread.sleep(1000);
//            System.out.println(rocketDetail + " is ready");
//            countDownLatch.countDown();
//            System.out.println("after countdown");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("The detail " + rocketDetail + " was used");
//    }
}
