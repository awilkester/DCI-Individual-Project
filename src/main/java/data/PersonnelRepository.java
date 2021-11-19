package main.java.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Data Repository
 *
 * @author riteshp
 *
 */
public class PersonnelRepository {

    private static List<Person> PERSON_LIST = new ArrayList<Person>();

    /**
     * Load item records from the stock.json file
     */
    static {
        // System.out.println("Loading items");
        BufferedReader reader = null;
        try {
            PERSON_LIST.clear();

            reader = new BufferedReader(new FileReader("src/main/resources/personnel.json"));
            Object data = JSONValue.parse(reader);
            if (data instanceof JSONArray) {
                JSONArray dataArray = (JSONArray) data;
                for (Object obj : dataArray) {
                    if (obj instanceof JSONObject) {
                        JSONObject jsonData = (JSONObject) obj;
                        Person person = new Person();
                        person.setUserName(jsonData.get("user_name").toString());
                        person.setPassword(jsonData.get("password").toString());
                        //person.setHeadOf((Person) jsonData.getOrDefault("head_of", null));
                        PERSON_LIST.add(person);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Get All persons
     *
     * @return
     */
    public static List<Person> getAllPersons() {
        return PERSON_LIST;
    }

    public static String askPassword(String userName, Scanner scanner){
        System.out.println("Please input your password, " + userName + ".");
        return scanner.nextLine();
    }

    public static boolean isUserValid(String userName, String password) {
        for (Person person :getAllPersons()){
            if(person.getUserName().equals(userName) && person.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public static boolean validateUser(String userName, Scanner scanner){
        String password = askPassword(userName, scanner);
        do{
            if(isUserValid(userName, password)){
                System.out.println("Thank you for verifying your identity.");
                return true;
            }else{
                System.out.println("There was a problem verifying your identity. \n" +
                        "Would you like to change either your username or your password? (y/n)");
                if(scanner.nextLine().toLowerCase().startsWith("y")){
                    userName = changeUserName(scanner);
                    return validateUser(userName, scanner);
                }else{
                    return false;
                }
            }
        }while(!isUserValid(userName, password));
    }
    public static String changeUserName(Scanner scanner){
        System.out.println("Please input your username.");
        return scanner.nextLine();
    }
}