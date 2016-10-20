import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Lynne on 2016-10-20.
 */
public class Producer implements Runnable {

    private final BlockingQueue queue;

    private ArrayList<User> userList;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public Producer(BlockingQueue queue, ArrayList<User> userList) {
        this.queue = queue;
        this.userList = userList;
    }

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
