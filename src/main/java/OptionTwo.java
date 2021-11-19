package main.java;

import main.java.data.Item;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static main.java.data.PersonnelRepository.validateUser;
import static main.java.data.StockRepository.*;
import static main.java.data.StockRepository.getWarehouses;

public class OptionTwo {
    Session session = new Session();
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
}
