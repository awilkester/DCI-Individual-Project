package main.java.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static main.java.TheWarehouseApp.IS_EMPLOYEE;
import static main.java.TheWarehouseApp.SESSION_USER;


/**
 * The Data Repository
 *
 * @author pujanov
 */
public class UserRepository {

    private static List<Employee> EMPLOYEE_LIST = new ArrayList<>();
    private static List<Admin> ADMIN_LIST = new ArrayList<>();

    /**
     * Load employee, records from the personnel.json file
     */
    static {
        // System.out.println("Loading items");
        BufferedReader reader = null;
        try {
            EMPLOYEE_LIST.clear();

            reader = new BufferedReader(new FileReader("src/main/resources/personnel.json"));
            Object data = JSONValue.parse(reader);
            if (data instanceof JSONArray) {
                JSONArray dataArray = (JSONArray) data;
                for (Object obj : dataArray) {
                    if (obj instanceof JSONObject) {
                        JSONObject jsonData = (JSONObject) obj;
                        String userName = jsonData.get("user_name").toString();
                        String password = jsonData.get("password").toString();
                        String role = jsonData.get("role").toString();
                        if(role.equals("EMPLOYEE")){
                            Employee employee = new Employee(userName, password, null);
                            EMPLOYEE_LIST.add(employee);
                        }else if(role.equals("EMPLOYEE")){
                            Admin admin = new Admin(userName, password, null);
                            ADMIN_LIST.add(admin);
                        }
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
     * Get All users
     *
     * @return
     */
    public static List<Employee> getAllEmployees() {
        return EMPLOYEE_LIST;
    }
    public static List<Admin> getAllAdmins() {
        return ADMIN_LIST;
    }

  //// Add isUserAdmin and isAdminValid methods and implement them in similar fashion as isUserEmployee
  //// and isEmployeeValid methods (but for Admin) to return the correct boolean value.
  public static boolean isUserValid(String userName, String password) {
        for(Employee employee : getAllEmployees()) {
            if(userName.equals(employee.getName()) && password.equals(employee.getPassword())) {
                    return true;
            }
        }
        return false;
    }
    //isUserEmployee which takes the employees name and return a boolean value as per the existence of the name in usernel.json.
    public static boolean isUserEmployee(String name) {
        for(Employee employee : getAllEmployees()) {
            if(name.equals(employee.toString())){
                return true;
            }
        }
        return false;
    }

    public static String askPassword(String userName, Scanner scanner){
        System.out.printf("Please input your password, %s.%n", userName);
        return scanner.nextLine();
    }

    public static boolean validateUser(String userName, Scanner scanner, String password){
        do{
            if(isUserValid(userName, password)){
                System.out.println("Thank you for verifying your identity.");
                IS_EMPLOYEE = true;
                return true;
            }else{
                System.out.println("There was a problem verifying your identity. \n" +
                        "Would you like to change either your username or your password? (y/n)");
                if(scanner.nextLine().toLowerCase().startsWith("y")){
                    userName = changeUserName(scanner);
                    SESSION_USER.setName(userName);
                    password = askPassword(userName, scanner);
                    return validateUser(userName, scanner, password);
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