package main.java.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * The Data Repository
 *
 * @author riteshp
 */
public class Repository {

    private static final List<Item> ITEM_LIST = new ArrayList<>();

    /*
     * Load item records from the data.json file
     */
    static {
        // System.out.println("Loading items");
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     Objects.requireNonNull(
                                             Repository.class.getClassLoader().getResourceAsStream("data.json"))))) {

            Object data = JSONValue.parse(reader);
            if (data instanceof JSONArray) {
                JSONArray dataArray = (JSONArray) data;
                for (Object obj : dataArray) {
                    if (obj instanceof JSONObject) {
                        JSONObject jsonData = (JSONObject) obj;
                        Item item = new Item();
                        item.setState(jsonData.get("state").toString());
                        item.setCategory(jsonData.get("category").toString());
                        item.setWarehouse(Integer.parseInt(jsonData.get("warehouse").toString()));
                        String date = jsonData.get("date_of_stock").toString();
                        // System.out.println("Item Date " + date);
                        item.setDateOfStock(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));
                        // System.out.println(item);

                        ITEM_LIST.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get All items available in the repository
     *
     * @return the list of items
     */
    public static List<Item> getAllItems() {
//        List<Item> result = new ArrayList<>();
//        for(Item x: ITEM_LIST) {
//            result.add(x);
//        }
//        return result;
        return ITEM_LIST;
    }

    // By Warehouse
    /**
     * Get the list of unique warehouse IDs
     *
     * @return the warehouses
     */
    public static Set<Integer> getWarehouses() {
        HashSet<Integer> result = new HashSet<>();
        for(Item x : getAllItems()) {
            result.add(x.getWarehouse());
        }
        return result;
    }

    /**
     * Get the list of all items in a specific warehouse
     *
     * @param warehouse the warehouse ID
     * @return the items
     */
    public static List<Item> getItemsByWarehouse(int warehouse) {
        List<Item> result = new ArrayList<>();
        for(Item x : ITEM_LIST){
            if(x.getWarehouse() == warehouse){
                result.add(x);
            }
        }
        return result;
    }

    /**
     * Get the list of items related to a specific warehouse in a given master-list
     *
     * @param warehouse the warehouse ID
     * @return the items
     */
    public static List<Item> getItemsByWarehouse(int warehouse, List<Item> masterList) {
        List<Item> result = new ArrayList<>();
        for(Item x : masterList){
            if(x.getWarehouse() == warehouse){
                result.add(x);
            }
        }
        return result;
    }

    // By Category
    /**
     * Get the list of unique Categories
     *
     * @return the categories
     */
    public static Set<String> getCategories() {
        Set<String> result = new HashSet<>();
        for(Item x : getAllItems()){
            result.add(x.getCategory());
        }
        return result;
    }

    /**
     * Get the list of all items of a specific category
     *
     * @param category the category
     * @return the items
     */
    public static List<Item> getItemsByCategory(String category) {
        List<Item> result = new ArrayList<>();
        for(Item x: getAllItems()){
            if(x.getCategory() == category){
            result.add(x);
            }
        }
        return result;
    }

    /**
     * Get the list of items of a specific category in a given master-list
     *
     * @param category the category
     * @return the items
     */
    public static List<Item> getItemsByCategory(String category, List<Item> masterList) {
        List<Item> result = new ArrayList<>();
        for(Item x: masterList){
            if(x.getCategory() == category){
                result.add(x);
            }
        }
        return result;
    }
}