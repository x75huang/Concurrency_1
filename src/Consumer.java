import java.util.concurrent.BlockingQueue;

/**
 * Created by Lynne on 2016-10-20.
 */
public class Consumer {

    private final BlockingQueue queue;

    private ConsumerInner consumerInner;

    private Thread t;

    private class ConsumerInner implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(100);
                    consume(queue.take());
                }
            } catch (InterruptedException ex) {
            }

        }
    }


    private void consume(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            System.out.println("ID: " + user.getId() + ", Message: " + user.getType() + ", Type: " + user.getType() + "---- Queue Size:" + queue.size());
        }
    }

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
        this.consumerInner = new ConsumerInner();
    }

    public void start() {
        t = new Thread(consumerInner);
        t.start();
    }
}
