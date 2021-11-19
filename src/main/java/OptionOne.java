package main.java;

import main.java.data.Item;

import java.util.List;
import java.util.Set;

import static main.java.data.StockRepository.getItemsByWarehouse;
import static main.java.data.StockRepository.getWarehouses;

public class OptionOne {
    Session session = new Session();
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
}
