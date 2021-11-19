package main.java;

import main.java.data.Item;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static main.java.data.PersonnelRepository.*;
import static main.java.data.StockRepository.*;

/**
 * Provides necessary methods to deal through the Warehouse management actions
 *
 * @author riteshp
 */
public class TheWarehouseManager {
    Session session = new Session();
    // =====================================================================================
    // Member Variables
    // =====================================================================================

    // To read inputs from the console/CLI
    private final Scanner reader = new Scanner(System.in);
    private final String[] userOptions = {
            "1. List items by warehouse", "2. Search an item and place an order", "3. Browse by category", "4. Quit"
    };
    // To refer the user provided name.
    private String userName;
    private boolean validUser = false;

    // =====================================================================================
    // Public Member Methods
    // =====================================================================================

    /** Welcome User */
    public void welcomeUser() {
        this.seekUserName();
        this.greetUser();
    }

    /** Ask for user's choice of action */
    public int getUsersChoice() {
    System.out.println("Your options are:");
        for (String option : this.userOptions) {
            System.out.println(option);
        }
    System.out.println("Type a number to select that option.");
        int choice = reader.nextInt();
        reader.nextLine();
    return choice;
    }

    /** Initiate an action based on given option */
    public void performAction(int option) {
        switch(option){
            case 1:
                listItemsByWarehouse();
                break;
            case 2:
                searchItemAndPlaceOrder();
                break;
            case 3:
                browseByCategory();
                break;
            case 4:
                quit();
            default:
                System.out.println("Sorry, you've selected an invalid option. Please try again.");
        }
    }

  /**
   * Confirm an action
   *
   * @return action
   */
  public boolean confirm() {
//    do{
        System.out.println("Do you want to perform another action?\n"
                + "Type 'yes' to continue, and 'no' to quit.");
//    } while(! reader.nextLine().charAt(0).equals('y'));
      return reader.nextLine().charAt(0) == 'y';
  }

    /** End the application */
    public void quit() {
        System.out.printf("\nThank you for your visit, %s!\n", this.userName);
        if(session.getSessionActions().size() > 0){
            System.out.println("In this session you have :");
            session.listSessionActions();
        }else{
            System.out.println("In this session you have not done anything.");
        }

        System.exit(0);
    }

    // =====================================================================================
    // Private Methods
    // =====================================================================================

    /** Get user's name via CLI */
    private void seekUserName() {
    System.out.println("Welcome to Andrew's Warehouse Manager App. Please input your name to continue: ");
    userName = reader.nextLine();
    }

    /** Print a welcome message with the given user's name */
    private void greetUser() {
    System.out.println("Hello " + userName + "!");
    }

  /**
   * It should use the Repository.getWarehouses() method, which should
   * return a Set<Integer>. This set represents a set of IDs of each warehouse.
   *
   * With those IDs, you should call the method Repository.getItemsByWarehouse(warehouse) on each
   * to retrieve a List<Item>. Finally, you should print each item.
   */
  private void listItemsByWarehouse() {
      Set<Integer> x = getWarehouses();
      for(int i = 0; i < x.size(); i++) {
        List<Item> items = getItemsByWarehouse(i);
        this.listItems(i, items);
      }
      printNumberOfItemsByWarehouse();
    }

  private void listItems(int warehouse, List<Item> x) {
    System.out.println("Items in Warehouse " + warehouse + ": ");
      for (Item z: x){
          System.out.println("- " + z.toString());
      }
      session.addToSession(1,"n/a");
  }

  private void printNumberOfItemsByWarehouse(){
    System.out.println();
    for(int x: getWarehouses()){
      System.out.println("Total items in warehouse " + x + ": " + getItemsByWarehouse(x).size());
    }
  }

    private void searchItemAndPlaceOrder() {
    System.out.println("You've selected to search for an item and place an order for it.");
    if(validUser){
        String itemName = askItemToOrder();
        printAmountAvailable(itemName);
        printLocations(itemName);
        if(getAvailableAmount(itemName) > 0){
            printMaximumAvailability(itemName);
            System.out.println("Would you like to place an order for this item?");
            if(reader.nextLine().toLowerCase().charAt(0) == 'y'){
                askAmountAndConfirmOrder(getAvailableAmount(itemName), itemName);
            }
        }
        session.addToSession(2,itemName);
    }else{
        if(validateUser(userName, reader)){
            validUser = true;
            searchItemAndPlaceOrder();
        }
    }

    }



    /**
     *
     * @param item
     */
  private void printAmountAvailable(String item){
      System.out.println("There are " + getAvailableAmount(item) + " " + item + " available.");
  }
    /**
     * If the search returns at least one result (in any warehouse),
     *  it prints a list of all the items showing the name of the warehouse
     *  and the number of days it has passed since they were stocked.
     * @param item String itemName
     */
  private void printLocations(String item){
    if(getAvailableAmount(item) > 0){
        System.out.println("Location:");
        for(Item x : getAllItems()){
            if((x.toString().equals(item))){
                System.out.println("- Warehouse " + x.getWarehouse() + " (in stock for " + daysSinceStocked(x) +" days.)");
            }
        }
    } else{
        System.out.println("Location: Not in stock");
    }
  }

    /**
     * It still prints the maximum availability only when the item is found in more than one warehouse.
     * @param item String itemName
     */
  private void printMaximumAvailability(String item){
      int result = 0;
      int resultWarehouse = 0;
        for(int i = 0; i < availableNumberPerWarehouse(item).size(); i++){
            if(availableNumberPerWarehouse(item).get(i) > result){
                result = availableNumberPerWarehouse(item).get(i);
                resultWarehouse = i;
            }
        }
        System.out.println("Maximum Availability: " + result + " in Warehouse " + resultWarehouse);
  }

