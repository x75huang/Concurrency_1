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

    private ArrayList<User> userList;

    private String json;
    private User user;

    public ProcessFile() {

    }

    public ProcessFile(String json) {
        this.json = json;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

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


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

            new Thread(producer).start();
            new Thread(consumer).start();
        }
    }


    public static void main(String args[]) {

        processFile("/Users/Lynne/IdeaProjects/Concurrency_1/out/production/Concurrency_1/data.json");

    }
}
