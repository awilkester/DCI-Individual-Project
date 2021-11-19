package main.java;

import main.java.data.Item;

import java.util.LinkedHashMap;
import java.util.Map;

import static main.java.data.StockRepository.getCategories;
import static main.java.data.StockRepository.getItemsByCategory;

public class OptionThree {
    Session session = new Session();
}
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
