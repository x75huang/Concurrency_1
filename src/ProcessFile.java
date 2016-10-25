import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by Lynne on 2016-10-19.
 */
public class ProcessFile {


    /**
     * Parse the JSON file and get the user list
     */
    private static ArrayList<User> readFile(String json) {

        ArrayList<User> userList = new ArrayList<User>();

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonData = (JSONObject) parser.parse(new FileReader(json));
            JSONArray userArray = (JSONArray) jsonData.get("data");

            for (Object userObj : userArray) {

                User user = new User();
                JSONObject jsonUser = (JSONObject) userObj;

                user.setId((String) jsonUser.get("id"));
                user.setMessage((String) jsonUser.get("message"));
                user.setType((String) jsonUser.get("type"));

                userList.add(user);

            }

            return userList;


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        return userList;
    }

    /**
     * Parse the JSON file and send the user list from one thread to another
     */
    private static void processFile(String json) {
        ArrayList<User> userList = readFile(json);
        BlockingQueue<User> queue = new ArrayBlockingQueue<User>(10);

        if (userList != null && userList.size() > 0) {
            Producer producer = new Producer(queue, userList);
            Consumer consumer = new Consumer(queue);


            producer.start();
            consumer.start();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            producer.stop();
            consumer.stop();

        }
    }


    public static void main(String args[]) {

        processFile("/Users/Lynne/IdeaProjects/Concurrency_1/out/production/Concurrency_1/data.json");

    }
}