    /**
     *
     * @param item
     * @return
     */
  private long daysSinceStocked(Item item){
      LocalDate date = LocalDate.now();
      LocalDate itemStockDate = item.getDateOfStock().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      return ChronoUnit.DAYS.between(itemStockDate, date);
  }

  /**
   * @return String itemName
   */
  private String askItemToOrder() {

    System.out.println("Please input the item you'd like to search for: ");
    return reader.nextLine().toLowerCase();
    }


    /**
     * Calculate total availability of the given item
     *
     * @param itemName itemName
     * @return integer availableCount
     */
    private int getAvailableAmount(String itemName) {
        int count = 0;
        for(int x : getWarehouses()){
            count += find(itemName, x);
        }
        return count;
    }


    /**
     * Find the count of an item in a given warehouse
     *
     * @param item the item (as a concatenation of the state and the category from the Item class)
     * @param warehouse the warehouse (as an int now, from getWarehouses set)
     * @return count
     */
    private int find(String item, int warehouse) {
        int count = 0;
        for(Item x: getItemsByWarehouse(warehouse)){
            if(item.equals(x.toString())){
                count++;
            }
        }
        return count;
    }

    /**
     * returns a list of number of items available for a given item name per warehouse
     * @param item
     * @return
     */
    private List<Integer> availableNumberPerWarehouse(String item){
        Set<Integer> x = getWarehouses();
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < x.size(); i++){
           result.add(find(item, i));
        }
        return result;
    }

    /** Ask order amount and confirm order */
    private void askAmountAndConfirmOrder(int availableAmount, String item) {
        int orderAmount = getOrderAmount(availableAmount);
        System.out.println(orderAmount + " " + item + " have been ordered.");
            }


    /**
     * Get amount of order
     *
     * @param availableAmount
     * @return
     */
    private int getOrderAmount(int availableAmount) {
        System.out.println("How many would you like to order? Please enter a whole positive number.");
        int orderAmount = reader.nextInt();
        reader.nextLine();
        if(orderAmount > availableAmount){
            System.out.println("Sorry, your order value is more than we have currently available. \n" +
                    "Would you like to order the maximum available instead? (y / n)");
            if(reader.nextLine().toLowerCase().charAt(0) == 'y'){
                orderAmount = availableAmount;
            }
        }
        return orderAmount;
    }
  // Show a menu of available categories. This menu will have to include a numeric code (the number
  // the user will type in to select a category), the name of the category and the amount of items
  // available in that category (in any warehouse).
  //
  // There is no list of categories in the dataset, so you will have to iterate all the stock to
  // identify the categories and count their items.
  //
  // You will also have to find a way to produce a numeric identifier for each category.
  //
  // The menu list should show single categories (each category should only appear once).
  //
  // Ask the user to type the category number of their choice.
  //
  // List all items in that category. This time, print them one after the other (not separated by
  // warehouse) and include the name of the warehouse at the end of each line.
  //
  // Be aware that the code associated to each category will be an auto-generated numeric code and
  // the items have a text value as category. You will have to think of a way to identify each
  // number typed by the user to the correct category name to be able to filter the stock.


    private void browseByCategory() {
    Map<Integer, String> menu = createCategoryMenu();
    printCategoryMenu(menu);
    int choice = chooseCategory();
    printCategoryItems(choice, menu);
    session.addToSession(3, menu.get(choice));
    }

    /**
     * this asks for a category by string, and returns the total amount of items in
     * any warehouse that match that category
     * @param category
     * @return
     */
    private int amountOfItemsPerCategory(String category){
        return getItemsByCategory(category).size();
    }

    /**
     *  creates a map of numbers and categories, retains its order of insertion.
     * @return a LinkedHashMap of categories.
     * LinkedHashMap because all that matters is order of insertion,
     * I won't be changing it or needing to sort it after that,
     * but I still need the order for printing out.
     */
    private Map<Integer, String> createCategoryMenu(){
        Map<Integer, String> result = new LinkedHashMap<>();
        int count = 1;
        for(String category: getCategories()){
            result.put(count, category);
            count++;
        }
        return result;
    }

    /**
     * prints the CategoryMenu with a java 10+ version for each iterator
     * @param x
     */
    private void printCategoryMenu(Map<Integer, String> x){
        for(var entry : x.entrySet()) {
            System.out.printf("%d. %s (%d)%n", entry.getKey(), entry.getValue(), amountOfItemsPerCategory(entry.getValue()));
        }
    }

    /**
     *
     * @return the chosen integer corresponding to a category
     */
    private int chooseCategory(){
        int result = 0;
        do{
            System.out.println("Please select a category to browse by typing the number associated with the category.");
            result = reader.nextInt();
            reader.nextLine();
            if(result < getCategories().size() || result > getCategories().size()){
        System.out.println("You have typed an incorrect number.");
            }
        }while(result < getCategories().size() || result > getCategories().size());
        return result;
    }

    private void printCategoryItems(int choice, Map<Integer, String> x){
        String category = x.get(choice);
        System.out.printf("List of %ss available:%n", category);
        for (Item item: getItemsByCategory(category)){
            System.out.printf("%s %s, Warehouse %d%n", item.getState(), category, item.getWarehouse());
        }
    }
//    public int getTotalListedItems(){
//        return getAllItems().size();
//    }
}