package main.java;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static main.java.Repository.*;

/**
 * Provides necessary methods to deal through the Warehouse management actions
 *
 * @author riteshp
 */
public class TheWarehouseManager {
    // =====================================================================================
    // Member Variables
    // =====================================================================================

    // To read inputs from the console/CLI
    private final Scanner reader = new Scanner(System.in);
    private final String[] userOptions = {
            "1. List items by warehouse", "2. Search an item and place an order", "3. Quit"
    };
    // To refer the user provided name.
    private String userName;

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
    System.out.println("Your options are: \n" + userOptions);
    return reader.nextInt();
    }

    /** Initiate an action based on given option */
    public void performAction(int option) {
        System.out.println( "You've selected option " + option + ", is that what you wanted? Enter 'yes' to continue.");
    if (reader.nextLine().charAt(0) == 'y') {
      if (option == 1) {
        listItemsByWarehouse();
      } else if (option == 2) {
        searchItemAndPlaceOrder();
      } else if (option == 3) {
        quit();
      } else {
        System.out.println("Sorry, you've selected an invalid option. Please try again.");
      }
      }else{getUsersChoice();}
    }

  /**
   * Confirm an action
   *
   * @return action
   */
  public boolean confirm() {
    System.out.println("Do you want to perform another action?\n"
            + "Type 'yes' to continue, and 'no' to quit.");
      return reader.nextLine().charAt(0) == 'y';
  }

    /** End the application */
    public void quit() {
        System.out.printf("\nThank you for your visit, %s!\n", this.userName);
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
      Set x = getWarehouses();
      for(int i = 0; i < x.size(); i++) {
        List<Item> items = getItemsByWarehouse(i);
        listItems(i, items);
      }
    quit();
    }

  private void listItems(int warehouse, List<Item> x) {
    System.out.println("Items in Warehouse " + warehouse + ": ");
      for (Item z: x){
          System.out.println(z.getState() + z.getCategory());
      }
  }

    private void searchItemAndPlaceOrder() {
    System.out.println("You've selected to search for an item and place an order for it.");
    String itemName = askItemToOrder();
    int availableInW1 = (find(itemName, WAREHOUSE1));
    int availableInW2 = (find(itemName, WAREHOUSE2));
    if(availableInW1 > 0 && availableInW2 > 0){
        System.out.println("There are " + getAvailableAmount(itemName) + " "+ itemName + "s available in both warehouses.");
        if(availableInW1 > availableInW2){
            System.out.println("Warehouse 1 has more " + itemName + "s than warehouse 2, at " + availableInW1 +" items.");
        } else if(availableInW1 < availableInW2){
            System.out.println("Warehouse 1 has more " + itemName + "s than warehouse 2, at " + availableInW1 +" items.");
        } else{
            System.out.println("Both warehouses have the same number of " + itemName + "s, at " + availableInW1);
        }

    } else if(availableInW1 > 0 && availableInW2 == 0){
        System.out.println("There are " + getAvailableAmount(itemName) + " "+ itemName + "s available in Warehouse 1. ");

    } else if(availableInW1 == 0 && availableInW2 > 0){
        System.out.println("There are " + getAvailableAmount(itemName) + " "+ itemName + "s available in Warehouse 2. ");
        askItemToOrder();
    } else {
      System.out.println("Sorry, that item is not found in our warehouses.");
      quit();
    }


    }

  /**
   * @return String itemName
   */
  private String askItemToOrder() {
    System.out.println("Please input the item you'd like to search for: ");
    return reader.nextLine();
    }

    /**
     * Calculate total availability of the given item
     *
     * @param itemName itemName
     * @return integer availableCount
     */
    private int getAvailableAmount(String itemName) {
        return find(itemName, WAREHOUSE1) + find(itemName,WAREHOUSE2);
    }

    /**
     * Find the count of an item in a given warehouse
     *
     * @param item the item
     * @param warehouse the warehouse
     * @return count
     */
    private int find(String item, String[] warehouse) {
        int count = 0;
        for(String x: warehouse){
            if(item == x){
                count++;
            }
        }
        return count;
    }

    /** Ask order amount and confirm order */
    private void askAmountAndConfirmOrder(int availableAmount, String item) {
        // TODO
        System.out.println("How many would you like to order? Please enter a whole positive number.");
        int orderAmount = reader.nextInt();
        if(orderAmount <= availableAmount){
            getOrderAmount(orderAmount);
        } else {
            System.out.println("Sorry, your order value is more than we have currently available. \n" +
                    "Would you like to order the maximum available instead?");
        }
    }

    /**
     * Get amount of order
     *
     * @param availableAmount
     * @return
     */
    private int getOrderAmount(int availableAmount) {
        System.out.println("How many would you like to order? Please enter a whole positive number.");
        return reader.nextInt();
    }

    /*

    make a linkedhashset or treeset so I can save the order
     Cool method to count inventory before asking for it
        HashSet<String> wHouse1 = new HashSet<>(WAREHOUSE1.length);

        HashSet<String> wHouse2 = new HashSet<>(WAREHOUSE2.length);
        for (String x :WAREHOUSE1){
            wHouse1.add(x);
        }
        for (String y :WAREHOUSE2){
            wHouse2.add(y);
        }
        for(int i= 0; i < wHouse1.size(); i++){

        }
        */

}