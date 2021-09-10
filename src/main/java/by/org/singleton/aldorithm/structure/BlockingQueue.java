package by.org.singleton.aldorithm.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int size;
    private List<T> list;

    public int getSize() {
        return size;
    }

    public BlockingQueue(int size) {
        list = new ArrayList<>(this.size = size);
    }

    public void put(T element) throws InterruptedException {
        try {
            lock.lock();
            while (list.size() == size) {
                condition.await();
            }
            if (list.isEmpty()) {
                condition.signalAll();
            }
            list.add(element);
        } finally {
            lock.unlock();
        }
    }

    public T peek() throws InterruptedException {
        try {
            lock.lock();
            while (list.isEmpty()) {
                condition.await();
            }
            if (list.size() == size) {
                condition.signalAll();
            }
            return list.remove(0);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < queue.getSize(); i++) {
                try {
                    if (i == 2)
                        Thread.sleep(100);
                    System.out.println("put: " + i);
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < queue.getSize(); i++) {
                try {
                    System.out.println("peek :" + queue.peek());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        t1.start();
    }
}
