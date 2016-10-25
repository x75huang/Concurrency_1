import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Lynne on 2016-10-20.
 */
public class Producer {

    private final BlockingQueue queue;

    private ArrayList<User> userList;

    private ProducerInner producerInner;

    private Thread t;

    private class ProducerInner implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {

                    Thread.sleep(100);

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
        this.t = new Thread(producerInner);
        this.t.start();
    }


}
