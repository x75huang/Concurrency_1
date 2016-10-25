import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lynne on 2016-10-20.
 */
public class Producer {

    private final BlockingQueue queue;

    private ArrayList<User> userList;

    private ProducerInner producerInner;

    private Thread t;

    private Lock lock = new ReentrantLock();

    private class ProducerInner implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {

                    t.sleep(100);

                    if (userList != null) {
                        for (User user : userList) {
                            queue.put(user);
                        }

                    }
                }
            } catch (InterruptedException ex) {
            }

        }
    }

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public Producer(BlockingQueue queue, ArrayList<User> userList) {
        this.queue = queue;
        this.userList = userList;
        this.producerInner = new ProducerInner();
    }

    public void start() {
        lock.lock();
        t = new Thread(producerInner);
        t.setDaemon(true);
        t.start();
        lock.unlock();
    }

    public void stop() {
        if (t != null) {
            t.interrupt();
        }
    }


}
